package com.live.concert.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConcertController {

    @GetMapping("/hello")
    void hello(){
        System.out.println("Hello World");
    }
}
