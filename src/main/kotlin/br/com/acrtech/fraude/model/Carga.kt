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
    val totalTransacoes: Int,
    var status: Boolean?, //true = Sucesso, false = Falha
    val dataCarga: LocalDateTime = LocalDateTime.now(),
    var dataReferenciaTransacoes: LocalDate?,
    var listaErros: String?
) {
    override fun toString(): String {
        return "Carga(id=$id, fileName=$fileName, size=$size, totalTransacoes=$totalTransacoes, status=$status, dataCarga=$dataCarga, dataReferenciaTransacoes=$dataReferenciaTransacoes, listaErros=$listaErros)"
    }
}