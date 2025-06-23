package util;
import model.Paciente;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import controller.ProntuarioController;

//Classe reponsável por salvar e carregar os dados do sistema em arquivos.
public class Arquivo {
    private String path = "/C:/Users/Letic/OneDrive/Documentos/NetBeansProjects/prontuario-eletronico/arquivos/arquivo.txt"; //escolher um caminho de arquivo no seu computador
    private ProntuarioController controller; 
    
    public Arquivo(ProntuarioController controller){ //Cria o gerenciador de arquivos e recebe o controlador do sistema.
        this.controller = controller; 
    }
    //Salva um paciente em arquivo binário.
    public boolean salvarArquivoPaciente(Paciente p){
        try{
            FileOutputStream arquivoPaciente = new FileOutputStream(path);
            ObjectOutputStream objGravar = new ObjectOutputStream(arquivoPaciente);
            objGravar.writeObject(p);
            objGravar.flush();
            objGravar.close();
            arquivoPaciente.flush();
            arquivoPaciente.close();
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }  
    }
    
    public void carregarArquivoPaciente(){
        try{
            FileInputStream arquivoLeitura = new FileInputStream(path);
            ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);
            System.out.println(objLeitura.readObject());
            objLeitura.close();
            arquivoLeitura.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public boolean exportarParaTxtPaciente(Paciente p){
        try{
            PrintWriter fileOutput = new PrintWriter("pacientes.txt");
            
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
    
    public boolean salvarArquivoPacientes(){
        try{
            FileOutputStream arquivoPacientes = new FileOutputStream(path);
            ObjectOutputStream objGravar = new ObjectOutputStream(arquivoPacientes);
            
            objGravar.writeObject(controller.getArrayList());
            objGravar.flush();
            objGravar.close();
            
            arquivoPacientes.flush();
            arquivoPacientes.close();
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }  
    }
    
    public void carregarArquivoPacientes(){
        try{
            FileInputStream arquivoLeitura = new FileInputStream(path);
            ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);
            
            ArrayList<Paciente> pacientesLidos = (ArrayList<Paciente>) objLeitura.readObject();
            for (Paciente p : pacientesLidos) {
                System.out.println(p);
            }

            
            objLeitura.close();
            arquivoLeitura.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
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
