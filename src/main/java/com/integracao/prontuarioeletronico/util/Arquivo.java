package com.integracao.prontuarioeletronico.util;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.integracao.prontuarioeletronico.controller.ProntuarioController;
import com.integracao.prontuarioeletronico.model.Paciente;

//Classe reponsável por salvar e carregar os dados do sistema em arquivos.
public class Arquivo {
    private String pathPacientes = "C:/Users/Letic/Downloads/prontuario-eletronico-java/arquivos/pacientes.txt";
    private String pathCodigo = "C:/Users/Letic/Downloads/prontuario-eletronico-java/arquivos/codigo.txt";
    private ProntuarioController controller; 
    
    public Arquivo(ProntuarioController controller){ //Cria o gerenciador de arquivos e recebe o controlador do sistema.
        this.controller = controller; 
    }
     // Salva os dados dos pacientes e o próximo código em arquivos
    public void salvarDados() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pathPacientes))) {
            out.writeObject(controller.getHashMap());// Salva o HashMap (pacientes por código)
            out.writeObject(controller.getArrayList());// Salva a lista de pacientes
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (DataOutputStream outCodigo = new DataOutputStream(new FileOutputStream(pathCodigo))) {
            outCodigo.writeInt(Paciente.getProximoCodigo());// Salva o próximo código disponível
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carrega os dados dos pacientes e o próximo código
    public void carregarDados() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(pathPacientes))) {
            HashMap<Integer, Paciente> mapa = (HashMap<Integer, Paciente>) in.readObject();
            ArrayList<Paciente> lista = (ArrayList<Paciente>) in.readObject();
            //Atualiza no controlador
            controller.setHashMap(mapa);
            controller.setArrayList(lista);
        } catch (Exception e) {
            // Primeira execução, arquivos ainda não existem
        }

        try (DataInputStream inCodigo = new DataInputStream(new FileInputStream(pathCodigo))) {
            int codigo = inCodigo.readInt();
            Paciente.setProximoCodigo(codigo);
        } catch (Exception e) {
            Paciente.setProximoCodigo(1); // Primeira execução
        }
    }

    //Exporta os dados de um paciente individual para um arquivo de texto.
    public boolean exportarParaTxtPaciente(Paciente p, String nomeArquivo){
        try{
            PrintWriter fileOutput = new PrintWriter(nomeArquivo);
            
            fileOutput.println("ID    | Nome                 | Idade | CPF          | Diagnóstico");
            fileOutput.println("-----------------------------------------------------------------");
            fileOutput.println(p);
            fileOutput.flush();
            fileOutput.close();
            
            return true; 
        }catch(Exception e){
            e.printStackTrace();
            return false; 
        }
    }
    
      // Exporta todos os pacientes para um arquivo de texto no formato de tabela.
    public boolean exportarParaTxtPacientes(){
        
        try{
            PrintWriter fileOutput = new PrintWriter("pacientes.txt");
            ArrayList<Paciente> listaDePacientes = controller.getArrayList();
            
            fileOutput.println("ID    | Nome                 | Idade | CPF          | Diagnóstico");
            fileOutput.println("-----------------------------------------------------------------");
            for(Paciente p : listaDePacientes){
                fileOutput.println(p);
            }
            fileOutput.flush();
            fileOutput.close();
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
