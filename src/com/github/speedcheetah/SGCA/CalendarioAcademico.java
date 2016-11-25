/*
 * The MIT License
 *
 * Copyright 2016 Instituto de Informática (UFG) - Fábrica de Software.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.speedcheetah.SGCA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
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
            System.out.println("4 - Pesquisar por data.");
            System.out.println("5 - Registrar Administrador.");
            System.out.println("6 - Fazer login.");
            if (admin.isOnline()) {
                System.out.println("7 - Cadastrar evento novo.");
                System.out.println("8 - Remover um evento.");
                System.out.println("9 - Alterar um evento.");
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
                    repositorio.pesquisarRegional(eventos, escolhaRegional);
                    break;
                    
                case 3 :
                    System.out.print("Digite o nome do evento: ");
                    String nome = scan.nextLine();
                    Evento ev = repositorio.pesquisarNome(eventos, nome);
                    if (ev != null) {
                        System.out.println(ev.toString());
                    }
                    break;
                    
                case 4:
                    System.out.print("Digite a data desejada: ");
                    String data = scan.nextLine();
                    ArrayList dataValor = repositorio.parseData(data);
                    GregorianCalendar dataPesquisa = new GregorianCalendar();
                    try {
                        dataPesquisa.set(Integer.parseInt(dataValor.get(2).toString()),
                                Integer.parseInt(dataValor.get(1).toString()) - 1,
                                Integer.parseInt(dataValor.get(0).toString()));
                    } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                        dataPesquisa.set(-1, -1, -1);
                    }
                    repositorio.pesquisarData(eventos, dataPesquisa);
                    break;
                    
                case 5:
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
                    
                case 6:
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
                    
                case 7:
                    if (admin.isOnline()) {
                        repositorio.addEvento(eventos);
                        Collections.sort(eventos);
                    }
                    break;
                    
                case 8:
                    if (admin.isOnline()) {
                        System.out.println("Digite o nome do evento a ser"
                                + "removido");
                        String nomePesquisa = scan.nextLine();
                        repositorio.removerEvento(eventos, nomePesquisa);
                        Collections.sort(eventos);
                    }
                    break;
                
                case 9:
                    if (admin.isOnline()) {
                        System.out.println("Digite o nome do evento a ser "
                                + "alterado");
                        String nomePesquisa = scan.nextLine();
                        repositorio.alterarEvento(eventos, nomePesquisa);
                        Collections.sort(eventos);
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
