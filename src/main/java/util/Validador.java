package util;

public class Validador {
    public static boolean cpfValido(String cpf){
        return cpf.matches("\\d{11}");
    }
    
    public static boolean idadeValida(int idade){
        return idade > 0 && idade <= 110;
    }
    
    public static boolean nomeValido(String nome){
        return nome.matches("^[A-Za-zÀ-ÿ\s]+$");
    }
    
}
