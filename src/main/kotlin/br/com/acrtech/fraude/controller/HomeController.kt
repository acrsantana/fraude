package br.com.acrtech.fraude.controller

import br.com.acrtech.fraude.model.FileModel
import br.com.acrtech.fraude.service.TransacaoService
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.ServletContext

@Controller
class kHomeController(
    val context: ServletContext,
    val service: TransacaoService
) {

    @GetMapping("/")
    fun home():String{
        return "home"
    }

    @PostMapping("uploadFile")
    fun uploadFile(file: FileModel, result: BindingResult, model: ModelMap): String {
        if (result.hasErrors()){
            model.addAttribute("erro", "Erro ao carregar o arquivo")
            return "uploadError"
        }

        val resultado = service.processaArquivo(file)
        if (resultado.sucesso){
            model.addAttribute("lista", resultado.transacoes)
            return "home"
        } else {
            model.addAttribute("resposta", resultado.carga)
            return "uploadError"
        }

    }
}