package com.pitchbook.bootcamp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String returnLogInPage() {
        return "login";
    }

    @GetMapping("/")
    public String getStartPage() {
        return "start.page";
    }
}
