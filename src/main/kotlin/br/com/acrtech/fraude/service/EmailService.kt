package br.com.acrtech.fraude.service

import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import org.springframework.stereotype.Service

@Service
class EmailService {

    fun sendEmail(
        para: String,
        assunto: String,
        conteudo: String
    ) {

        val sg = SendGrid()
        val from = Email()
        val to = Email(para)
        val subject = assunto
        val content = Content("text/plain", conteudo)
        val mail = Mail(from, subject, to, content)
        val request = Request()
        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()
            val response = sg.api(request)
        } catch (e: Exception) {
            throw e
        }

    }
}