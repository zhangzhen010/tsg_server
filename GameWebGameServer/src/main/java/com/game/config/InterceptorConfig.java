package com.game.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * JWT拦截配置
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/13 10:30
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private @Resource ReqPlayerHandlerMethodArgumentResolver reqPlayerHandlerMethodArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                //拦截所有请求，通过判断token来决定是否需要登陆
                .addPathPatterns("/**")
                //.excludePathPatterns("/**");
                .excludePathPatterns("/tsg/login/**","/tsg/publicinfo/**");
    }

    @Bean
    public TokenInterceptor jwtInterceptor() {
        return new TokenInterceptor();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 所有请求都允许跨域
        registry.addMapping("/**").allowedOriginPatterns("*").allowCredentials(true).allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").maxAge(3600);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // 添加自定义的 HandlerMethodArgumentResolver 实例
        resolvers.add(reqPlayerHandlerMethodArgumentResolver);
    }

}
