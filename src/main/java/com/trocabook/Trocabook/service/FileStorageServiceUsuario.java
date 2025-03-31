package com.trocabook.Trocabook.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceUsuario {
	

	private String uploadsUsuario = Paths.get("C:/uploads/usuario").toAbsolutePath().toString();;

	
	private String uploadsLivro = Paths.get("C:/uploads/livro").toAbsolutePath().toString();;
	
	
	public String armazenarArquivo(MultipartFile arquivo, String diretorio, String tipo) throws IOException{
		File pasta = new File(diretorio);
		if (!pasta.exists()) {
			pasta.mkdirs();
		}
		String nomeArquivo = UUID.randomUUID().toString() + "_" + arquivo.getOriginalFilename();
		String nomeCompleto = nomeArquivo.replaceAll("[^a-zA-Z0-9.-]", "-");
		Path caminho = Paths.get(diretorio, nomeCompleto);
		
		arquivo.transferTo(caminho.toFile());
		
		return "http://localhost:8080/uploads/" + tipo + "/" + nomeCompleto;
	}
	
	public String armazenarArquivoUsuario(MultipartFile arquivo) throws IOException{
		return armazenarArquivo(arquivo, uploadsUsuario, "usuario");
	}
	
	public String armazenarArquivoLivro(MultipartFile arquivo) throws IOException{
		return armazenarArquivo(arquivo, uploadsLivro, "livro");
	}
	
	public void excluirArquivo(String caminhoArquivo) {
		System.out.println("a");
        File arquivo = new File(caminhoArquivo);
        if (arquivo.exists()) {
            boolean deletado = arquivo.delete();
            if (!deletado) {
                throw new RuntimeException("Erro ao excluir o arquivo: " + caminhoArquivo);
            }
        }
    }
	
	public String getUploadsUsuario() {
		return uploadsUsuario;
	}
	
	public String getUploadsLivro() {
		return uploadsLivro;
	}

}
