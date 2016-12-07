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

import com.github.speedcheetah.SGCA.usuario.Administrador;
import com.github.speedcheetah.SGCA.enums.Regional;
import com.github.speedcheetah.SGCA.exception.EventoNaoLocalizadoException;
import com.github.speedcheetah.SGCA.exception.EventoDuplicadoException;
import com.github.speedcheetah.SGCA.evento.RepositorioEvento;
import com.github.speedcheetah.SGCA.evento.Evento;
import com.github.speedcheetah.SGCA.persistencia.LerArquivo;
import com.github.speedcheetah.SGCA.persistencia.GravarArquivo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 *
 * @author saulognome
 */
public class CalendarioAcademico {

    public static Scanner scan = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        ArrayList eventos = new ArrayList();
        RepositorioEvento repositorio = new RepositorioEvento();
        Administrador admin = new Administrador("admin", "password");
        admin.logoff();

        eventos = LerArquivo.lerEventos(LerArquivo.
                getBufferedReader2("./src/evento"));

        System.out.println("Bem vindo ao"
                + " Sistema de Gestão de Calendário Acadêmico.\n");

        int opcao = 1;

        while (opcao != 0) {
            String user, pass;
            if (admin.isOnline()) {
                menuAdmin();
            } else {
                menuGuest();
            }

            try {
                opcao = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException ex) {
                opcao = -1;
            }

            if (admin.isOnline() && opcao > 4) {
                opcao += 2;
            }

            System.out.println("\n\n\n");

            switch (opcao) {
                case 0:
                    GravarArquivo.gravar(eventos);
                    System.out.println("Fim");
                    System.exit(0);
                    break;

                case 1:
                    if (eventos.isEmpty()) {
                        System.out.println("Calendário vazio.");
                    } else {
                        eventos.forEach(System.out::println);
                    }
                    waitUser();
                    break;

                case 2:
                    menuRegionais();
                    int numRegional;
                    try {
                        numRegional = Integer.parseInt(scan.nextLine());
                    } catch (NumberFormatException ex) {
                        break;
                    }
                    String escolhaRegional
                            = RepositorioEvento.escolhaRegional(numRegional);
                    ArrayList<Evento> regional;
                    try {
                        regional = repositorio.pesquisarRegional(eventos,
                                escolhaRegional);
                    } catch (EventoNaoLocalizadoException ex) {
                        System.out.println(ex.getMessage());
                        waitUser();
                        break;
                    }
                    ArrayList<Evento> pesquisaData;
                    int opcaoDeBusca = -1;
                    while (opcaoDeBusca != 0) {
                        System.out.println("Escolha o modo de busca:");
                        menuBuscaRegional();
                        try {
                            opcaoDeBusca = Integer.parseInt(scan.nextLine());
                        } catch (NumberFormatException ex) {
                            opcaoDeBusca = -1;
                        }
                        switch (opcaoDeBusca) {
                            case 0:
                                waitUser();
                                break;
                            case 1:
                                regional.forEach(System.out::println);
                                waitUser();
                                break;
                            case 2:
                                System.out.println("Digite"
                                        + " o nome do evento:");
                                String nomePesquisa = scan.nextLine();
                                Evento ev;
                                try {
                                    ev = repositorio.
                                            pesquisarNome(regional, nomePesquisa);
                                    System.out.println(ev.toString());
                                } catch (EventoNaoLocalizadoException ex) {
                                    System.out.println(ex.getMessage());
                                }
                                waitUser();
                                break;
                            case 3:
                                System.out.print("Digite a data desejada: ");
                                GregorianCalendar dataPesquisa = cadastrarData();
                                try {
                                    pesquisaData = repositorio
                                            .pesquisarData(regional, dataPesquisa);
                                    System.out.println(pesquisaData);
                                } catch (EventoNaoLocalizadoException
                                        | IllegalArgumentException ex) {
                                    System.out.println(ex.getMessage());
                                }
                                waitUser();
                                break;
                            default:
                                break;
                        }
                    }
                    break;

                case 3:
                    System.out.print("Digite o nome do evento: ");
                    String nome = scan.nextLine();
                    Evento ev;
                    try {
                        ev = repositorio.pesquisarNome(eventos, nome);
                        System.out.println(ev.toString());
                    } catch (EventoNaoLocalizadoException ex) {
                        System.out.println(ex.getMessage());
                    }
                    waitUser();
                    break;

                case 4:
                    System.out.print("Digite a data desejada: ");
                    GregorianCalendar dataPesquisa = cadastrarData();
                    try {
                        pesquisaData = repositorio.pesquisarData(eventos, dataPesquisa);
                        pesquisaData.forEach(System.out::println);
                    } catch (EventoNaoLocalizadoException 
                            | IllegalArgumentException ex) {
                        System.out.println(ex.getMessage());
                    }
                    waitUser();
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
                    waitUser();
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
                    waitUser();
                    break;

                case 7:
                    if (admin.isOnline()) {
                        try {
                            Evento newEvento = lerEvento();
                            repositorio.addEvento(eventos, newEvento);
                            System.out.println("Evento adicionado.");
                            Collections.sort(eventos);
                        } catch (EventoDuplicadoException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    waitUser();
                    break;

                case 8:
                    if (admin.isOnline()) {
                        System.out.println("Digite o nome do evento a ser"
                                + "removido");
                        String nomePesquisa = scan.nextLine();
                        try {
                            repositorio.removerEvento(eventos, nomePesquisa);
                        } catch (EventoNaoLocalizadoException ex) {
                            System.out.println(ex.getMessage());
                        }
                        Collections.sort(eventos);
                    }
                    waitUser();
                    break;

                case 9:
                    if (admin.isOnline()) {
                        System.out.println("Digite o nome do evento a ser "
                                + "alterado");
                        String nomePesquisa = scan.nextLine();
                        try {
                            repositorio.alterarEvento(eventos, nomePesquisa);
                            Collections.sort(eventos);
                        } catch (EventoDuplicadoException
                                | EventoNaoLocalizadoException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    waitUser();
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
                    waitUser();
                    break;

                case 11:
                    if (admin.isOnline()) {
                        admin.logoff();
                    }
                    waitUser();
                    break;

                default:
                    break;
            }

        }
        System.out.println("Obrigado por usar nosso"
                + " Sistema de Gestão de Calendário Acadêmico!");
        System.exit(0);
    }

    public static int menuModificacao() {
        System.out.println("O que deseja alterar?");
        System.out.println("0 - Terminar as alterações");
        System.out.println("1 - Nome.");
        System.out.println("2 - Data de inicio.");
        System.out.println("3 - Data de término.");
        System.out.println("4 - Regional.");
        System.out.println("5 - Instituto.");
        System.out.println("6 - Descrição.");

        int opcao;
        try {
            opcao = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException ex) {
            opcao = -1;
        }
        return opcao;
    }

    public static Evento lerEvento() throws EventoDuplicadoException {
        String nome = cadastrarNome();

        System.out.print("Data de início(dd/mm/aaaa): ");
        GregorianCalendar dataI = cadastrarData();

        System.out.print("Data de final(dd/mm/aaaa): ");
        GregorianCalendar dataFinal = cadastrarData();

        Evento evento = new Evento(nome, dataI, dataFinal,
                cadastrarRegional(), cadastrarInstituto(),
                cadastrarDescricao());

        return evento;
    }

    public static String cadastrarNome() {
        System.out.println("Digite o nome do Evento:");
        String nome = scan.nextLine();
        return nome.toUpperCase();
    }

    public static ArrayList<String> cadastrarRegional()
            throws EventoDuplicadoException {
        String maisUm = "Sim";
        int cont = 0;
        ArrayList<String> regionalList = new ArrayList();
        while ("Sim".equalsIgnoreCase(maisUm) && cont < 4) {

            menuRegionais();

            int numRegional;

            try {
                numRegional = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException ex) {
                numRegional = -1;
            }
            
            if (numRegional == 5) {
                regionalList.add(RepositorioEvento.escolhaRegional(1));
                regionalList.add(RepositorioEvento.escolhaRegional(2));
                regionalList.add(RepositorioEvento.escolhaRegional(3));
                regionalList.add(RepositorioEvento.escolhaRegional(4));
                cont = 5;
            }
            else if (numRegional > 0 && numRegional < 5){
                String escolhaRegional
                        = RepositorioEvento.escolhaRegional(numRegional);

                if (regionalList.contains(escolhaRegional)) {
                    throw new EventoDuplicadoException("Regional já consta"
                            + " cadastrada para esse evento.");
                } else {
                    regionalList.add(escolhaRegional);
                }
                System.out.println("Deseja cadastrar mais uma regional"
                        + " para o evento? (Sim/Nao)");
                maisUm = scan.nextLine();

                cont++;
            }
            else {
                System.out.println("Regional não existe");
            }
        }

        return regionalList;
    }

    public static void menuRegionais() {
        System.out.println("Escolha a regional pelo número correspondente"
                + ": ");
        System.out.println("1. "
                + Regional.CATALAO.getRepresentacaoTextual());
        System.out.println("2. "
                + Regional.GOIAS.getRepresentacaoTextual());
        System.out.println("3. "
                + Regional.JATAI.getRepresentacaoTextual());
        System.out.println("4. "
                + Regional.GOIANIA.getRepresentacaoTextual());
        System.out.println("5. Todas as regionais");
    }

    public static String cadastrarInstituto() {
        System.out.print("Instituto: ");
        String instituto = scan.nextLine();
        return instituto.toUpperCase();
    }

    public static String cadastrarDescricao() {
        System.out.print("Descrição: ");
        String descricao = scan.nextLine();
        return descricao.toUpperCase();
    }

    public static GregorianCalendar cadastrarData() {

        GregorianCalendar dataInicial = new GregorianCalendar();
        dataInicial.setLenient(false);
        do {
            String data = scan.nextLine();
            String[] dataValor = RepositorioEvento.parseData(data);
            try {
                dataInicial.set(Integer.parseInt(dataValor[2]),
                        Integer.parseInt(dataValor[1]) - 1,
                        Integer.parseInt(dataValor[0]));
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                dataInicial.set(-1, -1, -1);
                System.out.println("Reinsira a data.");
            }
        } while (!RepositorioEvento.testaData(dataInicial));

        return dataInicial;
    }

    public static GregorianCalendar inserirData(String data) {

        GregorianCalendar dataInicial = new GregorianCalendar();
        dataInicial.setLenient(false);
        do {
            String[] dataValor = RepositorioEvento.parseData(data);
            try {
                dataInicial.set(Integer.parseInt(dataValor[2]),
                        Integer.parseInt(dataValor[1]) - 1,
                        Integer.parseInt(dataValor[0]));
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                dataInicial.set(-1, -1, -1);
                System.out.println("Reinsira a data.");
            }
        } while (!RepositorioEvento.testaData(dataInicial));

        return dataInicial;
    }

    private static void menuAdmin() {
        System.out.println("0 - Sair do programa.");
        System.out.println("1 - Exibir calendário inteiro.");
        System.out.println("2 - Pesquisar na regional.");
        System.out.println("3 - Pesquisar por nome.");
        System.out.println("4 - Pesquisar por data.");
        System.out.println("5 - Cadastrar evento novo.");
        System.out.println("6 - Remover um evento.");
        System.out.println("7 - Alterar um evento.");
        System.out.println("8 - Alterar senha.");
        System.out.println("9 - Fazer logoff.");
    }

    private static void menuGuest() {
        System.out.println("0 - Sair do programa.");
        System.out.println("1 - Exibir calendário inteiro.");
        System.out.println("2 - Pesquisar na regional.");
        System.out.println("3 - Pesquisar por nome.");
        System.out.println("4 - Pesquisar por data.");
        System.out.println("5 - Registrar Administrador.");
        System.out.println("6 - Fazer login.");
    }

    public static void waitUser() {
        System.out.println("\n\nPressionar a tecla \"ENTER\" retorna ao menu.");
        scan.nextLine();
        System.out.println("\n\n\n\n\n\n\n\n\n");
    }

    private static void menuBuscaRegional() {
        System.out.println("0 - Retornar ao menu principal.");
        System.out.println("1 - Exibir calendário da regional.");
        System.out.println("2 - Pesquisar por nome.");
        System.out.println("3 - Pesquisar por data.");
    }
}
