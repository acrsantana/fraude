package br.com.acrtech.fraude.repository

import br.com.acrtech.fraude.model.Transacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.LocalDateTime

@Repository
interface TransacaoRepository : JpaRepository<Transacao, Long> {

    @Query("SELECT p FROM Transacao p WHERE " +
            "p.bancoOrigem = ?1 and p.agenciaOrigem = ?2 and p.contaOrigem = ?3 and " +
            "p.bancoDestino = ?4 and p.agenciaDestino = ?5 and  p.contaDestino = ?6 and " +
            "p.valorTransacao = ?7 and p.dataHoraTransacao = ?8")
    fun buscarTransacaoIgual(bo: String, ao: String, co: String, bd: String, ad: String, cd: String, vt: BigDecimal, dt: LocalDateTime) : Transacao?

}