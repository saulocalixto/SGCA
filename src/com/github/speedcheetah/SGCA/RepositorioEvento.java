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

    public void addEvento(ArrayList<Evento> eventos) {
        try (Scanner entrada = new Scanner(System.in)) {
            //Dizem que é uma boa prática colocar o Scanner dentro do try
            String continuar = "s";
            while ("s".equalsIgnoreCase(continuar)) {
                //enquanto for igual a s.
                System.out.print("Nome: ");
                String nome = entrada.nextLine();

                GregorianCalendar dataInicial = new GregorianCalendar();
                dataInicial.setLenient(false);
                do {
                    System.out.print("Data de início(dd/mm/aaaa): ");
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

                GregorianCalendar dataFinal = new GregorianCalendar();
                dataFinal.setLenient(false);
                do {
                    System.out.print("Data de término (dd/mm/aaaa): ");
                    String data = entrada.nextLine();
                    ArrayList dataValor = parseData(data);
                    try {
                        dataFinal.set(Integer.parseInt(dataValor.get(2).toString()),
                                Integer.parseInt(dataValor.get(1).toString()) - 1,
                                Integer.parseInt(dataValor.get(0).toString()));
                    } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                        dataFinal.set(-1, -1, -1);
                    }
                } while (testaData(dataFinal));

                System.out.print("Regional: ");
                String regional = entrada.nextLine();
                ArrayList<String> regionalList = parseString(regional);

                System.out.print("Instituto: ");
                String instituto = entrada.nextLine();

                System.out.print("Descrição: ");
                String descricao = entrada.nextLine();

                Evento evento = new Evento(nome, dataInicial, dataFinal,
                        regionalList, instituto, descricao);
                //Aqui eu instancio a classe eventos para adicionar os dados.
                if (eventos.contains(evento)) {
                    System.err.println("Esse produto já foi adicionado."
                            + " Utilize outro Evento!");
                } else {
                    eventos.add(evento);
                    System.out.println("Evento adicionado.");
                }

                System.out.print("Deseja adicionar mais algum evento? (s/n) ");

                continuar = entrada.nextLine();
            }
        }
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

    public void exibirRegional(ArrayList eventos, ArrayList<String> regPesq) {

        for (Iterator itr = eventos.iterator(); itr.hasNext();) {
            Evento e = (Evento) itr.next();
            for (Iterator itrReg = regPesq.iterator(); itrReg.hasNext();) {
                String reg = (String) itrReg.next();
                if (e.getRegional().contains(reg)) {
                    System.out.println(e.toString());
                    break;
                }
            }
        }
    }
    
    public ArrayList<String> parseString(String s) {
        ArrayList<String> palavras = new ArrayList();
        if (s.contains(",")){
            String[] tempPalavra = s.trim().split(",");
            palavras.addAll(Arrays.asList(tempPalavra));
        } else {
            palavras.add(s.trim());
        }
        return palavras;
    }

}
