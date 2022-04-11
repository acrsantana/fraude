package br.com.acrtech.fraude.model

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "transacoes")
data class Transacao(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val bancoOrigem: String,
    val agenciaOrigem: String,
    val contaOrigem: String,
    val bancoDestino : String,
    val agenciaDestino: String,
    val contaDestino: String,
    val valorTransacao: BigDecimal,
    val dataHoraTransacao: LocalDateTime
) {

}