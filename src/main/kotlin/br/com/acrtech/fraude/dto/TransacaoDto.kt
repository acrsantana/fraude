package br.com.acrtech.fraude.dto

import br.com.acrtech.fraude.model.Transacao
import java.time.format.DateTimeFormatter
import java.util.*

class TransacaoDto() {
    var id:Long? = null
    var bancoOrigem: String? = null
    var agenciaOrigem: String? = null
    var contaOrigem: String? = null
    var bancoDestino: String? = null
    var agenciaDestino: String? = null
    var contaDestino: String? = null
    var dataTransacao: String? = null
    var valorTransacao: String? = null

    constructor(transacao: Transacao) : this() {
        this.id = transacao.id
        this.bancoOrigem = transacao.bancoOrigem
        this.agenciaOrigem = transacao.agenciaOrigem
        this.contaOrigem = transacao.contaOrigem
        this.bancoDestino = transacao.bancoDestino
        this.agenciaDestino = transacao.agenciaDestino
        this.contaDestino = transacao.contaDestino
        this.dataTransacao = transacao.dataHoraTransacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        this.valorTransacao = transacao.valorTransacao.toString()
    }

}