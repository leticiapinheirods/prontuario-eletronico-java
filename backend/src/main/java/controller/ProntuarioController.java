package controller;
import java.util.ArrayList;
import java.util.HashMap;
import model.Paciente;
import util.Arquivo;

public class ProntuarioController { 
    private HashMap<Integer,Paciente> pacientesPorCodigo = new HashMap<>(); //Armazena pacientes por código.
    private ArrayList<Paciente> listaDePacientes = new ArrayList<>(); //Armazena todos os pacientes para listagem e busca por nome.
    private Arquivo arquivo; //Responsável pela leitura e gravação em arquivos.
    
    //Construtor que inicializa o objeto para ler e salvar arquivos.
    public ProntuarioController(){
        this.arquivo = new Arquivo(this);
    }
    /**
     * Cadastra um paciente no sistema.
     * Salva tanto na lista quanto no HashMap para garantir
     * que ele seja encontrado tanto por nome quanto por código.
     */
    public void cadastrarPaciente(Paciente p){
        int codigo = p.getCodigo();
        pacientesPorCodigo.put(codigo, p);
        listaDePacientes.add(p);
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
    }
      //Exclui um paciente do sistema, removendo dos dois armazenamentos HashMap e lista.
    public void ExcluirPaciente(int codigo){
        listaDePacientes.removeIf(p -> p.getCodigo() == codigo);
    }
     //Gera relatório completo do paciente.
    public void relatorioPacientes(){
        System.out.println("ID    | Nome                 | Idade | CPF          | Diagnóstico");
        System.out.println("---------------------------------------------------------------");
        arquivo.salvarArquivoPacientes();
        arquivo.carregarArquivoPacientes();
    }
    //Retorna a lista de pacientes.
    public ArrayList getArrayList(){
        return listaDePacientes;
    }
     //Retorna pacientes por código.
    public HashMap getHashMap(){
        return pacientesPorCodigo;
    }
}
