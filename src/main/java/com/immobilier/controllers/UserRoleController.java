package com.immobilier.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users") // remplir la
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class UserRoleController {

}
