package com.casanovajava.notificacao.controller;

import com.casanovajava.notificacao.business.EmailService;
import com.casanovajava.notificacao.business.dto.TarefaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController: marca a classe como controller REST (já é um bean do Spring,
// por isso NÃO precisa de @Service aqui). O retorno vira JSON/texto na resposta.
// @RequestMapping("/email"): todas as rotas desta classe começam com /email
// @RequiredArgsConstructor: o Lombok cria o construtor com os campos final (injeção do EmailService)
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    // O controller só RECEBE e REPASSA: a regra de negócio fica no EmailService
    private final EmailService emailService;

    // @PostMapping: atende POST /email (POST = enviar dados para o servidor fazer uma ação)
    // @RequestBody: transforma o JSON enviado no Apidog em um TarefaDTO
    @PostMapping
    public ResponseEntity<Void> enviarEmail(@RequestBody TarefaDTO dto) {
        // Monta e envia o e-mail. Se algo falhar, o EmailService lança o EmailException
        // e o GlobalExceptionHandler transforma em resposta HTTP.
        emailService.enviarEmail(dto);
        // 200 OK sem corpo: o e-mail foi enviado
        return ResponseEntity.ok().build();
    }
}
