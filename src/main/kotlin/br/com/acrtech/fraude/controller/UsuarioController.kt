package br.com.acrtech.fraude.controller

import br.com.acrtech.fraude.dto.UsuarioDto
import br.com.acrtech.fraude.service.UsuarioService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/usuarios")
class UsuarioController(
    val service: UsuarioService
) {

    @GetMapping
    fun listarTodos(model: ModelMap, @PageableDefault(size = 20, sort = ["id"]) paginacao: Pageable): String {
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
        @PageableDefault(size = 20, sort = ["id"]) paginacao: Pageable
    ): String {
        service.adicionaUsuario(usuarioDto)
        model.addAttribute("usuarios", service.listarTodos(paginacao))
        return "/usuarios/listar"
    }

    @GetMapping("/edita")
    fun alteraUsuario(
        @RequestParam id: Long,
        model: Model,
    ): String {
        println("passou aqui no alteraUsuario")
        model.addAttribute("usuario", service.listarPorId(id))
        return "usuarios/editaUsuario"
    }
    @PostMapping("/edita")
    fun editaUsuario(
        model: Model,
        @ModelAttribute usuarioDto: UsuarioDto,
        @PageableDefault(size = 20, sort = ["id"]) paginacao: Pageable
    ): String {
        println("passou aqui no editaUsuario")
        service.editaUsuario(usuarioDto)
        model.addAttribute("usuarios", service.listarTodos(paginacao))
        return "/usuarios/listar"
    }
    @GetMapping("/apaga")
    fun deletaUsuario(
        @RequestParam id: Long,
        model: Model,
        @PageableDefault(size = 20, sort = ["id"]) paginacao: Pageable

    ): String {
        service.deletaUsuario(id)
        model.addAttribute("usuarios", service.listarTodos(paginacao))
        return "/usuarios/listar"
    }

}