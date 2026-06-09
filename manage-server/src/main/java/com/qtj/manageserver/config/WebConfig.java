package com.qtj.manageserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${files.upload.local-path}")
    private String localDir;
    @Value("${files.upload.virtual-path}")
    private String virtualDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 把虚拟上传接口映射到本地目录
        registry.addResourceHandler(virtualDir + "**").addResourceLocations("file:" + localDir);
    }
}
