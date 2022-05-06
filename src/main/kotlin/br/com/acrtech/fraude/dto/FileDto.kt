package br.com.acrtech.fraude.dto

import org.springframework.web.multipart.MultipartFile

data class FileDto (
    val file: MultipartFile?
)