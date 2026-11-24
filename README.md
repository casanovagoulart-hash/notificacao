# 📧 Microsserviço de Notificações (`notificacao`)

![Java](https://shields.io)
![Spring Boot](https://shields.io)
![Groovy](https://shields.io)
![Thymeleaf](https://shields.io)
![Docker](https://shields.io)

Microsserviço dedicado ao processamento e envio transacional de e-mails de lembrete de tarefas utilizando **Spring Boot Mail (SMTP)** e templates HTML com **Thymeleaf**.

## 🛡️ Segurança e Arquitetura
* **Desacoplamento:** Isola o envio de e-mails dos demais serviços, recebendo requisições do `CronService` do BFF na porta interna `8082`.
* **Gestão Segura de Credenciais:** Autenticação SMTP externa injetada via variáveis de ambiente (`MAIL_USERNAME` e `MAIL_PASSWORD`), sem exposição de senhas no código-fonte.

## ⚙️ Variáveis de Ambiente Necessárias
* `MAIL_USERNAME`: Conta de e-mail remetente (ex: Gmail)
* `MAIL_PASSWORD`: Senha de aplicativo (App Password) gerada pelo provedor SMTP

## 🙏 Créditos e Agradecimentos
Desenvolvido com apoio da **Javanauta Academy** (mentora **Angélica Weiler**), assistência arquitetural e pair programming com **IA (Google)**, e dedicado a **Deus** e à minha família (**esposa, filhos, neto, nora e genro**).
