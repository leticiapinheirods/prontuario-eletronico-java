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
import com.integracao.prontuarioeletronico.util.Validador;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController { //Controlador REST com endpoints da API

    private final ProntuarioController prontuarioController = new ProntuarioController();

    @GetMapping //Lista todos os pacientes cadastrados no sistema
    public ResponseEntity<List<Paciente>> listarTodosPacientes() {
        return ResponseEntity.ok(prontuarioController.getArrayList());
    }
       //Cadastra um novo paciente após validar os dados
    @PostMapping 
    public ResponseEntity<String> cadastrarPaciente(@RequestBody Paciente paciente) {
        if (!Validador.nomeValido(paciente.getNome())) { // Valida o nome do paciente
            return ResponseEntity.badRequest().body("Nome inválido.");
        }
        if (!Validador.idadeValida(paciente.getIdade())) { // Valida a idade
            return ResponseEntity.badRequest().body("Idade inválida.");
        }
        if (!Validador.cpfValido(paciente.getCpf())) { // Valida o CPF
            return ResponseEntity.badRequest().body("CPF inválido.");
        }

        prontuarioController.cadastrarPaciente(paciente); // Se passou por todas as validações, cadastra o paciente
        return ResponseEntity.status(HttpStatus.CREATED).body("Paciente cadastrado com sucesso!");
    }
        //Procura um paciente pelo código
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Paciente> buscarPorCodigo(@PathVariable int codigo) {
        Paciente paciente = prontuarioController.buscarPorCodigo(codigo);
        if (paciente != null) {
            return ResponseEntity.ok(paciente); //se achou, retorna o paciente
        } else { //se não achou, retorna erro
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
        //procura um paciente usando o nome
    @GetMapping("/nome/{nome}")
    public ResponseEntity<Paciente> buscarPorNome(@PathVariable String nome) {
        Paciente paciente = prontuarioController.buscarPorNome(nome);
        if (paciente != null) {
            return ResponseEntity.ok(paciente);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
         //Atualiza os dados de um paciente já cadastrado, com base no código.
   @PutMapping("/{codigo}")
    public ResponseEntity<String> atualizarPaciente(@PathVariable int codigo, @RequestBody Paciente paciente) {
        if (!Validador.nomeValido(paciente.getNome())) {
            return ResponseEntity.badRequest().body("Nome inválido.");
        }
        if (!Validador.idadeValida(paciente.getIdade())) {
            return ResponseEntity.badRequest().body("Idade inválida.");
        }
        if (!Validador.cpfValido(paciente.getCpf())) {
            return ResponseEntity.badRequest().body("CPF inválido.");
        }
          // Define o código do paciente com base no valor recebido na URL
        paciente.setCodigo(codigo); 
        prontuarioController.atualizarPaciente(codigo, paciente);
        return ResponseEntity.ok("Paciente atualizado com sucesso!");
    }
         //Remove o paciente com o código indicado.
    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> excluirPaciente(@PathVariable int codigo) {
        prontuarioController.ExcluirPaciente(codigo);
        return ResponseEntity.ok("Paciente excluído com sucesso!");
    }
         //Gera um relatório de todos os pacientes
    @GetMapping("/relatorio")
    public ResponseEntity<String> gerarRelatorio() {
        List<Paciente> pacientes = prontuarioController.getArrayList();
        StringBuilder sb = new StringBuilder();

        sb.append("<html><head>");
        sb.append("<meta charset=\"UTF-8\">"); // Garante UTF-8 no HTML
        sb.append("</head><body>");
        sb.append("<h2>Relatório de Pacientes</h2>");
        sb.append("<table border='1' cellpadding='5' cellspacing='0'>");
        sb.append("<tr>")
            .append("<th>ID</th>")
            .append("<th>Nome</th>")
            .append("<th>Idade</th>")
            .append("<th>CPF</th>")
            .append("<th>Diagnóstico</th>")
            .append("</tr>");

        for (Paciente p : pacientes) {
            sb.append("<tr>")
                .append("<td>").append(p.getCodigo()).append("</td>")
                .append("<td>").append(p.getNome()).append("</td>")
                .append("<td>").append(p.getIdade()).append("</td>")
                .append("<td>").append(p.getCpf()).append("</td>")
                .append("<td>").append(p.getDiagnostico()).append("</td>")
                .append("</tr>");
        }

        sb.append("</table>");
        sb.append("</body></html>");

        return ResponseEntity.ok()
                .header("Content-Type", "text/html; charset=UTF-8") // Aqui força a codificação UTF-8
                .body(sb.toString());
    }
        //Gera um arquivo .txt com os dados do paciente e envia como download
   @GetMapping("/baixar/{codigo}")
    public ResponseEntity<Void> baixarPaciente(@PathVariable int codigo, HttpServletResponse response) {
        Paciente paciente = prontuarioController.buscarPorCodigo(codigo);

        if (paciente == null) { //Se não encontrou o paciente, retorna erro
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
          //Exporta os dados do paciente para um arquivo
        Arquivo arquivo = new Arquivo(prontuarioController);
        String nomeArquivo = "paciente_" + codigo + ".txt";

        boolean sucesso = arquivo.exportarParaTxtPaciente(paciente, nomeArquivo);

        if (!sucesso) { //Se não conseguiu gerar o arquivo, retorna erro 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
         
         //Baixar um arquivo .txt com os dados do paciente. Lê o arquivo e envia como resposta ao navegador, ativando o download
        try {
            File file = new File(nomeArquivo);
            response.setContentType("text/plain"); //Define que o tipo do conteúdo é texto (.txt) e que será baixado
            response.setHeader("Content-Disposition", "attachment; filename=" + nomeArquivo);
            response.setContentLength((int) file.length());

            InputStream is = new FileInputStream(file); //Lê o arquivo e envia seu conteúdo
            StreamUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
            is.close();
            return ResponseEntity.ok().build(); // Retorna sucesso
        } catch (IOException e) { //Retorna falha
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
