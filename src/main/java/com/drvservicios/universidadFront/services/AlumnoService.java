package com.drvservicios.universidadFront.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.drvservicios.universidadFront.dtos.AlumnoDTO;

import java.util.Arrays;
import java.util.List;

@Service
public class AlumnoService {
    @Autowired
    private RestTemplate restTemplate;

    private final String baseUrl = "http://localhost:8080/api/alumnos";

    public List<AlumnoDTO> findAll() {
        ResponseEntity<AlumnoDTO[]> response = restTemplate.getForEntity(baseUrl, AlumnoDTO[].class);
        return Arrays.asList(response.getBody());
    }
}
