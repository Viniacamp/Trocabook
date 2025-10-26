package com.trocabook.Trocabook.service;

import com.trocabook.Trocabook.controllers.response.ChatResponse;
import com.trocabook.Trocabook.model.dto.ConversaDTO;
import com.trocabook.Trocabook.model.dto.MensagemDTO;
import com.trocabook.Trocabook.repository.UsuarioRepository;
import com.trocabook.Trocabook.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.LinkedList;
import java.util.List;

@Service
public class ChatService implements IChatService {
    private static String url = "http://localhost:8282/api/chat";

    private RestTemplate restTemplate;

    private UsuarioRepository usuarioRepository;

    @Autowired
    public ChatService(RestTemplate restTemplate, UsuarioRepository usuarioRepository) {
        this.restTemplate = restTemplate;
        this.usuarioRepository = usuarioRepository;
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
    public ChatResponse<List<MensagemDTO>> listarMensagensEntreUsuarios(int cdUsuarioLivro, int cdUsuarioDestinatario, int cdUsuarioRemetente) {
        String endpoint = String.format("%s/mensagens?remetente=%d&destinatario=%d&usuarioLivro=%d", url, cdUsuarioRemetente, cdUsuarioDestinatario, cdUsuarioLivro);
        ResponseEntity<ChatResponse<List<MensagemDTO>>> response = restTemplate.exchange(
                endpoint,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ChatResponse<List<MensagemDTO>>>() {
                }

        );
        return response.getBody();
    }

    @Override
    public ChatResponse<List<MensagemDTO>> listarMensagensPorUsuarioDataEnvioDecrescente(int cdUsuarioRemetente) {
        String endpoint = String.format("%s/mensagens?remetente=%d", url, cdUsuarioRemetente);
        ResponseEntity<ChatResponse<List<MensagemDTO>>> response = restTemplate.exchange(
                endpoint,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ChatResponse<List<MensagemDTO>>>() {}
        );
        return response.getBody();
    }

    @Override
    public List<ConversaDTO> listarMensagensPorUsuarioConverter(List<MensagemDTO> listaMensagensEntreUsuarios, int cdUsuarioLogado) {
        List<ConversaDTO> conversas = new LinkedList<>();

        for (MensagemDTO mensagemDTO : listaMensagensEntreUsuarios) {


            boolean enviadaPeloUsuarioLogado = true;

            if (mensagemDTO.getCdUsuarioRemetente() != cdUsuarioLogado) {
                mensagemDTO.setCdUsuarioDestinatario(mensagemDTO.getCdUsuarioRemetente());
                mensagemDTO.setCdUsuarioRemetente(cdUsuarioLogado);
                enviadaPeloUsuarioLogado = false;
            }


            Usuario destinatario = usuarioRepository
                    .findById(mensagemDTO.getCdUsuarioDestinatario())
                    .orElse(null);

            if (destinatario != null) {
                ConversaDTO conversa = new ConversaDTO();
                conversa.setCdUsuarioLivro(mensagemDTO.getCdUsuarioLivro());
                conversa.setCdUsuarioDestinatario(destinatario.getCdUsuario());
                conversa.setNomeDestinatario(destinatario.getNmUsuario());
                conversa.setFotoDestinatario(destinatario.getFoto());
                conversa.setUltimaMensagem(mensagemDTO.getConteudo());
                conversa.setEnviadaPeloUsuarioLogado(enviadaPeloUsuarioLogado);

                conversas.add(conversa);
            }
        }

        return conversas;
    }

}
