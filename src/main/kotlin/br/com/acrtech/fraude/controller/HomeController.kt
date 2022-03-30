package br.com.acrtech.fraude.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/")
    fun home(model:Model):String{
        model.addAttribute("variavel", "Hello, world! Cezão da Bahia broca pai.")
        return "home"
    }
}