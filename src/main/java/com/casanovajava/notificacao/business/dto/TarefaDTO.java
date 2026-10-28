package com.casanovajava.notificacao.business.dto;

import com.casanovajava.notificacao.business.enums.StatusNotificacaoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

// DTO (Data Transfer Object): objeto que só CARREGA dados entre as camadas e o JSON.
// Lombok gera getters, setters, construtores e o builder, sem escrever isso à mão.
@Builder           // permite TarefaDTO.builder().nomeTarefa("x").build()
@Getter            // gera os getXxx()
@Setter            // gera os setXxx()
@AllArgsConstructor // construtor com todos os campos
@NoArgsConstructor  // construtor vazio: o Jackson precisa dele para montar o objeto a partir do JSON
public class TarefaDTO {
    private String id;
    private String nomeTarefa;
    private String descricao;

    // @JsonFormat: define como a data aparece/é lida no JSON.
    // Aqui: texto no padrão dia-mês-ano hora:minuto:segundo (ex.: "28-10-2026 14:30:00").
    // "yyyy" = ano com 4 dígitos. Quem chamar a API precisa mandar a data EXATAMENTE neste formato,
    // senão recebe erro 400.
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;

    // Data e hora em que a tarefa acontece: é a que aparece no e-mail
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataEvento;

    // Sem @JsonFormat: é um texto (e-mail), não uma data, então não há o que formatar.
    private String emailUsuario;

    // Mesmo padrão das outras datas. Atenção: "Ateracao" provavelmente deveria ser "Alteracao".
    // Se renomear, o nome no JSON muda junto (e quem envia precisa acompanhar).
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataAteracao;

    // Sem @JsonFormat: o Jackson já escreve o enum pelo nome ("PENDENTE", "NOTIFICADO"...).
    private StatusNotificacaoEnum statusNotificacaoEnum;

    // Cria a propriedade "email" no JSON: quem envia {"email": "..."} preenche o emailUsuario.
    // Útil para aceitar o nome "email" vindo de outro projeto (o agendador-tarefas).
    public void setEmail(String email) {
        this.emailUsuario = email;
    }
}
