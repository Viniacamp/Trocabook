package com.trocabook.Trocabook.controllers;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trocabook.Trocabook.service.FileStorageServiceUsuario;

@RestController
@RequestMapping("/uploads")
public class FileController {
	
	@Autowired
	private FileStorageServiceUsuario fs;
	
	@PostMapping("/usuario")
    public ResponseEntity<String> uploadUsuario(@RequestParam("file") MultipartFile file) {
        try {
            String filePath = fs.armazenarArquivoUsuario(file);
            return ResponseEntity.ok("Arquivo salvo em: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar arquivo.");
        }
    }
	
	@PostMapping("/livro")
    public ResponseEntity<String> uploadLivro(@RequestParam("file") MultipartFile file) {
        try {
            String filePath = fs.armazenarArquivoLivro(file);
            return ResponseEntity.ok("Arquivo salvo em: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar arquivo.");
        }
    }
	
	 @GetMapping("/{tipo}/{filename}")
	    public ResponseEntity<Resource> getFile(@PathVariable String tipo, @PathVariable String filename) {
	        try {
	            String baseDir;
	            if ("usuario".equalsIgnoreCase(tipo)) {
	                baseDir = fs.getUploadsUsuario();
	            } else if ("livro".equalsIgnoreCase(tipo)) {
	                baseDir = fs.getUploadsLivro();
	            } else {
	                return ResponseEntity.badRequest().build();
	            }

	            Path file = Paths.get(baseDir).resolve(filename);
	            Resource resource = new UrlResource(file.toUri());

	            if (resource.exists() || resource.isReadable()) {
	                return ResponseEntity.ok()
	                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
	                        .body(resource);
	            } else {
	                return ResponseEntity.notFound().build();
	            }
	        } catch (Exception e) {
	            return ResponseEntity.internalServerError().build();
	        }
	    }
	}


