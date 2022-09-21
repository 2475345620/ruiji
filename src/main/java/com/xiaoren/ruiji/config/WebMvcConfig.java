package com.xiaoren.ruiji.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoren.ruiji.common.JacksonObjectMapper;
import com.xiaoren.ruiji.filter.LoginCheckFilter;
//import com.xiaoren.ruiji.filter.LoginCheckFilter2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Autowired
    private LoginCheckFilter loginCheckFilter;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射...");
        registry
                .addResourceHandler("/backend/**")
                .addResourceLocations("classpath:/backend/");
        registry
                .addResourceHandler("/front/**")
                .addResourceLocations("classpath:/front/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckFilter)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/backend/**",
                        "/front/**",
                        "/employee/login",
                        "/employee/logout"
//                        "/common/**"
                        );
    }


    /**
     * 扩展mvc框架消息转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        创建消息转换器
        MappingJackson2HttpMessageConverter messageConverter =new MappingJackson2HttpMessageConverter();
//        设置为对象转换器，底层使用Jackson将Java对象转为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
//        将上面的消息转换器对象追加到mvc框架的转换器集合中
        converters.add(0,messageConverter);
    }
}
