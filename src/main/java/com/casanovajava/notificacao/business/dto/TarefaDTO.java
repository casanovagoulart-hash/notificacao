package com.casanovajava.notificacao.business.dto;

import com.casanovajava.notificacao.business.enums.StatusNotificacaoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarefaDTO {

    private String id;
    private String nomeTarefa;
    private String descricao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataEvento;

    private String emailUsuario;

    // CORREÇÃO: Renomeado de 'dataAteracao' para 'dataAlteracao'.
    // ARGUMENTO DIDÁTICO: Como o BFF e o agendadortarefas agora serializam esse campo
    // como "dataAlteracao" no JSON, o Jackson aqui precisa ter o nome exato para não
    // ignorar a propriedade durante a desserialização.
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataAlteracao;

    private StatusNotificacaoEnum statusNotificacaoEnum;

    public void setEmail(String email) {
        this.emailUsuario = email;
    }
}
