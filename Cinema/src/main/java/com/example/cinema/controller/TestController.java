package com.example.cinema.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class TestController {

    @GetMapping()
    @RolesAllowed({"user"})
    public String get() {
        return "xD";
    }
}
