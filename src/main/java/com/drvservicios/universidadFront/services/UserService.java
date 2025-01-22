package com.drvservicios.universidadFront.services;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.drvservicios.universidadFront.dtos.UserDTO;

@Service
public class UserService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String BACKEND_URL = "http://localhost:8080/api/users/signin";

    public String signin(UserDTO user) {
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/api/auth/signin", user, String.class);
        return response.getBody(); // Retorna el JWT Token
    }

}