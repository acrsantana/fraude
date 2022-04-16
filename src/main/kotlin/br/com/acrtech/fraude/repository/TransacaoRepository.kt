package br.com.acrtech.fraude.repository

import br.com.acrtech.fraude.model.Transacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.LocalDateTime

@Repository
interface TransacaoRepository : JpaRepository<Transacao, Long> {
    fun findByBancoOrigemAndAgenciaOrigemAndContaOrigemAndBancoDestinoAndAgenciaDestinoAndContaDestinoAndValorTransacaoAndDataHoraTransacao(
        bancoOrigem: String,
        agenciaOrigem: String,
        contaOrigem: String,
        bancoDestino : String,
        agenciaDestino: String,
        contaDestino: String,
        valorTransacao: BigDecimal,
        dataHoraTransacao: LocalDateTime
    ) : Transacao?

}