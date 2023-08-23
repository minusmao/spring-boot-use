package com.example.spring.boot.use.rabbitmq.service.impl;

import com.example.spring.boot.use.rabbitmq.service.BlrTestService;
import com.rabbitmq.client.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 测试编程不良人 rabbitmq 教程
 * <a href="https://www.bilibili.com/video/BV1dE411K7MG/">教程地址</a>
 *
 * @author minus
 * @since 2020/12/6 20:51
 */
//@Service
public class BlrTestServiceImpl implements BlrTestService {

    @Override
    public void testModel1(String msg) {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/blr");
        try {
            Connection connection = connectionFactory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //设置队列
            //参数 1：队列名
            //参数 2：是否持久化
            //参数 3：是否独占队列
            //参数 4：是否自动删除
            //参数 5：其他属性
            channel.queueDeclare("model1", true, false, false, null);
            //生产者发布
            //参数 1：交换机名
            //参数 2：路由键（当前模式需与对列名相同）
            //参数 3：消息的其他属性–路由标头等
            //参数 4：消息正文
            channel.basicPublish("", "model1", null, ("model1 -> " + msg).getBytes());
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册模式一消费者
     * 模型一（Hello world，直连）：channel.queueDeclare()，一个生产者，一个消费者
     */
    @PostConstruct
    private void registerModel1Consumer() {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/blr");
        try {
            Connection connection = connectionFactory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //设置队列
            //参数 1：队列名
            //参数 2：是否持久化
            //参数 3：是否独占队列
            //参数 4：是否自动删除
            //参数 5：其他属性
            channel.queueDeclare("model1", true, false, false, null);
            //消费者消费
            //参数 1：队列名
            //参数 2：是否自动确认消息
            //参数 3：消费时的回调接口
            channel.basicConsume("model1", true, new DefaultConsumer(channel) {
                //参数 1：与消费者关联的消费者标签
                //参数 2：消息的包装数据
                //参数 3：消息的内容标头数据
                //参数 4：消息正文
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    System.out.println("model1 消费者接收到的消息：" + new String(body));
                }
            });
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void testModel2(String msg) {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/blr");
        try {
            Connection connection = connectionFactory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //设置队列
            channel.queueDeclare("model2", true, false, false, null);
            //生产者发布
            for (int i = 0; i < 10; i++) {
                channel.basicPublish("", "model2", null, ("model2 -> [" + i + "]" + msg).getBytes());
            }
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册模式二消费者 1
     * 模型二（work queues）：channel.queueDeclare()，一个生产者，多个消费者
     */
    @PostConstruct
    private void registerModel1Consumer1() {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/blr");
        try {
            Connection connection = connectionFactory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //设置队列
            channel.queueDeclare("model2", true, false, false, null);
            //消费者消费
            channel.basicConsume("model2", true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    System.out.println("model2 消费者 1 接收到的消息：" + new String(body));
                }
            });
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册模式二消费者 2
     * 模型二（work queues）：channel.queueDeclare()，一个生产者，多个消费者
     */
    @PostConstruct
    private void registerModel1Consumer2() {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/blr");
        try {
            Connection connection = connectionFactory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //设置队列
            channel.queueDeclare("model2", true, false, false, null);
//            //消费者消费
//            channel.basicConsume("model2", true, new DefaultConsumer(channel) {
//                @Override
//                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
//                    try {
//                        // 模拟处理消息比较慢，一秒处理一个消息。
//                        // 但是依然会发现，rabbitmq 依然会将消息均匀的发给两个消费者
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    System.out.println("model2 消费者 2 接收到的消息：" + new String(body));
//                }
//            });
            // 一次只接受一条未确认的消息
            channel.basicQos(1);
            // 消费者消费，关闭自动确认
            channel.basicConsume("model2", false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("model2 消费者 2 接收到的消息：" + new String(body));
                    channel.basicAck(envelope.getDeliveryTag(), false);    // 手动确认消息
                }
            });
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void testModel3(String msg) {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/blr");
        try {
            Connection connection = connectionFactory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //设置交换机
            //参数 1：交换机名
            //参数 2：交换机类型（fanout-广播、direct-直接、topic-订阅）
            channel.exchangeDeclare("model3", "fanout");
            //发布消息
            //参数 1：交换机名
            //参数 2：路由键
            //参数 3：消息的其他属性–路由标头等
            //参数 4：消息正文
            channel.basicPublish("model3", "", null, ("model3 -> " + msg).getBytes());
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册模式三消费者 1
     * 模型三（fanout，即广播）：channel.exchangeDeclare("交换机名称","fanout")
     */
    @PostConstruct
    private void registerModel3Consumer1() {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/blr");
        try {
            Connection connection = connectionFactory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //设置交换机
            //参数 1：交换机名称
            //参数 2：交换机类型（fanout-广播、direct-直接、topic-订阅）
            channel.exchangeDeclare("model3", "fanout");
            //创建临时队列
            String queue = channel.queueDeclare().getQueue();
            //队列绑定交换机
            //参数 1：队列名
            //参数 2：交换机名
            //参数 3：路由键
            channel.queueBind(queue, "model3", "");
            //消费者消费
            //参数 1：队列名
            //参数 2：是否自动确认消息
            //参数 3：消费时的回调接口
            channel.basicConsume(queue, true, new DefaultConsumer(channel) {
                //参数 1：与消费者关联的消费者标签
                //参数 2：消息的包装数据
                //参数 3：消息的内容标头数据
                //参数 4：消息正文
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    System.out.println("model3 消费者 1 接收到的消息：" + new String(body));
                }
            });
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 注册模式三消费者 2
     * 模型三（fanout，即广播）：channel.exchangeDeclare("交换机名称","fanout")
     */
    @PostConstruct
    private void registerModel3Consumer2() {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/blr");
        try {
            Connection connection = connectionFactory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //设置交换机
            channel.exchangeDeclare("model3", "fanout");
            //设置队列（非临时队列也可以）
            channel.queueDeclare("model3-2", true, false, false, null);
            //队列绑定交换机
            channel.queueBind("model3-2", "model3", "");
            //消费者消费
            channel.basicConsume("model3-2", true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    System.out.println("model3 消费者 2 接收到的消息：" + new String(body));
                }
            });
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void testModel4(String msg, String routingKey) {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/blr");
        try {
            Connection connection = connectionFactory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //设置交换机
            //参数 1：交换机名称
            //参数 2：交换机类型（fanout-广播、direct-直接、topic-订阅）
            channel.exchangeDeclare("model4", "direct");
            //发布消息
            //参数 1：交换机名
            //参数 2：路由键
            //参数 3：消息的其他属性–路由标头等
            //参数 4：消息正文
            channel.basicPublish("model4", routingKey, null, ("model4 -> " + msg).getBytes());
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册模式四消费者 1
     * 模型四（Routing-Direct，直连）：channel.exchangeDeclare("交换机名称","direct")
     */
    @PostConstruct
    private void registerModel4Consumer1() {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/blr");
        try {
            Connection connection = connectionFactory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //设置交换机
            channel.exchangeDeclare("model4", "direct");
            //创建临时队列
            String queue = channel.queueDeclare().getQueue();
            //队列绑定交换机
            channel.queueBind(queue, "model4", "info");
            channel.queueBind(queue, "model4", "warn");
            channel.queueBind(queue, "model4", "error");
            //消费者消费
            channel.basicConsume(queue, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    System.out.println("model4 消费者 1 接收到的消息：" + new String(body));
                }
            });
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册模式四消费者 2
     * 模型四（Routing-Direct，直连）：channel.exchangeDeclare("交换机名称","direct")
     */
    @PostConstruct
    private void registerModel4Consumer2() {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/blr");
        try {
            Connection connection = connectionFactory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //设置交换机
            channel.exchangeDeclare("model4", "direct");
            //设置队列（非临时队列也可以）
            channel.queueDeclare("model4-2", true, false, false, null);
            //队列绑定交换机
            channel.queueBind("model4-2", "model4", "info");
            channel.queueBind("model4-2", "model4", "warn");
            //消费者消费
            channel.basicConsume("model4-2", true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    System.out.println("model4 消费者 2 接收到的消息：" + new String(body));
                }
            });
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void testModel5(String msg, String routingKey) {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接参数
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机
        connectionFactory.setVirtualHost("/blr");
        try {
            //创建连接
            Connection connection = connectionFactory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //设置交换机
            //参数 1：交换机名称
            //参数 2：交换机类型（fanout-广播、direct-直接、topic-订阅）
            channel.exchangeDeclare("model5", "topic");
            //发布消息
            //参数 1：交换机名
            //参数 2：路由键
            //参数 3：消息的其他属性–路由标头等
            //参数 4：消息正文
            channel.basicPublish("model5", routingKey, null, ("model5 -> " + msg).getBytes());
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册模式五消费者 1
     * 模型五（Routing-Topics，订阅）：channel.exchangeDeclare("交换机名称","topic")
     */
    @PostConstruct
    private void registerModel5Consumer1() {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接参数
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机
        connectionFactory.setVirtualHost("/blr");
        try {
            //创建连接
            Connection connection = connectionFactory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //设置交换机
            channel.exchangeDeclare("model5", "topic");
            //创建临时队列
            String queue = channel.queueDeclare().getQueue();
            //队列绑定交换机
            channel.queueBind(queue, "model5", "info.*");
            channel.queueBind(queue, "model5", "*.error");
            //消费者消费
            channel.basicConsume(queue, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    System.out.println("model5 消费者 1 接收到的消息：" + new String(body));
                }
            });
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册模式五消费者 2
     * 模型五（Routing-Topics，订阅）：channel.exchangeDeclare("交换机名称","topic")
     */
    @PostConstruct
    private void registerModel5Consumer2() {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接参数
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机
        connectionFactory.setVirtualHost("/blr");
        try {
            //创建连接
            Connection connection = connectionFactory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //设置交换机
            channel.exchangeDeclare("model5", "topic");
            //设置队列（非临时队列也可以）
            channel.queueDeclare("model5-2", true, false, false, null);
            //队列绑定交换机
            channel.queueBind("model5-2", "model5", "info.#");
            channel.queueBind("model5-2", "model5", "#.error");
            //消费者消费
            channel.basicConsume("model5-2", true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    System.out.println("model5 消费者 2 接收到的消息：" + new String(body));
                }
            });
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

}
