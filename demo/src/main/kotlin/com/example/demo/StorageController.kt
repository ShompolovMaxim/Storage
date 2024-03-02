package com.example.demo

import lombok.AllArgsConstructor
import org.springframework.web.bind.annotation.*
import com.example.demo.File
import com.example.demo.FileDb
import com.example.demo.InputFile

@RestController
@AllArgsConstructor
@RequestMapping("/storage")
class StorageController {
    @PostMapping
    fun createProduct(@RequestBody file: InputFile): String {
        val fileForDb = File(System.currentTimeMillis().toString() + "." + file.type, file.type, file.data)
        FileDb.create()
        FileDb.insert(fileForDb)
        return fileForDb.name
    }

    @GetMapping("/{name}")
    fun getProductById(@PathVariable name: String): File {
        FileDb.create()
        return FileDb.get(name) ?: throw RuntimeException("File with such name is not found")
    }
}
