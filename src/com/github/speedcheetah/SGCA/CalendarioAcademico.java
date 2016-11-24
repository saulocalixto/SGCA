/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.speedcheetah.SGCA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author saulognome
 */
public class CalendarioAcademico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ArrayList eventos = new ArrayList();
        RepositorioEvento repositorio = new RepositorioEvento();
        Administrador admin = new Administrador("admin", "password");
        admin.logoff();

        System.out.println("##### Cadastro de eventos #####\n");

        int opcao = 1;
        Scanner scan = new Scanner(System.in);

        while (opcao != 0) {
            String user = null, pass = null;
            System.out.println("0 - Sair do programa.");
            System.out.println("1 - Exibir calendário inteiro.");
            System.out.println("2 - Pesquisar por regional.");
            System.out.println("3 - Pesquisar por nome.");
            System.out.println("6 - Registrar Administrador.");
            System.out.println("7 - Fazer login.");
            if (admin.isOnline()) {
                System.out.println("8 - Cadastrar evento novo.");
                System.out.println("9 - Remover um evento.");
                System.out.println("10 - Alterar senha.");
            }

            try {
                opcao = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException ex) {
                opcao = -1;
            }

            switch (opcao) {
                case 0:
                    System.out.println("Fim");
                    System.exit(0);
                    break;
                    
                case 1:
                    eventos.forEach(System.out::println);
                    break;

                case 2:
                    
                    Regionais.CATALAO.mostrarMenu();
                    int numRegional;
                    try {
                        numRegional = Integer.parseInt(scan.nextLine());
                    } catch (NumberFormatException ex) {
                        System.out.println("Número inválido.");
                        break;
                    }
                    String escolhaRegional
                            = Regionais.CATALAO.escolhaRegional(numRegional);
                    repositorio.exibirRegional(eventos, escolhaRegional);
                    break;
                    
                case 3 :
                    System.out.print("Digite o nome do evento: ");
                    String nome = scan.nextLine();
                    Evento ev = repositorio.pesquisaNome(eventos, nome);
                    System.out.println(ev.toString());
                    break;
                    
                case 6:
                    System.out.print("Confirme o usuário pré-cadastrado: ");
                    user = scan.nextLine();
                    System.out.print("Confirme a senha pré-cadastrada: ");
                    pass = scan.nextLine();
                    admin.login(user, pass);
                    if (admin.isOnline()) {
                        System.out.print("Digite o novo nome de usuário: ");
                        user = scan.nextLine();
                        System.out.print("Digite a nova senha: ");
                        pass = scan.nextLine();
                        admin = new Administrador(user, pass);
                    } else {
                        System.out.println("Usuário ou senha incorreto.");
                    }
                    break;
                    
                case 7:
                    System.out.print("Digite o nome de usuário: ");
                    user = scan.nextLine();
                    System.out.print("Digite a senha: ");
                    pass = scan.nextLine();
                    admin.login(user, pass);
                    if (admin.isOnline()) {
                        System.out.println("Bem vindo "
                                + admin.getUser() + "!");
                    } else {
                        System.out.println("Usuário ou senha incorreto.");
                    }
                    break;
                    
                case 8:
                    if (admin.isOnline()) {
                        repositorio.addEvento(eventos);
                        Collections.sort(eventos);
                    }
                    break;
                    
                case 9:
                    if (admin.isOnline()) {
                        String nomePesquisa = scan.nextLine();
                        repositorio.removeNome(eventos,nomePesquisa);
                    }
                    break;
                    
                    
                case 10:
                    if (admin.isOnline()) {
                        System.out.print("Confirme sua senha: ");
                        pass = scan.nextLine();
                        if (admin.confirmaSenha(pass)) {
                            System.out.print("Entre com a nova senha: ");
                            String newPass = scan.nextLine();
                            admin.alteraSenha(pass, newPass);
                        }
                    }
                    break;
                    
                default:
                    break;
            }

        }
        System.out.println("Fim");
        System.exit(0);
    }

}
