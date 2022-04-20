package br.com.acrtech.fraude.service

import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class EmailService {

    @Value("\${app.sendgrid.key}")
    lateinit var key: String

    @Value("\${app.sendgrid.account}")
    lateinit var account: String

    fun sendEmail(
        para: String,
        assunto: String,
        conteudo: String
    ) {

        val sg = SendGrid(key)
        val from = Email(account)
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