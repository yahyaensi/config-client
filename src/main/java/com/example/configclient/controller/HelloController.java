package com.example.configclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
@RefreshScope
public class HelloController {

    @Autowired
    private Environment environment;

    @Value("${spring.datasource.password}")
    private String greetingMsg;

    @Value("${spring.datasource.username}")
    private String greetingMsg2;

    @GetMapping("/msg")
    public String getMessage() {
        return greetingMsg + " : " + greetingMsg2;
    }
}
