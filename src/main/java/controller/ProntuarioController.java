package controller;
import java.util.ArrayList;
import java.util.HashMap;
import model.Paciente;
import util.Arquivo;

public class ProntuarioController {
    private HashMap<Integer,Paciente> pacientesPorCodigo = new HashMap<>();
    private ArrayList<Paciente> listaDePacientes = new ArrayList<>();
    private Arquivo arquivo;
    
    public ProntuarioController(){
        this.arquivo = new Arquivo(this);
    }
    
    public void cadastrarPaciente(Paciente p){
        int codigo = p.getCodigo();
        pacientesPorCodigo.put(codigo, p);
        listaDePacientes.add(p);
    }
    
    public Paciente buscarPorCodigo(int codigo){
        return pacientesPorCodigo.get(codigo);
    }
    
    public Paciente buscarPorNome(String nome){
        for(Paciente p : listaDePacientes){
            if (p.getNome().equalsIgnoreCase(nome)){
                return p;
            }
        }
        return null;
    }
    
    public void listarPacientes(){
        for(Paciente p : listaDePacientes){
            System.out.println(p);
        }
    }
    
    public void atualizarPaciente(int codigo, Paciente atualizado){   
        pacientesPorCodigo.put(codigo, atualizado);
        
        for(int i = 0; i < listaDePacientes.size(); i++){
            if(listaDePacientes.get(i).getCodigo() == codigo){
                listaDePacientes.set(i, atualizado);
                break;
            }
        }
    }
    
    public void ExcluirPaciente(int codigo){
        listaDePacientes.removeIf(p -> p.getCodigo() == codigo);
    }
    
    public void relatorioPacientes(){
        System.out.println("ID    | Nome                 | Idade | CPF          | Diagnóstico");
        System.out.println("---------------------------------------------------------------");
        arquivo.salvarArquivoPacientes();
        arquivo.carregarArquivoPacientes();
    }
    
    public ArrayList getArrayList(){
        return listaDePacientes;
    }
    
    public HashMap getHashMap(){
        return pacientesPorCodigo;
    }
}