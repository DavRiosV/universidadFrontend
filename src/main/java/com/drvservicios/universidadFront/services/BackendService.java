package com.drvservicios.universidadFront.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class BackendService {

    private static final String BASE_URL = "http://localhost:8080/api";

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, Object> login(String username, String password) {
        String url = BASE_URL + "/users/signin";
        Map<String, String> params = Map.of("username", username, "password", password);
        return restTemplate.postForObject(url, params, Map.class);
    }

    public void signup(Map<String, Object> userDetails) {
        String url = BASE_URL + "/users/signup";
        restTemplate.postForObject(url, userDetails, Void.class);
    }
}
