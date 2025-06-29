package com.integracao.prontuarioeletronico.br.com.prontuario.eletronico; 
import java.util.HashMap;
import java.util.Scanner;

import com.integracao.prontuarioeletronico.controller.ProntuarioController;
import com.integracao.prontuarioeletronico.model.Paciente;
import com.integracao.prontuarioeletronico.util.Arquivo;
import com.integracao.prontuarioeletronico.util.Validador;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProntuarioController controller = new ProntuarioController();
        Arquivo arquivo = new Arquivo(controller);
        boolean sair = false;
        
        //Exibe um menu interativo em loop até o usuário sair.
        while(!sair){
            System.out.println("-----------MENU-----------\n1 - Cadastrar\n2 - Consultar por codigo\n3 - Consultar por nome\n4 - Atualizar\n5 - Excluir\n6 - Listagem completa\n7 - Exportar relatórios\n8 - Sair\n--------------------------");
            int opcao = sc.nextInt();
            sc.nextLine();
            
            //Direciona para a opção selecionada.
            switch(opcao){
                case 1: //Fluxo de dados para cadastrar novo paciente.
                    System.out.println("Insira os dados do paciente:");
                    //Lê e valida o nome
                    System.out.print("Nome: ");
                    String nomePaciente = sc.nextLine();
                    while(!Validador.nomeValido(nomePaciente)){
                        System.out.println("Não é permitido números e caracteres especiais. Insira um nome: ");
                        nomePaciente = sc.nextLine();
                    }
                    //Lê e valida a idade
                    System.out.print("Idade: ");
                    int idadePaciente = sc.nextInt();
                    sc.nextLine();
                    while(!Validador.idadeValida(idadePaciente)){
                        System.out.println("Insira uma idade maior que 0 e menor que 110: ");
                        idadePaciente = sc.nextInt();
                        sc.nextLine();
                    }
                    //Lê e valida o CPF
                    System.out.print("CPF: ");
                    String cpfPaciente = sc.nextLine();
                    while(!Validador.cpfValido(cpfPaciente)){ 
                        System.out.println("Insira 11 números para o formato do CPF XXX.XXX.XXX-XX:");
                        cpfPaciente = sc.nextLine();
                    }
                    //Lê o diagnóstico
                    System.out.print("Diagnostico: ");
                    String diagnosticoPaciente = sc.nextLine();
                    while(diagnosticoPaciente.isEmpty()){
                        System.out.print("Preencha o campo: ");
                        diagnosticoPaciente = sc.nextLine();
                    }
                      //Cria o objeto Paciente e o adiciona ao sistema
                    Paciente p = new Paciente(nomePaciente, idadePaciente, cpfPaciente, diagnosticoPaciente);
                    controller.cadastrarPaciente(p);
                    System.out.println("Paciente cadastrado com sucesso!");
                    
                    break;
                case 2: //Buscar paciente por código de registro.
                    System.out.print("Insira o código do paciente que deseja consultar: ");
                    int codigoBusca = sc.nextInt();
                    sc.nextLine();
                    while(!controller.getHashMap().containsKey(codigoBusca)){
                        System.out.print("Paciente não encontrado. Insira um código válido: ");
                        codigoBusca = sc.nextInt();
                        sc.nextLine();
                    }
                    System.out.println(controller.buscarPorCodigo(codigoBusca));
                    break;
                case 3: // Realiza a busca de um paciente pelo nome. Verifica se está cadastrado e exibe os dados.
                    System.out.print("Insira o nome do paciente que deseja consultar: ");
                    String nomeBusca = sc.nextLine();
                    while(!controller.getHashMap().containsValue(nomeBusca)){
                        System.out.print("Paciente não encontrado. Insira um nome novamente: ");
                        nomeBusca = sc.nextLine();
                    }
                    System.out.println(controller.buscarPorNome(nomeBusca));
                    break;
                case 4: //Altera dados de um paciente.
                    System.out.print("Insira o codigo do paciente que deseja alterar: ");
                    int codigoAtualizar = sc.nextInt();
                    sc.nextLine();
                    
                    if(controller.getHashMap().containsKey(codigoAtualizar)){ //Verifica se o paciente existe
                        System.out.println("Insira os novos dados do paciente:");
                    
                        System.out.println("Nome: "); //Nome atualizado
                        String nomeAtualizado = sc.nextLine();
                        while(!Validador.nomeValido(nomeAtualizado)){
                            System.out.println("Não é permitido números e caracteres especiais. Insira um nome: ");
                            nomeAtualizado = sc.nextLine();
                        }
                        
                        System.out.println("Idade: "); //Idade atualizada
                        int idadeAtualizada = sc.nextInt();
                        sc.nextLine();
                        while(!Validador.idadeValida(idadeAtualizada)){
                            System.out.println("Insira uma idade maior que 0 e menor que 110: ");
                            idadeAtualizada = sc.nextInt();
                            sc.nextLine();
                        }
                        // CPF atualizado
                        System.out.println("CPF: ");
                        String cpfAtualizado = sc.nextLine();
                            while(!Validador.cpfValido(cpfAtualizado)){
                            System.out.println("Insira 11 números para o formato do CPF XXX.XXX.XXX-XX:");
                            cpfAtualizado = sc.nextLine();
                        }
                        // Diagnóstico atualizado
                        System.out.println("Diagnostico: ");
                        String diagnosticoAtualizado = sc.nextLine();
                        while(diagnosticoAtualizado.isEmpty()){ 
                            System.out.print("Preencha o campo: ");
                            diagnosticoAtualizado = sc.nextLine();
                        }
                          // Atualiza os dados do paciente
                        Paciente pAtualizado = new Paciente(nomeAtualizado, idadeAtualizada, cpfAtualizado, diagnosticoAtualizado);
                        controller.atualizarPaciente(codigoAtualizar, pAtualizado);
                        System.out.println("Paciente atualizado com sucesso!");
                    }else{
                        System.out.println("Paciente não existe");
                    }
         
                    break;
                    
                case 5: // Realiza a exclusão de um paciente. Solicita o código, verifica se existe e confirma a exclusão.
                    System.out.println("Insira o código do paciente que deseja excluir:");
                    int codigoExcluir = sc.nextInt();
                    sc.nextLine();
                    while(!controller.getHashMap().containsKey(codigoExcluir)){ 
                        System.out.println("Paciente não encontrado. Insira outro código: ");
                        codigoExcluir = sc.nextInt();
                        sc.nextLine();
                    }
                    System.out.println("Tem certeza de deseja excluir o paciente " + codigoExcluir + " ?");
                    String r = sc.nextLine();
                    if(r.equalsIgnoreCase("sim")){
                        controller.ExcluirPaciente(codigoExcluir);
                        System.out.println("Paciente excluido com sucesso");
                    }
                   
                    break;
                case 6: //Lista todos os pacientes.
                    controller.relatorioPacientes();
                    break;
                case 7: //Opção de expostação de dados.
                    System.out.println("Digite 1 para exportar dados de um paciente ou digite 2 para exportar dados de todos os pacientes");
                    int op = sc.nextInt();
                    sc.nextLine();
                    
                    if(op == 1){ //Exportação individual.
                        System.out.println("Insira o codigo do paciente:");
                        int pacienteCodigo = sc.nextInt();
                        sc.nextLine();
                        
                        // Verifica existência do paciente e gera o arquivo.
                        if(controller.getHashMap().containsKey(pacienteCodigo)){
                            HashMap<Integer, Paciente> pacientesPorCodigo = controller.getHashMap();
                            Paciente pacienteEncontrado = pacientesPorCodigo.get(pacienteCodigo);
                            String nomeArquivo = "paciente_" + pacientesPorCodigo.get(pacienteCodigo).getCodigo() + ".txt";
                            arquivo.exportarParaTxtPaciente(pacienteEncontrado, nomeArquivo);
                            System.out.println("Dados exportados com sucesso");
                        }else{
                            System.out.println("Paciente não encontrado");
                        }
                    }else if(op == 2){ //Exportação de todos os registros dos pacientes, ou avisa sobre opção inválida.
                        arquivo.exportarParaTxtPacientes();
                        System.out.println("Dados exportado com sucesso!");
                    }else{
                        System.out.println("Opção inválida. Insira 1 ou 2");
                    }
                    
                    break;
                case 8: //Sair do menu.
                    sair = true;
                    break;     
               
                default: //Executa quando o usuário digita uma opção inválida.
                    System.out.println("Opção inválida");
                    break;
            }   
        }
    }
}
