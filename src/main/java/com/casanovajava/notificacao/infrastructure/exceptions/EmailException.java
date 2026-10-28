package com.casanovajava.notificacao.infrastructure.exceptions;

// Exceção PRÓPRIA do projeto. Estende RuntimeException (não verificada), então não precisa
// de "throws" nem de try/catch em todo lugar. Nome claro ajuda a achar o erro no log.
public class EmailException extends RuntimeException {

    // Só a mensagem
    public EmailException(String message) {
        super(message);
    }

    // Mensagem + a causa original (o erro de verdade, como "senha incorreta").
    // Passar a causa mantém o histórico do erro no log, o que facilita muito achar o problema.
    public EmailException(String mensagem, Throwable throwable) {
        super(mensagem, throwable);
    }

}
