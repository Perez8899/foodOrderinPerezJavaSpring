package com.perez.controller;

import com.perez.config.JwtProvider;
import com.perez.reporsitory.UserRepository;
import com.perez.service.CustomerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
   private UserRepository userRepository;
   private PasswordEncoder passwordEncoder;
   private JwtProvider jwtProvider;
   private CustomerService customerService;
   private CartRepository cartRepository;
   //2:51
}
