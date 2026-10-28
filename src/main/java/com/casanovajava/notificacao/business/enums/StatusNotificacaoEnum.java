package com.casanovajava.notificacao.business.enums;

// enum: lista FIXA de valores permitidos. Evita erro de digitação ("pendente", "Pendente"...),
// porque só existem estas três opções.
public enum StatusNotificacaoEnum {
    PENDENTE,   // a notificação ainda não foi enviada
    NOTIFICADO, // o e-mail já foi enviado
    CANCELADO   // a tarefa foi cancelada, não deve notificar
}
