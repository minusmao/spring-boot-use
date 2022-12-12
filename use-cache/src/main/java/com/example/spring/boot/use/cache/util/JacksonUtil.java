package com.example.spring.boot.use.cache.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 基于jackson框架的JSON工具类
 * 参考文档1：https://blog.csdn.net/pan_junbiao/article/details/106442666
 * 参考文档2：https://juejin.cn/post/6844904166809157639
 *
 * @author minus
 * @since 2022/12/8 23:02
 */
public class JacksonUtil {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = generateObjectMapper();
    }

    /**
     * 获得指定规则的ObjectMapper对象
     *
     * @return ObjectMapper对象
     */
    public static ObjectMapper generateObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 配置忽略未知字段，即JSON中的字段在Java对象中找不到，直接忽略，不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 设置基本类型不能为null值，默认基本类型在JSON为null时，会自动处理，这里可以设置为不能为null且抛出异常
//        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        // 设置LocalDateTime序列化支持，参考文档：https://juejin.cn/post/7025160932653268999
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        objectMapper.registerModule(javaTimeModule);

        return objectMapper;
    }

    /**
     * 将对象转换成JSON字符串
     *
     * @param object 对象
     * @return JSON字符串
     */
    public static String getBeanToJsonStr(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON字符串转换成对象
     *
     * @param jsonStr JSON字符串
     * @param clazz   对象类型
     * @return 对象
     */
    public static <T> T getJsonStrToBean(String jsonStr, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON字符串转换成List列表
     *
     * @param jsonStr JSON字符串
     * @param clazz   列表元素类型
     * @return List列表
     */
    public static <T> List<T> getJsonStrToList(String jsonStr, Class<T> clazz) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
            return objectMapper.readValue(jsonStr, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON字符串转换成Set集合
     *
     * @param jsonStr JSON字符串
     * @param clazz   集合元素类型
     * @return Set集合
     */
    public static <E> Set<E> getJsonStrToSet(String jsonStr, Class<E> clazz) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(Set.class, clazz);
            return objectMapper.readValue(jsonStr, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON字符串转换成Map集合
     *
     * @param jsonStr   JSON字符串
     * @param keyType   键类型
     * @param valueType 值类型
     * @return Map集合
     */
    public static <K, V> Map<K, V> getJsonToMap(String jsonStr, Class<K> keyType, Class<V> valueType) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructMapType(Map.class, keyType, valueType);
            return objectMapper.readValue(jsonStr, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON字符串转换成JsonNode
     *
     * @param jsonStr JSON字符串
     * @return JsonNode
     */
    public static JsonNode getStrToJsonNode(String jsonStr) {
        try {
            return objectMapper.readTree(jsonStr);    // objectMapper.readValue(jsonStr, JsonNode.class); 效果一样
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将对象转换成JsonNode
     *
     * @param object 对象
     * @return JsonNode
     */
    public static JsonNode getBeanToJsonNode(Object object) {
        try {
            return objectMapper.valueToTree(object);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JsonNode转换成对象
     *
     * @param jsonNode 对象
     * @return 对象
     */
    public static <T> T getBeanToJsonNode(JsonNode jsonNode, Class<T> clazz) {
        try {
            return objectMapper.treeToValue(jsonNode, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
