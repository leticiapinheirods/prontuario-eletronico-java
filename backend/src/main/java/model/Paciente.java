package model;
import java.io.Serializable;

//Classe que representa um paciente, contendo informações pessoais e médicas.
//E implementa Serializable para permitir salvar e carregar em arquivos.
public class Paciente implements Serializable {
    private static int proximoCodigo = 1;
    private int codigo;
    private String nome;
    private int idade;
    private String cpf;
    private String diagnostico;
    
    //Método que cria um paciente com seus dados e gera código automático.
    public Paciente(String nome, int idade, String cpf, String diagnostico){
        this.codigo = proximoCodigo++; 
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.diagnostico = diagnostico;
    }
     //Cria um paciente com valores vazios, para preencher depois.
    public Paciente(){
        this.codigo = proximoCodigo++;
        this.nome = "Desconhecido";
        this.idade = 0;
        this.cpf = "00000000000";
        this.diagnostico = "Não informado";
    }
    //Retorna o código do paciente.
    public int getCodigo() {
        return codigo;
    }
    //Atualiza o nome do paciente.
    public void setNome(String nome) {
        this.nome = nome;
    }
    //Retorna nome atual.
    public String getNome() {
        return nome;
    }
   //Atualiza idade.
    public void setIdade(int idade) {
        this.idade = idade;
    }
   //Retorna idade.
    public int getIdade() {
        return idade;
    }
    //Atualiza CPF.
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
   //Retorna CPF.
    public String getCpf() {
        return cpf;
    }
    //Atualiza diagnóstico.
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
    //Retorna diagnóstico.
    public String getDiagnostico() {
        return diagnostico;
    }
     //Formata os dados do paciente para exibição.
    @Override  
    public String toString(){
        return String.format("%-5d | %-20s | %-5d | %-12s | %-20s",
                         codigo, nome, idade, cpf, diagnostico);
    }
}
