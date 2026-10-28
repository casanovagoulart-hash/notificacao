package com.casanovajava.notificacao.business;

import com.casanovajava.notificacao.business.dto.TarefaDTO;
import com.casanovajava.notificacao.infrastructure.exceptions.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

// @Service: avisa o Spring que esta classe é um bean da camada de negócio
// @RequiredArgsConstructor: o Lombok cria o construtor com os campos "final",
// e é por ele que o Spring injeta o JavaMailSender e o TemplateEngine
@Service
@RequiredArgsConstructor
public class EmailService {

    // JavaMailSender: o "carteiro". Usa o application.yaml (host, porta, usuário, senha) para enviar
    private final JavaMailSender javaMailSender;

    // TemplateEngine: o motor do Thymeleaf. Pega o HTML e troca ${...} pelos valores reais
    private final TemplateEngine templateEngine;

    // @Value lê uma propriedade do application.yaml: aqui, envio.email.remetente.
    // Não pode ser "final", porque o Spring preenche depois de criar o objeto.
    @Value("${envio.email.remetente}")
    private String remetente;

    // Nome que aparece para o destinatário (ex.: "Casanova Java")
    @Value("${envio.email.nomeRemetente}")
    private String nomeRemetente;

    // Monta e envia o e-mail de aviso de uma tarefa
    public void enviarEmail(TarefaDTO dto) {

        try {
            // MimeMessage: a mensagem de e-mail "vazia"; o helper facilita preenchê-la
            MimeMessage mensagem = javaMailSender.createMimeMessage();
            // true = aceita HTML e anexos (multipart) | UTF-8 = acentos aparecem certos
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mensagem, true, StandardCharsets.UTF_8.name());

            // De quem: e-mail + nome de exibição. O Gmail pode trocar pelo e-mail autenticado.
            mimeMessageHelper.setFrom(new InternetAddress(remetente, nomeRemetente));
            // Para quem: parse aceita um ou vários e-mails separados por vírgula
            mimeMessageHelper.setTo(InternetAddress.parse(dto.getEmailUsuario()));
            mimeMessageHelper.setSubject("Notificação de tarefa!");

            // Context: a "sacola" de variáveis que o template HTML vai ler.
            // Os nomes (nomeTarefa, dataEvento, descricao) precisam ser IGUAIS aos do HTML: ${nomeTarefa}
            Context context = new Context();
            context.setVariable("nomeTarefa", dto.getNomeTarefa());
            context.setVariable("dataEvento", dto.getDataEvento());
            context.setVariable("descricao", dto.getDescricao());

            // process("notificacao", ...) procura src/main/resources/templates/notificacao.html
            // e devolve o HTML já preenchido. Se o nome do arquivo for outro, dá erro de template.
            String template = templateEngine.process("notificacao", context);
            // true = o texto é HTML (sem isso apareceriam as tags na tela)
            mimeMessageHelper.setText(template, true);
            // Envia de verdade: é AQUI que o Spring conecta no Gmail (por isso o erro de senha só aparece agora)
            javaMailSender.send(mensagem);

            // MailException: erros do envio feitos pelo Spring (senha errada, timeout, servidor fora do ar).
            // Estes são os erros MAIS comuns, por isso entram no mesmo tratamento dos outros dois.
        } catch (MessagingException | UnsupportedEncodingException | MailException e) {
            // Transforma o erro técnico numa exceção própria do projeto.
            // Passamos o próprio "e" como causa: assim o log mostra o erro REAL
            // (ex.: senha incorreta, servidor fora do ar) e não só "Erro ao enviar email".
            throw new EmailException("Erro ao enviar email", e);
        }

    }

}
