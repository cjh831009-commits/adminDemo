package com.admin.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcPathPrefixConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

        configurer.addPathPrefix("/adminApi",
                handlerType -> handlerType.getPackageName().startsWith("com.admin.iam.adapter.in.web.admin")
        );

        configurer.addPathPrefix("/openApi",
                handlerType -> handlerType.getPackageName().startsWith("com.admin.iam.adapter.in.web.openapi")
        );
    }
}
