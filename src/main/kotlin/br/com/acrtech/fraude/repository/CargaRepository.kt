package br.com.acrtech.fraude.repository

import br.com.acrtech.fraude.model.Carga
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface CargaRepository : JpaRepository<Carga, Long> {
    fun findByDataReferenciaTransacoes(data: LocalDate): Carga?
}