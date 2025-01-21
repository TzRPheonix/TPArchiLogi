package org.example.borrowingservice.rest;

import org.example.borrowingservice.dto.BorrowingDTO;
import org.example.borrowingservice.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class ServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    public String getUserRank(Long id) {
        String userServiceUrl = "http://localhost:8090/users/" + id;

        // Récupérer l'objet complet de l'utilisateur
        UserDTO userDTO = restTemplate.getForObject(userServiceUrl, UserDTO.class);

        // Retourner uniquement le champ membershipType
        if (userDTO != null) {
            return userDTO.getMembershipType();
        } else {
            throw new RuntimeException("Utilisateur avec l'ID " + id + " introuvable.");
        }
    }

    public void lockUser(Long id) {
        String userServiceUrl = "http://localhost:8090/users/" + id + "/lock";
        restTemplate.put(userServiceUrl, null);
    }

    public void unlockUser(Long id) {
        String userServiceUrl = "http://localhost:8090/users/" + id + "/unlock";
        restTemplate.put(userServiceUrl, null);
    }

    public void setAvailable(Long id) {
        String bookServiceUrl = "http://localhost:8090/books/" + id + "/available";
        restTemplate.put(bookServiceUrl, null);
    }

    public void setUnavailable(Long id) {
        String bookServiceUrl = "http://localhost:8090/books/" + id + "/unavailable";
        restTemplate.put(bookServiceUrl, null);
    }
}
