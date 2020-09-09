package com.cloud.blog.common.config.base;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class ObjectMapperConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 为mapper注册一个带有SerializerModifier的Factory
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new BeanSerializerModifier() {
            @Override
            public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
                //循环所有的beanPropertyWriter
                for (Object beanProperty : beanProperties) {
                    BeanPropertyWriter writer = (BeanPropertyWriter) beanProperty;
                    //判断字段的类型，如果是array，list，set则注册nullSerializer
                    if (isArrayType(writer)) {
                        //给writer注册一个自己的nullSerializer
                        writer.assignNullSerializer(new JsonSerializer<Object>() {
                            @Override
                            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                                if (o == null) {
                                    jsonGenerator.writeStartArray();
                                    jsonGenerator.writeEndArray();
                                }
                            }
                        });
                    } else if (isNumber(writer)) {
                        writer.assignNullSerializer(new JsonSerializer<Object>() {
                            @Override
                            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                                jsonGenerator.writeNumber(0);
                            }
                        });
                    } else if (isBoolean(writer)) {
                        writer.assignNullSerializer(new JsonSerializer<Object>() {
                            @Override
                            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                                jsonGenerator.writeBoolean(false);
                            }
                        });
                    } else if (isStr(writer) || isDate(writer)) {
                        writer.assignNullSerializer(new JsonSerializer<Object>() {
                            @Override
                            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                                jsonGenerator.writeString("");
                            }
                        });
                    } else {
                        writer.assignNullSerializer(new JsonSerializer<Object>() {
                            @Override
                            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                                jsonGenerator.writeStartObject();
                                jsonGenerator.writeEndObject();
                            }
                        });
                    }
                }
                return beanProperties;
            }
        }));
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        /*
          long类型前端精度丢失问题（超过53位二进制数），
          建议在需要转换的字段上加注解 @JsonFormat(shape = JsonFormat.Shape.STRING)
          不做整体数值转换，防止无需转换的long类型也转为string
         */
        return objectMapper;
    }

    /**
     * 判断数组类型
     */
    boolean isArrayType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.isArray() || clazz.equals(List.class) || clazz.equals(Set.class);

    }

    /**
     * 判断日期类型
     */
    boolean isDate(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.equals(Date.class) || clazz.equals(LocalDateTime.class) || clazz.equals(LocalDate.class);
    }

    /**
     * 判断bool类型
     */
    boolean isBoolean(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.equals(Boolean.class) || clazz.equals(boolean.class);
    }
    /**
     * 判断数字类型
     */
    boolean isNumber(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return Number.class.isAssignableFrom(clazz)
                || clazz.equals(Short.class) || clazz.equals(short.class)
                || clazz.equals(Integer.class) || clazz.equals(int.class)
                || clazz.equals(Long.class) || clazz.equals(long.class)
                || clazz.equals(Double.class) || clazz.equals(double.class)
                || clazz.equals(Float.class) || clazz.equals(float.class)
                || clazz.equals(BigDecimal.class);
    }

    /**
     * 判断字符类型
     */
    boolean isStr(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.equals(String.class) || clazz.equals(Character.class)
                || clazz.equals(StringBuilder.class) || clazz.equals(StringBuffer.class);
    }


}
