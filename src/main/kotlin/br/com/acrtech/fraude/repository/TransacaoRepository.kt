package br.com.acrtech.fraude.repository

import br.com.acrtech.fraude.model.Transacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransacaoRepository : JpaRepository<Transacao, Long> {
}