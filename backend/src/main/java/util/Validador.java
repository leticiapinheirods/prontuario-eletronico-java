package util;

public class Validador { //Classe responsável por validar os dados do sistema.
    public static boolean cpfValido(String cpf){ //Verifica se o CPF tem exatamente 11 números.
        return cpf.matches("\\d{11}");
    }
    
    public static boolean idadeValida(int idade){  //Verifica se a idade é válida entre 1 e 110.
        return idade > 0 && idade <= 110;
    }
    
    public static boolean nomeValido(String nome){ //Verifica se o nome contém apenas letras e espaços.
        return nome.matches("^[A-Za-zÀ-ÿ\s]+$");
    }
    
}
