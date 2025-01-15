package com.drvservicios.universidadFront.services;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.drvservicios.universidadFront.dtos.AlumnoDTO;

import java.util.Arrays;
import java.util.List;

@Service
public class AlumnoService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String BACKEND_URL = "http://localhost:8080/api/alumnos";

    public List<AlumnoDTO> findAll() {
        AlumnoDTO[] alumnos = restTemplate.getForObject(BACKEND_URL, AlumnoDTO[].class);
        return Arrays.asList(alumnos);
    }
}