package com.example.demo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMapper1 userMapper1;

    @GetMapping("/sayHi")
    public String sayHi() {
        int i = userMapper.getUserId();
        System.out.println("用户1数量" + i);
        int i2 = userMapper1.getUserId();
        System.out.println("用户2数量" + i2);
        return "你好啊";
    }
}

