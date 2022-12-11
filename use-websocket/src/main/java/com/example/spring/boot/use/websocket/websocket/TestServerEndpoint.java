package com.example.spring.boot.use.websocket.websocket;

import com.example.spring.boot.use.websocket.common.ApiConst;
import com.example.spring.boot.use.websocket.model.MessageDTO;
import com.example.spring.boot.use.websocket.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试
 *
 * @author minus
 * @since 2022/12/7 23:44
 */
@ServerEndpoint(ApiConst.API_URL + "/test/{userId}")
@Component
@Slf4j
public class TestServerEndpoint {

    // 系统消息id
    private static final String SYSTEM_ID = "system";

    // 记录当前在线连接数（线程安全）
    private static final AtomicInteger onlineNum = new AtomicInteger();

    // 记录当前在线连接（线程安全）
    private static final ConcurrentHashMap<String, Session> onlineSessionMap = new ConcurrentHashMap<>();

    public static void addOnlineCount() {
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

    /**
     * 发送消息
     *
     * @param receiver   接收方
     * @param messageDTO 消息对象
     */
    private static void sendMessage(Session receiver, MessageDTO messageDTO) {
        try {
            receiver.getBasicRemote().sendText(JacksonUtil.getBeanToJsonStr(messageDTO));
        } catch (IOException e) {
            log.error("发送消息错误");
            e.printStackTrace();
        }
    }

    /**
     * 建立连接时调用
     *
     * @param session 连接
     * @param userId  用户id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        try {
            String message;
            // 判断该用户id是否已建立连接
            if (onlineSessionMap.containsKey(userId)) {
                message = "用户" + userId + "已存在";
                sendMessage(session, new MessageDTO(SYSTEM_ID, userId, message, LocalDateTime.now()));
                Session onlineSession = onlineSessionMap.get(userId);
                addOnlineCount();
                session.close();
                onlineSessionMap.put(userId, onlineSession);
            } else {
                addOnlineCount();
                onlineSessionMap.put(userId, session);
                message = "欢迎" + userId + "加入，当前在线人数" + onlineNum;
                MessageDTO messageDTO = new MessageDTO(SYSTEM_ID, userId, message, LocalDateTime.now());
                for (Session receiver : onlineSessionMap.values()) {
                    sendMessage(receiver, messageDTO);
                }
            }
            log.info(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接时调用
     *
     * @param userId 用户id
     */
    @OnClose
    public void onClose(@PathParam(value = "userId") String userId) {
        onlineSessionMap.remove(userId);
        subOnlineCount();
        log.info("用户" + userId + "断开连接，当前在线人数" + onlineNum);
    }

    /**
     * 收到客户端信息时调用
     *
     * @param message 客户端消息
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("收到客户端消息：{}", message);
        final MessageDTO messageDTO = JacksonUtil.getJsonStrToBean(message, MessageDTO.class);
        if (messageDTO != null) {
            // 找到接受方id
            final String receiverId = messageDTO.getReceiver();
            if (receiverId == null || receiverId.isBlank()) {
                for (String userId : onlineSessionMap.keySet()) {
                    if (!messageDTO.getSender().equals(userId)) {
                        sendMessage(onlineSessionMap.get(userId), messageDTO);
                    }
                }
            } else {
                if (onlineSessionMap.containsKey(receiverId)) {
                    Session receiver = onlineSessionMap.get(receiverId);
                    sendMessage(receiver, messageDTO);
                } else {
                    log.info("接收方id不存在：{}", receiverId);
                }
            }
        } else {
            log.info("收到客户端消息：{} 解析失败", message);
        }
    }

    /**
     * 错误时调用
     *
     * @param session   错误的session
     * @param throwable 错误对象
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("发生未知错误");
        throwable.printStackTrace();
    }

}
