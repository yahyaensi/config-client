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

    @Value("${greeting.msg}")
    private String greetingMsg;

    @GetMapping("/msg")
    public String getMessage() {
        return greetingMsg + " " + environment.getProperty("owner.name") + " : " + environment.getProperty("spring.datasource.password");
    }
}
