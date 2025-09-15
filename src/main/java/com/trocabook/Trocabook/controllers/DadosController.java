package com.trocabook.Trocabook.controllers;

import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.model.dto.DashboardDTO;
import com.trocabook.Trocabook.model.dto.UsuarioDTO;
import com.trocabook.Trocabook.service.IDadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dados")
public class DadosController {
    final IDadosService dadosService;

    @Autowired
    DadosController(IDadosService dadosService){
        this.dadosService = dadosService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> obterDashboard(){
        return ResponseEntity.status(HttpStatus.OK).body(dadosService.obterDashboard());
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable int id, @RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.status(HttpStatus.OK).body(dadosService.atualizarUsuario(id, usuarioDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterUsuario(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(dadosService.obterUsuario(id));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioDTO>> obterUsuarios(){
        return ResponseEntity.status(HttpStatus.OK).body(dadosService.listarUsuarios());
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable int id){
        System.out.println(id);
        dadosService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
