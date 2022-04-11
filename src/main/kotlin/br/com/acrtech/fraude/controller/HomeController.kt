package br.com.acrtech.fraude.controller

import br.com.acrtech.fraude.model.FileModel
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.ServletContext

@Controller
class HomeController(
    val context: ServletContext
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
        println("Nome do arquivo: ${file.file?.originalFilename}")
        println("Tamanho: ${file.file?.bytes?.size} bytes")
        return "home"
    }
}