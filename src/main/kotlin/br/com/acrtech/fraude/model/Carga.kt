package br.com.acrtech.fraude.model

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.Table

@Entity
@Table(name = "cargas")
data class Carga(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val fileName: String?,
    val size: Int?,
    var totalTransacoesArquivo: Int,
    var totalTransacoesSucesso: Int,
    var totalTransacoesFalha: Int,
    val dataCarga: LocalDateTime = LocalDateTime.now(),
    var dataReferenciaTransacoes: LocalDate?,
    var erro: String?,
    var status: Boolean?
) {
    override fun toString(): String {
        return "Carga(id=$id, " +
                "fileName=$fileName, " +
                "size=$size, " +
                "totalTransacoesArquivo=$totalTransacoesArquivo, " +
                "totalTransacoesSucesso=$totalTransacoesSucesso, " +
                "totalTransacoesFalha=$totalTransacoesFalha, " +
                "dataCarga=$dataCarga, " +
                "dataReferenciaTransacoes=$dataReferenciaTransacoes, " +
                "erros=$erro, " +
                "status=$status)"
    }
}