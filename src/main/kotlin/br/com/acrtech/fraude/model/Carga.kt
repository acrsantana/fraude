package br.com.acrtech.fraude.model

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
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
    val status: Boolean, //true = Sucesso, false = Falha
    val dataCarga: LocalDateTime = LocalDateTime.now(),
    val dataReferenciaTransacoes: LocalDate
)