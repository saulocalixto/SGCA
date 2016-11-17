/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    public void addEvento(ArrayList<Evento> eventos) {
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
                System.err.println("Esse produto já foi adicionado."
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

    public ArrayList cadastrarRegional() {
        String maisum = "s";
        int cont = 0;
        ArrayList<String> regionalList = new ArrayList();
        while ("s".equalsIgnoreCase(maisum) && cont < 4) {

            Regionais.CATALAO.mostrarMenu();

            int numRegional = Integer.parseInt(entrada.nextLine());

            String escolhaRegional = Regionais.CATALAO.escolhaRegional(numRegional);

            if (regionalList.contains(escolhaRegional)) {
                System.out.println("Regional já consta cadastrada para "
                        + "esse evento.");
                continue;
            }
            if (!regionalList.contains(escolhaRegional)) {
                regionalList.add(escolhaRegional);
            } else {
                System.out.println("Regional já cadastrada.");
                continue;
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
            ArrayList dataValor = parseData(data);
            try {
                dataInicial.set(Integer.parseInt(dataValor.get(2).toString()),
                        Integer.parseInt(dataValor.get(1).toString()) - 1,
                        Integer.parseInt(dataValor.get(0).toString()));
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

    public ArrayList parseData(String data) {
        ArrayList valores = new ArrayList();

        if (data.length() != 10) {
            return valores;
        }

        valores.add(data.substring(0, 2));
        valores.add(data.substring(3, 5));
        valores.add(data.substring(6, 10));

        return valores;
    }

    public void exibirRegional(ArrayList eventos, String regPesq) {

        for (Iterator itr = eventos.iterator(); itr.hasNext();) {
            Evento e = (Evento) itr.next();
            if (e.getRegional().contains(regPesq)) {
                System.out.println(e.toString());
            }
        }
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

    public void exibirNome(ArrayList eventos, String nomePesquisa) {

        for (Iterator itr = eventos.iterator(); itr.hasNext();) {
            Evento e = (Evento) itr.next();
            if (nomePesquisa.equals(e.getNome())) {
                System.out.println(e.toString());
                return;
            }
        }
        System.out.println("Evento não encontrado!");
    }
}
