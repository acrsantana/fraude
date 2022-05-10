package br.com.acrtech.fraude.service

import br.com.acrtech.fraude.dto.UsuarioDto
import br.com.acrtech.fraude.model.Usuario
import br.com.acrtech.fraude.repository.UsuarioRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class UsuarioService(
    val repository: UsuarioRepository,
    val email: EmailService
) : UserDetailsService {
    val illegalArgumentMessage: String = "Já existe um usuário cadastrado com o e-mail informado"
    fun listarTodos(paginacao: Pageable): Page<UsuarioDto> {

        return repository.buscaTodos(paginacao).map {
             UsuarioDto(it)
        }
    }

    fun adicionaUsuario(usuario: UsuarioDto) {

        if (repository.existsByEmail(usuario.email!!)){
            throw IllegalArgumentException(illegalArgumentMessage)
        }

        val senha = Random.nextLong(100000, 999999).toString()
        val usr = Usuario(usuario)
        usr.password = BCryptPasswordEncoder().encode(senha)
        repository.save(usr)

        val mail: String = usuario.email!!
        email.sendEmail(
            mail,
            "Sua senha de acesso ao sistema Anti-fraudes.",
            "Segue senha gerada de forma automática pela aplicação: $senha"
        )
    }

    fun deletaUsuario(id: Long) {
        repository.deleteById(id)
    }

    fun listarPorId(id: Long): UsuarioDto {
        val usuario = repository.findById(id).orElseThrow()
        return UsuarioDto(usuario)
    }

    fun editaUsuario(usuarioDto: UsuarioDto) {

        if (repository.existsByEmail(usuarioDto.email!!)){
            throw IllegalArgumentException(illegalArgumentMessage)
        }
        var usuario = repository.findById(usuarioDto.id!!).orElseThrow()
        usuario.nome = usuarioDto.nome
        usuario.email = usuarioDto.email
        repository.save(usuario)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = repository.findByEmail(username) ?: throw RuntimeException()
        return UserDetailService(usuario)
    }

}