package com.trocabook.Trocabook.service;


import com.trocabook.Trocabook.controllers.response.ChatResponse;
import com.trocabook.Trocabook.model.dto.ConversaDTO;
import com.trocabook.Trocabook.model.dto.MensagemDTO;

import java.util.List;

public interface IChatService {
    ChatResponse<MensagemDTO> enviarMensagem(MensagemDTO mensagemDTO);

    ChatResponse<List<MensagemDTO>> listarMensagensEntreUsuarios(int cdUsuarioLivro, int cdUsuarioRemetente, int cdUsuarioDestinatario);

    ChatResponse<List<MensagemDTO>> listarMensagensPorUsuarioDataEnvioDecrescente(int cdUsuarioRemetente);

    List<ConversaDTO> listarMensagensPorUsuarioConverter(List<MensagemDTO> listaMensagensEntreUsuarios, int cdUsuarioLogado);

    ChatResponse<MensagemDTO> alterarMensagem(String id, String conteudo);

    ChatResponse<Void> excluirMensagem(String id);
}
