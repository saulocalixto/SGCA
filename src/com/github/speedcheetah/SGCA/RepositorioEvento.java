/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.speedcheetah.SGCA;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 *
 * @author saulocalixto
 */
public class RepositorioEvento {

    public void addEvento(Collection eventos) {

        try (Scanner entrada = new Scanner(System.in)) {
            //Dizem que é uma boa prática colocar o Scanner dentro do try
            String continuar = "s";
            while ("s".equalsIgnoreCase(continuar)) {
                //enquanto for igual a s.
                System.out.print("Nome: ");
                String nome = entrada.nextLine();

                GregorianCalendar dataInicial = new GregorianCalendar();
                dataInicial.clear();
                dataInicial.setLenient(false);
                do {
                    System.out.print("Data de início(ano): ");
                    int ano = Integer.parseInt(entrada.nextLine());
                    System.out.print("Data de início(mês): ");
                    int mes = Integer.parseInt(entrada.nextLine());
                    System.out.print("Data de início (dia): ");
                    int dia = Integer.parseInt(entrada.nextLine());
                    dataInicial.set(ano, mes - 1, dia);
                }while (testaData(dataInicial));

                GregorianCalendar dataFinal = new GregorianCalendar();
                dataFinal.clear();
                dataFinal.setLenient(false);
                do {
                    System.out.print("Data de término (ano): ");
                    int ano = Integer.parseInt(entrada.nextLine());
                    System.out.print("Data de término (mês): ");
                    int mes = Integer.parseInt(entrada.nextLine());
                    System.out.print("Data de término (dia): ");
                    int dia = Integer.parseInt(entrada.nextLine());
                    dataFinal.set(ano, mes - 1, dia);
                }while (testaData(dataFinal));

                System.out.print("Regional: ");
                String regional = entrada.nextLine();

                System.out.print("Instituto: ");
                String instituto = entrada.nextLine();

                System.out.print("Descrição: ");
                String descricao = entrada.nextLine();

                Eventos evento = new Eventos(nome, dataInicial, dataFinal,
                        regional, instituto, descricao);
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

    public boolean testaData(GregorianCalendar cal) {
        try{
            cal.getTime();
        } catch (Exception ex) {
            return true;
        }

        return false;
    }
}
