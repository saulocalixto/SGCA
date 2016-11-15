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

        System.out.println("##### Cadastro de eventos #####\n");

        int opcao = 1;
        Scanner scan = new Scanner(System.in);

        while (opcao != 0) {
            System.out.println("0 - Sair do programa.");
            System.out.println("1 - Cadastrar evento novo.");
            System.out.println("2 - Exibir calendário inteiro.");
            System.out.println("3 - Pesquisar por regional.");

            try {
                opcao = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException ex) {
                opcao = 10;
            }

            switch (opcao) {
                case 0:
                    System.out.println("Fim");
                    System.exit(0);
                    break;

                case 1:
                    repositorio.addEvento(eventos, scan);
                    Collections.sort(eventos);
                    break;

                case 2:
                    eventos.forEach(System.out::println);
                    break;

                case 3:
                    Regionais regionalEnum = null;
                    System.out.println("Escolha a regional"
                            + " pelo número correspondente: ");
                    System.out.println("1. "
                            + Regionais.CATALAO.getRepresentacaoTextual());
                    System.out.println("2. "
                            + Regionais.GOIAS.getRepresentacaoTextual());
                    System.out.println("3. "
                            + Regionais.JATAI.getRepresentacaoTextual());
                    System.out.println("4. "
                            + Regionais.GOIANIA.getRepresentacaoTextual());
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

                default:
                    break;
            }

        }
        System.out.println("Fim");
        System.exit(0);
    }

}
