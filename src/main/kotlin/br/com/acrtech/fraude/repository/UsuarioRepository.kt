package br.com.acrtech.fraude.repository

import br.com.acrtech.fraude.model.Usuario
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long> {

    fun findByEmail(email: String?): Usuario?


    @Query("select u from Usuario u where u.visivel = true")
    fun buscaTodos(paginacao: Pageable): Page<Usuario>

    fun existsByEmail(email: String): Boolean
}
