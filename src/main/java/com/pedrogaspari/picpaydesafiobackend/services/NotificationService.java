package com.pedrogaspari.picpaydesafiobackend.services;

import com.pedrogaspari.picpaydesafiobackend.DTOs.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import com.pedrogaspari.picpaydesafiobackend.domain.user.User;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        if (restTemplate == null) {
            throw new IllegalStateException("RestTemplate não foi injetado corretamente");
        }
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

        try {
            ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest, String.class);

            if (!notificationResponse.getStatusCode().is2xxSuccessful()) {
                String responseBody = notificationResponse.getBody();
                throw new Exception("Falha ao enviar notificação: " + (responseBody != null ? responseBody : "Resposta vazia"));
            }
            System.out.println("Notificação enviada com sucesso para " + email);
        }catch (HttpServerErrorException e){
            throw new Exception("Serviço de notificação indisponível no momento. Tente novamente mais tarde. Detalhes: " + e.getMessage());
        }
    }
}
