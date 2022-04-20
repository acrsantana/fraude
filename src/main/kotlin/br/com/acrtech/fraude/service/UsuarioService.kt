package br.com.acrtech.fraude.service

import br.com.acrtech.fraude.dto.UsuarioDto
import br.com.acrtech.fraude.model.Usuario
import br.com.acrtech.fraude.repository.UsuarioRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    val repository: UsuarioRepository
) {
    fun listarTodos(paginacao: Pageable): Page<UsuarioDto> {
        return repository.findAll(paginacao).map { UsuarioDto(it) }
    }

    fun adicionaUsuario(usuario: UsuarioDto, senha: String) {
        val usr = Usuario(usuario)
        usr.password = senha
        repository.save(usr)
    }

}
