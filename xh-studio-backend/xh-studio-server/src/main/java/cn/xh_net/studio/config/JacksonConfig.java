package cn.xh_net.studio.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

/**
 * Jackson 配置类
 * @author XingHai
 * @date 2026/7/12 16:47
 * @description 用于配置 Jackson 序列化器和反序列化器
 */
@Configuration
public class JacksonConfig {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final String TIME_ZONE = "GMT+8";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            // 1.设置 java.util.Date 的格式和时区
            builder.simpleDateFormat(DATE_FORMAT);
            builder.timeZone(TIME_ZONE);

            // 2.忽略未知属性
            builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            // 3.为 java8 时间类型分别设置序列化器和反序列化器
            builder.serializers(
                    new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
                    new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                    new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMAT))
            );

            builder.deserializers(
                    new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
                    new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                    new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_FORMAT))
            );
        };
    }
}
