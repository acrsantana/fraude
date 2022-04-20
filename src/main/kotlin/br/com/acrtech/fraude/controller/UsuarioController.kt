package br.com.acrtech.fraude.controller

import br.com.acrtech.fraude.dto.UsuarioDto
import br.com.acrtech.fraude.service.EmailService
import br.com.acrtech.fraude.service.UsuarioService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import kotlin.random.Random

@Controller
@RequestMapping("/usuarios")
class UsuarioController(
    val service: UsuarioService,
    val email: EmailService
) {

    @GetMapping
    fun listarTodos(model: ModelMap, @PageableDefault(size = 20) paginacao: Pageable): String {
        model.addAttribute("usuarios", service.listarTodos(paginacao))

        return "/usuarios/listar"
    }

    @GetMapping("/cadastro")
    fun cadastrarUsuario(
        model: Model
    ): String {
        model.addAttribute("usuario", UsuarioDto())
        return "usuarios/cadastroUsuario"
    }

    @PostMapping("/cadastro")
    fun cadastraUsuario(
        model: Model,
        @ModelAttribute usuarioDto: UsuarioDto,
        @PageableDefault(size = 20) paginacao: Pageable
    ): String {
        val senha = Random.nextLong(100000, 999999).toString()
        val mail: String = usuarioDto.email ?: ""
        email.sendEmail(
            mail,
            "Sua senha de acesso ao sistema Anti-fraudes.",
            "Segue senha gerada de forma automática pela aplicação: $senha"
        )
        service.adicionaUsuario(usuarioDto, senha)
        model.addAttribute("usuarios", service.listarTodos(paginacao))
        return "/usuarios/listar"
    }
}