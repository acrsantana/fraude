package br.com.acrtech.fraude.model

import org.springframework.web.multipart.MultipartFile

data class FileModel (
    val file: MultipartFile?
)