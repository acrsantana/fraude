package br.com.acrtech.fraude

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FraudeApplication

fun main(args: Array<String>) {
	runApplication<FraudeApplication>(*args)
}
