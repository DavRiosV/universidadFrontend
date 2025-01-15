package com.drvservicios.universidadFront.services;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.drvservicios.universidadFront.dtos.UserDTO;

@Service
public class UserService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String BACKEND_URL = "http://localhost:8080/api/users/signin";

    public String signin(UserDTO user) {
        return restTemplate.postForObject(BACKEND_URL, user, String.class);
    }
}