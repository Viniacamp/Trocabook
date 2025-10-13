package com.trocabook.Trocabook.service;

import com.trocabook.Trocabook.controllers.response.ChatResponse;
import com.trocabook.Trocabook.model.dto.MensagemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Service
public class ChatService implements IChatService {
    private static String url = "http://localhost:8282/api/chat";

    private RestTemplate restTemplate;

    @Autowired
    public ChatService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ChatResponse<MensagemDTO> enviarMensagem(MensagemDTO mensagemDTO) {
        HttpEntity<MensagemDTO> entidade = new HttpEntity<>(mensagemDTO);
        ResponseEntity<ChatResponse<MensagemDTO>> response = restTemplate.exchange(
                url + "/mensagens",
                HttpMethod.POST,
                entidade,
                new ParameterizedTypeReference<ChatResponse<MensagemDTO>>(){}
        );
        return response.getBody();
    }

    @Override
    public ChatResponse<List<MensagemDTO>> listarMensagensEntreUsuarios(int cdUsuarioDestinatario, int cdUsuarioRemetente) {
        HashMap<String, Integer> params = new HashMap<>();
        params.put("destinatario", cdUsuarioDestinatario);
        params.put("remetente", cdUsuarioRemetente);
        ResponseEntity<ChatResponse<List<MensagemDTO>>> response = restTemplate.exchange(
                url + "/mensagens",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ChatResponse<List<MensagemDTO>>>() {
                },
                params

        );
        return response.getBody();
    }

    @Override
    public ChatResponse<List<MensagemDTO>> listarMensagensPorUsuarioDataEnvioDecrescente(int cdUsuarioRemtente) {
        HashMap<String, Integer> params = new HashMap<>();
        params.put("remetente", cdUsuarioRemtente);
        ResponseEntity<ChatResponse<List<MensagemDTO>>> response = restTemplate.exchange(
                url + "/mensagens",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ChatResponse<List<MensagemDTO>>>() {},
                params
        );
        return response.getBody();
    }
}
