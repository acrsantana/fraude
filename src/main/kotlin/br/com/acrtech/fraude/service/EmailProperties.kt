package br.com.acrtech.fraude.service

import org.springframework.beans.factory.annotation.Value

class EmailProperties {
    @Value("\${app.sendgrid.key}")
    lateinit var key: String

    @Value("\${app.sendgrid.account}")
    lateinit var account: String

}