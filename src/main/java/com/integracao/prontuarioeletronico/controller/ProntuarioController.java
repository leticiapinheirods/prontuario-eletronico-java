package com.integracao.prontuarioeletronico.controller;
import java.util.ArrayList;
import java.util.HashMap;

import com.integracao.prontuarioeletronico.model.Paciente;
import com.integracao.prontuarioeletronico.util.Arquivo;

public class ProntuarioController { 
    private HashMap<Integer,Paciente> pacientesPorCodigo = new HashMap<>(); //Armazena pacientes por código.
    private ArrayList<Paciente> listaDePacientes = new ArrayList<>(); //Armazena todos os pacientes para listagem e busca por nome.
    private Arquivo arquivo; //Responsável pela leitura e gravação em arquivos.
    
    //Construtor que inicializa o objeto para ler e salvar arquivos.
    public ProntuarioController(){
        this.arquivo = new Arquivo(this);
        this.arquivo.carregarDados();
    }
     // Cadastra um novo paciente no sistema
    public void cadastrarPaciente(Paciente p) {
        int codigo = Paciente.getProximoCodigo(); // Gera código automaticamente 
        p.setCodigo(codigo);
        Paciente.incrementarCodigo();
        // Armazena o paciente no HashMap e na lista
        pacientesPorCodigo.put(codigo, p);
        listaDePacientes.add(p);
        arquivo.salvarDados();
    }

    //Busca paciente por código.
    public Paciente buscarPorCodigo(int codigo){
        return pacientesPorCodigo.get(codigo);
    }
    //Busca paciente pelo nome, comparando cada nome da lista.
    public Paciente buscarPorNome(String nome){
        for(Paciente p : listaDePacientes){
            if (p.getNome().equalsIgnoreCase(nome)){
                return p; //Retorna o paciente encontrado.
            }
        }
        return null; //Retorna null se não encontrar.
    }
    //Exibe todos os pacientes cadastrados.
    public void listarPacientes(){
        for(Paciente p : listaDePacientes){
            System.out.println(p);
        }
    }
    //Atualiza dados de um paciente. Substitui tanto na lista quanto no HashMap.
    public void atualizarPaciente(int codigo, Paciente atualizado){   
        pacientesPorCodigo.put(codigo, atualizado); //Atualiza no HashMap.

        //Percorre a lista para substituir.
        for(int i = 0; i < listaDePacientes.size(); i++){
            if(listaDePacientes.get(i).getCodigo() == codigo){
                listaDePacientes.set(i, atualizado);
                break; //Para o laço após encontrar.
            }
        }

        arquivo.salvarDados();
    }
      //Exclui um paciente do sistema, removendo dos dois armazenamentos HashMap e lista.
    public void ExcluirPaciente(int codigo){
        pacientesPorCodigo.remove(codigo);
        listaDePacientes.removeIf(p -> p.getCodigo() == codigo);
        arquivo.salvarDados();
    }
    
    //Retorna a lista de pacientes.
    public ArrayList getArrayList(){
        return listaDePacientes;
    }

    public void setArrayList(ArrayList<Paciente> lista) {
        this.listaDePacientes = lista;
    }

     //Retorna pacientes por código.
    public HashMap getHashMap(){
        return pacientesPorCodigo;
    }

    public void setHashMap(HashMap<Integer, Paciente> mapa) {
        this.pacientesPorCodigo = mapa;
    }
}
