package com.integracao.prontuarioeletronico.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integracao.prontuarioeletronico.model.Paciente;
import com.integracao.prontuarioeletronico.util.Arquivo;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

    private final ProntuarioController prontuarioController = new ProntuarioController();

    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodosPacientes() {
        return ResponseEntity.ok(prontuarioController.getArrayList());
    }

    @PostMapping
    public ResponseEntity<String> cadastrarPaciente(@RequestBody Paciente paciente) {
        prontuarioController.cadastrarPaciente(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Paciente cadastrado com sucesso!");
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Paciente> buscarPorCodigo(@PathVariable int codigo) {
        Paciente paciente = prontuarioController.buscarPorCodigo(codigo);
        if (paciente != null) {
            return ResponseEntity.ok(paciente);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Paciente> buscarPorNome(@PathVariable String nome) {
        Paciente paciente = prontuarioController.buscarPorNome(nome);
        if (paciente != null) {
            return ResponseEntity.ok(paciente);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<String> atualizarPaciente(@PathVariable int codigo, @RequestBody Paciente paciente) {
        prontuarioController.atualizarPaciente(codigo, paciente);
        return ResponseEntity.ok("Paciente atualizado com sucesso!");
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> excluirPaciente(@PathVariable int codigo) {
        prontuarioController.ExcluirPaciente(codigo);
        return ResponseEntity.ok("Paciente excluído com sucesso!");
    }

    @GetMapping("/relatorio")
    public ResponseEntity<String> gerarRelatorio() {
        List<Paciente> pacientes = prontuarioController.getArrayList();
        StringBuilder sb = new StringBuilder();

        sb.append("ID    | Nome                 | Idade | CPF           | Diagnóstico\n");
        sb.append("---------------------------------------------------------------\n");
        for (Paciente p : pacientes) {
            sb.append(String.format("%-5d | %-20s | %-5d | %-13s | %s\n",
                    p.getCodigo(), p.getNome(), p.getIdade(), p.getCpf(), p.getDiagnostico()));
        }

        return ResponseEntity.ok(sb.toString());
    }

   @GetMapping("/baixar/{codigo}")
    public ResponseEntity<Void> baixarPaciente(@PathVariable int codigo, HttpServletResponse response) {
        Paciente paciente = prontuarioController.buscarPorCodigo(codigo);

        if (paciente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Arquivo arquivo = new Arquivo(prontuarioController);
        String nomeArquivo = "C:/Users/Letic/OneDrive/Documentos/paciente_" + codigo + ".txt";

        boolean sucesso = arquivo.exportarParaTxtPaciente(paciente, nomeArquivo);

        if (!sucesso) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        try {
            File file = new File(nomeArquivo);
            response.setContentType("text/plain");
            response.setHeader("Content-Disposition", "attachment; filename=" + nomeArquivo);
            response.setContentLength((int) file.length());

            InputStream is = new FileInputStream(file);
            StreamUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
            is.close();
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
