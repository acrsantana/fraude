package br.com.acrtech.fraude.controller

import br.com.acrtech.fraude.dto.FileDto
import br.com.acrtech.fraude.service.TransacaoService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Suppress("UNREACHABLE_CODE")
@Controller
class HomeController(
    val service: TransacaoService
) {

    @GetMapping("/")
    fun home(model: ModelMap, @PageableDefault(size=100, sort = ["status","dataCarga"], direction = Sort.Direction.DESC) paginacao: Pageable):String{
        model.addAttribute("cargas", service.listarCargas(paginacao))
        return "/home/home"
    }

    @PostMapping("uploadFile")
    fun uploadFile(
        file: FileDto,
        result: BindingResult,
        model: ModelMap,
        paginacao: Pageable
    ): String {
        if (result.hasErrors()){
            model.addAttribute("erro", "Erro ao carregar o arquivo")
            return "/home/uploadError"
        }

        val resultado = service.processaArquivo(file, paginacao)
        if (resultado.sucesso){
            model.addAttribute("transacoes", service.listarTransacoes(paginacao))
            return "/home/transacoes"
        } else {
            model.addAttribute("resposta", resultado.carga)
            return "/home/uploadError"
        }

        @GetMapping("/login")
        fun login(): String{
            return "/login/login"
        }

    }
}