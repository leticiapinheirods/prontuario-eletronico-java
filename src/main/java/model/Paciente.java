package model;
import java.io.Serializable;

public class Paciente implements Serializable {
    private static int proximoCodigo = 1;
    private int codigo;
    private String nome;
    private int idade;
    private String cpf;
    private String diagnostico;

    public Paciente(String nome, int idade, String cpf, String diagnostico){
        this.codigo = proximoCodigo++;
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.diagnostico = diagnostico;
    }

    public Paciente(){
        this.codigo = proximoCodigo++;
        this.nome = "Desconhecido";
        this.idade = 0;
        this.cpf = "00000000000";
        this.diagnostico = "Não informado";
    }

    public int getCodigo() {
        return codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getIdade() {
        return idade;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    @Override
    public String toString(){
        return String.format("%-5d | %-20s | %-5d | %-12s | %-20s",
                         codigo, nome, idade, cpf, diagnostico);
    }
}
