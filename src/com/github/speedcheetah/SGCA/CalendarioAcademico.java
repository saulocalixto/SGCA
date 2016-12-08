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

import com.github.speedcheetah.SGCA.enums.Interessados;
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
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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

            if (admin.isOnline() && opcao > 5) {
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
                        regional = RepositorioEvento.pesquisarRegional(eventos,
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
                                    ev = RepositorioEvento.
                                            pesquisarNome(regional, nomePesquisa);
                                    System.out.println(ev.toString());
                                } catch (EventoNaoLocalizadoException ex) {
                                    System.out.println(ex.getMessage());
                                }
                                waitUser();
                                break;
                            case 3:
                                System.out.print("Digite a data desejada"
                                        + "(dd/mm/aaaa - HH:mm): ");
                                GregorianCalendar dataPesquisa = cadastrarData();
                                try {
                                    pesquisaData = RepositorioEvento
                                            .pesquisarData(regional,
                                                    dataPesquisa);
                                    System.out.println(pesquisaData);
                                } catch (EventoNaoLocalizadoException
                                        | IllegalArgumentException ex) {
                                    System.out.println(ex.getMessage());
                                }
                                waitUser();
                                break;
                            case 4:
                                System.out.print("Digite o início do período"
                                        + "(dd/mm/aaaa - HH:mm): ");
                                GregorianCalendar dataPeriodoinicio
                                        = cadastrarData();
                                System.out.print("Digite o término do período"
                                        + "(dd/mm/aaaa - HH:mm): ");
                                GregorianCalendar dataPeriodofim
                                        = cadastrarData();
                                try {
                                    ArrayList<Evento> pesquisaPeriodo;
                                    pesquisaPeriodo = RepositorioEvento
                                            .pesquisarEventoPeriodo(eventos,
                                                    dataPeriodoinicio, dataPeriodofim);
                                    pesquisaPeriodo.forEach(System.out::println);
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
                        ev = RepositorioEvento.pesquisarNome(eventos, nome);
                        System.out.println(ev.toString());
                    } catch (EventoNaoLocalizadoException ex) {
                        System.out.println(ex.getMessage());
                    }
                    waitUser();
                    break;

                case 4:
                    System.out.print("Digite a data desejada"
                            + "(dd/mm/aaaa - HH:mm): ");
                    GregorianCalendar dataPesquisa = cadastrarData();
                    try {
                        pesquisaData = RepositorioEvento
                                .pesquisarData(eventos, dataPesquisa);
                        pesquisaData.forEach(System.out::println);
                    } catch (EventoNaoLocalizadoException
                            | IllegalArgumentException ex) {
                        System.out.println(ex.getMessage());
                    }
                    waitUser();
                    break;

                case 5:
                    System.out.print("Digite o início do período"
                            + "(dd/mm/aaaa - HH:mm): ");
                    GregorianCalendar dataPeriodoinicio = cadastrarData();
                    System.out.print("Digite o término do período"
                            + "(dd/mm/aaaa - HH:mm): ");
                    GregorianCalendar dataPeriodofim = cadastrarData();
                    try {
                        ArrayList<Evento> pesquisaPeriodo;
                        pesquisaPeriodo = 
                                RepositorioEvento.pesquisarEventoPeriodo(eventos,
                                        dataPeriodoinicio, dataPeriodofim);
                        pesquisaPeriodo.forEach(System.out::println);
                    } catch (EventoNaoLocalizadoException
                            | IllegalArgumentException ex) {
                        System.out.println(ex.getMessage());
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

                case 8:
                    if (admin.isOnline()) {
                        try {
                            Evento newEvento = lerEvento();
                            RepositorioEvento.addEvento(eventos, newEvento);
                            System.out.println("Evento adicionado.");
                            Collections.sort(eventos);
                        } catch (EventoDuplicadoException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    waitUser();
                    break;

                case 9:
                    if (admin.isOnline()) {
                        System.out.println("Digite o nome do evento a ser"
                                + "removido");
                        String nomePesquisa = scan.nextLine();
                        try {
                            RepositorioEvento.removerEvento(eventos, nomePesquisa);
                        } catch (EventoNaoLocalizadoException ex) {
                            System.out.println(ex.getMessage());
                        }
                        Collections.sort(eventos);
                    }
                    waitUser();
                    break;

                case 10:
                    if (admin.isOnline()) {
                        System.out.println("Digite o nome do evento a ser "
                                + "alterado");
                        String nomePesquisa = scan.nextLine();
                        try {
                            RepositorioEvento.alterarEvento(eventos,
                                    nomePesquisa);
                            Collections.sort(eventos);
                        } catch (EventoDuplicadoException
                                | EventoNaoLocalizadoException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    waitUser();
                    break;

                case 11:
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

                case 12:
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

    /**
     * O método imprime uma lista de opçãos a serem escolhidas.
     *
     * @return Retorna um numero inteiro da opção do menu de modificação.
     * @throws NumberFormatException
     */
    public static int menuModificacao() {
        System.out.println("O que deseja alterar?");
        System.out.println("0 - Terminar as alterações");
        System.out.println("1 - Nome.");
        System.out.println("2 - Data de inicio(dd/mm/aaaa - HH:mm).");
        System.out.println("3 - Data de término(dd/mm/aaaa - HH:mm).");
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

    /**
     *
     * @return @throws EventoDuplicadoException
     */
    public static Evento lerEvento() throws EventoDuplicadoException {
        String nome = cadastrarNome();

        System.out.print("Data de início(dd/mm/aaaa - HH:mm): ");
        GregorianCalendar dataI = cadastrarData();

        System.out.print("Data final(dd/mm/aaaa - HH:mm): ");
        GregorianCalendar dataFinal = cadastrarData();

        Evento evento = new Evento(nome, dataI, dataFinal, cadastrarRegional(),
                cadastrarInstituto(), cadastrarInteressado(), 
                cadastrarDescricao());

        return evento;
    }

    /**
     * Faz a leitura do nome do evento passado pelo usuario e adiciona no evento
     *
     * @return Cadastra nome do evento
     */
    public static String cadastrarNome() {
        System.out.println("Digite o nome do Evento:");
        String nome = scan.nextLine();
        return nome.toUpperCase();
    }

    /**
     * Cadastra uma regional a partir de uma lista.
     *
     * @return retorna uma lista de regionais
     * @throws EventoDuplicadoException
     */
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

            if (numRegional > 0 && numRegional < 5) {
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
            } else if (numRegional == 5) {
                ArrayList<String> todasRegionais = new ArrayList();
                todasRegionais.add(RepositorioEvento.escolhaRegional(1));
                todasRegionais.add(RepositorioEvento.escolhaRegional(2));
                todasRegionais.add(RepositorioEvento.escolhaRegional(3));
                todasRegionais.add(RepositorioEvento.escolhaRegional(4));
                return todasRegionais;
            } else {
                System.out.println("Regional não existe");
            }
        }

        return regionalList;
    }

    public static ArrayList<String> cadastrarInteressado()
            throws EventoDuplicadoException {
        String maisUm = "Sim";
        int cont = 0;
        ArrayList<String> interessadoList = new ArrayList();
        while ("Sim".equalsIgnoreCase(maisUm) && cont < 4) {

            menuInteressados();

            int numInteressados;

            try {
                numInteressados = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException ex) {
                numInteressados = -1;
            }

            if (numInteressados > 0 && numInteressados < 5) {
                String escolhaInteressado
                        = RepositorioEvento.escolhaInteressado(numInteressados);

                if (interessadoList.contains(escolhaInteressado)) {
                    throw new EventoDuplicadoException("Interessado já consta"
                            + " cadastrada para esse evento.");
                } else {
                    interessadoList.add(escolhaInteressado);
                }
                System.out.println("Deseja cadastrar mais um interessado?"
                        + " para o evento? (Sim/Nao)");
                maisUm = scan.nextLine();

                cont++;
            } else if (numInteressados == 5) {
                ArrayList<String> todosInteressados = new ArrayList();
                todosInteressados.add(RepositorioEvento.escolhaInteressado(1));
                todosInteressados.add(RepositorioEvento.escolhaInteressado(2));
                todosInteressados.add(RepositorioEvento.escolhaInteressado(3));
                todosInteressados.add(RepositorioEvento.escolhaInteressado(4));
                return todosInteressados;
            } else {
                System.out.println("Interessado não existe");
            }
        }

        return interessadoList;
    }

    /**
     * Imprime o menu das regionais.
     */
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

    public static void menuInteressados() {
        System.out.println("Escolha o interessado pelo número correspondente"
                + ": ");
        System.out.println("1. "
                + Interessados.P.getRepresentacaoTextual());
        System.out.println("2. "
                + Interessados.A.getRepresentacaoTextual());
        System.out.println("3. "
                + Interessados.S.getRepresentacaoTextual());
        System.out.println("4. "
                + Interessados.C.getRepresentacaoTextual());
        System.out.println("5. Todas os Interessados");
    }

    /**
     * Cadastra instituto e adiciona ao Evento.
     *
     * @return Adiciona o instituto no objeto Evento.
     */
    public static String cadastrarInstituto() {
        System.out.print("Instituto: ");
        String instituto = scan.nextLine();
        return instituto.toUpperCase();
    }

    /**
     * Cadastra descrição do evento.
     *
     * @return Adiciona uma descrição ao Evento.
     */
    public static String cadastrarDescricao() {
        System.out.print("Descrição: ");
        String descricao = scan.nextLine();
        return descricao.toUpperCase();
    }

    /**
     * Cadastra data do evento.
     *
     * @return A data inicial.
     * @throws NumberFormatException
     * @throws IndexOutOfBoundsException
     */
    public static GregorianCalendar cadastrarData() {

        GregorianCalendar dataInicial = new GregorianCalendar();
        dataInicial.setLenient(false);
        do {
            String data = scan.nextLine();
            try {
                List<String> dataValor = RepositorioEvento.parseData(data);
                dataInicial.set(Integer.parseInt(dataValor.get(2)),
                        Integer.parseInt(dataValor.get(1)) - 1,
                        Integer.parseInt(dataValor.get(0)),
                        Integer.parseInt(dataValor.get(3)),
                        Integer.parseInt(dataValor.get(4)));
            } catch (Exception ex) {
                dataInicial.set(-1, -1, -1, -1, -1);
                System.out.println("Reinsira a data.");
            }
        } while (!RepositorioEvento.testaData(dataInicial));

        return dataInicial;
    }

    /**
     * Verifica se a data é válida, caso não seja pede para que seja inseria
     * uma, data válida.
     *
     * @param data String da data a ser inserida.
     * @return a data inicial válida.
     */
    public static GregorianCalendar inserirData(String data) {

        GregorianCalendar dataInicial = new GregorianCalendar();
        dataInicial.setLenient(false);
        List<String> dataValor = new ArrayList();
        try {
            dataValor = RepositorioEvento.parseData(data);
        } catch (Exception ex) {
            Logger.getLogger(CalendarioAcademico.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        dataInicial.set(Integer.parseInt(dataValor.get(2)),
                        Integer.parseInt(dataValor.get(1)) - 1,
                        Integer.parseInt(dataValor.get(0)),
                        Integer.parseInt(dataValor.get(3)),
                        Integer.parseInt(dataValor.get(4)));

        return dataInicial;
    }

    /**
     * Imprime o menu do administrador.
     */
    private static void menuAdmin() {
        System.out.println("0 - Sair do programa.");
        System.out.println("1 - Exibir calendário inteiro.");
        System.out.println("2 - Pesquisar na regional.");
        System.out.println("3 - Pesquisar por nome.");
        System.out.println("4 - Pesquisar por data.");
        System.out.println("5 - Pesquisar por período.");
        System.out.println("6 - Cadastrar evento novo.");
        System.out.println("7 - Remover um evento.");
        System.out.println("8 - Alterar um evento.");
        System.out.println("9 - Alterar senha.");
        System.out.println("10 - Fazer logoff.");
    }

    /**
     * Imprime o menu do usuario normal.
     */
    private static void menuGuest() {
        System.out.println("0 - Sair do programa.");
        System.out.println("1 - Exibir calendário inteiro.");
        System.out.println("2 - Pesquisar em uma regional.");
        System.out.println("3 - Pesquisar por nome.");
        System.out.println("4 - Pesquisar por data.");
        System.out.println("5 - Pesquisar por período.");
        System.out.println("6 - Fazer login.");
    }

    /**
     * Aguarda o usuario pressionar a tecla enter e imprime uma mensagem,
     * retorna para o usuario para o menu.
     *
     */
    public static void waitUser() {
        System.out.println("\n\nPressionar a tecla \"ENTER\" retorna ao menu.");
        scan.nextLine();
        System.out.println("\n\n\n\n\n\n\n\n\n");
    }

    /**
     * Imprime o menu para a busca avançada na regional.
     */
    private static void menuBuscaRegional() {
        System.out.println("0 - Retornar ao menu principal.");
        System.out.println("1 - Exibir calendário da regional.");
        System.out.println("2 - Pesquisar por nome.");
        System.out.println("3 - Pesquisar por data.");
        System.out.println("4 - Pesquisar por período.");
    }
}
