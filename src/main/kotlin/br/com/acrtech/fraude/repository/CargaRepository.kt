package br.com.acrtech.fraude.repository

import br.com.acrtech.fraude.model.Carga
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface CargaRepository : JpaRepository<Carga, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM cargas WHERE data_referencia_transacoes = :data LIMIT 1")
    fun listaPorData(data: LocalDate): Carga?
}