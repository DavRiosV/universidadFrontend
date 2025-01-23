package com.drvservicios.universidadFront.services;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class BackendService {

    private static final Logger logger = LoggerFactory.getLogger(BackendService.class);

    @Autowired
    private RestTemplate restTemplate;

    private static final String BACKEND_URL = "http://localhost:8080/api/users/signin";

    public Map<String, Object> login(String username, String password) {
        logger.info("Enviando solicitud de inicio de sesión al backend para el usuario: {}", username);

        // Construir el cuerpo de la solicitud
        Map<String, String> requestBody = Map.of(
            "username", username,
            "password", password
        );

        try {
            // Construir los encabezados si es necesario (opcional)
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            // Crear la entidad HTTP con cuerpo y encabezados
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Realizar la solicitud POST al backend
            ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                BACKEND_URL,
                org.springframework.http.HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {}
            );

            Map<String, Object> backendResponse = responseEntity.getBody();

            if (backendResponse != null && backendResponse.containsKey("token")) {
                logger.info("Inicio de sesión exitoso. Token recibido del backend para el usuario: {}", username);
                return Map.of("token", backendResponse.get("token"));
            } else {
                logger.error("El backend no devolvió un token válido para el usuario: {}", username);
                throw new RuntimeException("No se recibió un token válido del backend.");
            }
        } catch (HttpClientErrorException e) {
            logger.error("Error en la solicitud al backend: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Error en la autenticación: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            logger.error("Error inesperado durante la autenticación: {}", e.getMessage());
            throw new RuntimeException("Error inesperado: " + e.getMessage());
        }
    }

    public void logout() {
        logger.info("Cerrando sesión en el backend.");
        // Aquí podrías implementar la lógica para notificar al backend sobre el logout si es necesario
    }
}
