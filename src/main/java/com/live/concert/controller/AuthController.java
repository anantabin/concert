package com.live.concert.controller;

import com.live.concert.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class AuthController {
    @Autowired
    private AccountService accountService;

    // TODO: Not Implemented Yet
}
