package com.example.myblog.controller;

import com.example.myblog.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;
}
