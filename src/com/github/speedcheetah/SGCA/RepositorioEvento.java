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
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author saulocalixto
 */
public class RepositorioEvento {

    Scanner entrada = new Scanner(System.in);

    public void addEvento(ArrayList<Evento> eventos) throws EventoDuplicadoException{
        String continuar = "s";
        while ("s".equalsIgnoreCase(continuar)) {
            //enquanto for igual a s.

            String nome = cadastrarNome();

            System.out.print("Data de início(dd/mm/aaaa): ");
            GregorianCalendar dataI = cadastrarData();

            System.out.print("Data de final(dd/mm/aaaa): ");
            GregorianCalendar dataFinal = cadastrarData();

            Evento evento = new Evento(nome, dataI, dataFinal,
                    cadastrarRegional(), cadastrarInstituto(),
                    cadastrarDescricao());
            //Aqui eu instancio a classe eventos para adicionar os dados.
            if (eventos.contains(evento)) {
                throw new EventoDuplicadoException("Esse produto já foi adicionado."
                        + " Cadastre outro Evento!");
            } else {
                eventos.add(evento);
                System.out.println("Evento adicionado.");
            }

            System.out.print("Deseja adicionar mais algum evento? (s/n) ");

            continuar = entrada.nextLine();
        }
    }

    public String cadastrarNome() {
        System.out.println("Digite o nome do Evento:");
        String nome = entrada.nextLine();
        return nome.toUpperCase();
    }

    public ArrayList cadastrarRegional() throws EventoDuplicadoException {
        String maisum = "s";
        int cont = 0;
        ArrayList<String> regionalList = new ArrayList();
        while ("s".equalsIgnoreCase(maisum) && cont < 4) {

            Regionais.CATALAO.mostrarMenu();

            int numRegional = Integer.parseInt(entrada.nextLine());

            String escolhaRegional = Regionais.CATALAO.escolhaRegional(numRegional);

            if (regionalList.contains(escolhaRegional)) {
                throw new EventoDuplicadoException("Regional já consta"
                        + " cadastrada para esse evento.");
            }
            if (!regionalList.contains(escolhaRegional)) {
                regionalList.add(escolhaRegional);
            } else {
                throw new EventoDuplicadoException("Regional já cadastrada.");
            }
            System.out.println("Deseja cadastrar mais uma regional"
                    + " para o evento:?");
            maisum = entrada.nextLine();

            cont++;
        }

        return regionalList;
    }

    public String cadastrarInstituto() {
        System.out.print("Instituto: ");
        String instituto = entrada.nextLine();
        return instituto.toUpperCase();
    }

    public String cadastrarDescricao() {
        System.out.print("Descrição: ");
        String descricao = entrada.nextLine();
        return descricao.toUpperCase();
    }

    public GregorianCalendar cadastrarData() {

        GregorianCalendar dataInicial = new GregorianCalendar();
        dataInicial.setLenient(false);
        do {
            String data = entrada.nextLine();
            String[] dataValor = parseData(data);
            try {
                dataInicial.set(Integer.parseInt(dataValor[2]),
                        Integer.parseInt(dataValor[1]) - 1,
                        Integer.parseInt(dataValor[0]));
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                dataInicial.set(-1, -1, -1);
            }
        } while (testaData(dataInicial));

        return dataInicial;
    }

    private boolean testaData(GregorianCalendar cal) {
        try {
            cal.getTime();
        } catch (Exception ex) {
            return true;
        }

        return false;
    }

    public String[] parseData(String data) {
        String valores[];

        valores = data.split("/");

        return valores;
    }

    public ArrayList<Evento> pesquisarRegional(ArrayList eventos, String regPesq)
            throws EventoNaoLocalizadoException {
        ArrayList<Evento> eventosRegionais = new ArrayList();
        for (Iterator itr = eventos.iterator(); itr.hasNext();) {
            Evento e = (Evento) itr.next();
            if (e.getRegional().contains(regPesq)) {
                eventosRegionais.add(e);
            }
        }
        if (eventosRegionais.isEmpty()) {
            throw new EventoNaoLocalizadoException();
        }
        return eventosRegionais;
    }

    public ArrayList<String> parseString(String s) {
        ArrayList<String> palavras = new ArrayList();
        if (s.contains(",")) {
            String[] tempPalavra = s.trim().split(",");
            palavras.addAll(Arrays.asList(tempPalavra));
        } else {
            palavras.add(s.trim());
        }
        return palavras;
    }

    public Evento pesquisarNome(ArrayList eventos, String nomePesquisa)
            throws RuntimeException{

        for (Iterator itr = eventos.iterator(); itr.hasNext();) {
            Evento e = (Evento) itr.next();
            if (nomePesquisa.compareToIgnoreCase(e.getNome()) == 0) {
                return e;
            }
        }
        throw new RuntimeException("Evento não encontrado.");
    }
    
    public void pesquisarData(ArrayList eventos, GregorianCalendar dataPesquisa) {

        for (Iterator itr = eventos.iterator(); itr.hasNext();) {
            Evento e = (Evento) itr.next();
            if ((dataPesquisa.after(e.getDataInicial())
                    && dataPesquisa.before(e.getDataFinal()))
                    || dataPesquisa.equals(e.getDataInicial())
                    || dataPesquisa.equals(e.getDataFinal())) {
                System.out.println(e.toString());
            }
        }
    }
    
    public void removerEvento (ArrayList eventos, String nomePesquisa) {
        Evento e = pesquisarNome (eventos,nomePesquisa);
        eventos.remove(e);
    }

    public void alterarEvento (ArrayList eventos, String nomePesquisa)
            throws EventoDuplicadoException {
        int opcao = 1;
        while (opcao != 0) {
            CalendarioAcademico.menuModificacao();
            
            try {
                opcao = Integer.parseInt(entrada.nextLine());
            } catch (NumberFormatException ex) {
                opcao = -1;
            }
            Evento mod = pesquisarNome(eventos, nomePesquisa);
            int index = eventos.indexOf(mod);
            Evento e = (Evento) eventos.get(index);

            
            switch (opcao) {
                case 1:
                    String novoNome = cadastrarNome();
                    e.setNome(novoNome);
                    break;
                    
                case 2:
                    GregorianCalendar novaDataI = cadastrarData();
                    e.setDataInicio(novaDataI);
                    break;
                
                case 3:
                    GregorianCalendar novaDataF = cadastrarData();
                    e.setDataFinal(novaDataF);
                    break;
                    
                case 4:
                    ArrayList<String> novaRegionalList = cadastrarRegional();
                    e.setRegional (novaRegionalList);
                    break;
                
                case 5:
                    String novoInstituto = cadastrarInstituto();
                    e.setInstituto(novoInstituto);
                    break;
                
                case 6:
                    String novaDescricao = cadastrarDescricao();
                    e.setDescricao(novaDescricao);
                    break;
                
                default:
                    break;
            }
        eventos.set(index, e);
        } 
    }
}
