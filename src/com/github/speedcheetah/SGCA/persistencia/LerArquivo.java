/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.speedcheetah.SGCA.persistencia;

import com.github.speedcheetah.SGCA.CalendarioAcademico;
import com.github.speedcheetah.SGCA.evento.Evento;
import com.github.speedcheetah.SGCA.evento.RepositorioEvento;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 *
 * @author saulocalixto
 */
public class LerArquivo {

    public static BufferedReader getBufferedReader2(String arquivo)
            throws FileNotFoundException, IOException {
        try {
        FileReader arquivoTeste = new FileReader(arquivo);
        BufferedReader br = new BufferedReader(arquivoTeste);
        return br;
        } catch (FileNotFoundException e) {
            GravarArquivo.gravar();
            return getBufferedReader2(arquivo); 
        } 
    }

    public static ArrayList<Evento> lerEventos(BufferedReader br) throws IOException {

        ArrayList<Evento> evento = new ArrayList<>();

        String nome;
        GregorianCalendar dataI;
        GregorianCalendar dataF;
        ArrayList<String> regional = new ArrayList();
        String instituto;
        String descricao;

        try {
            String linha = br.readLine();
            while (linha != null) {
                String[] atributo = linha.split("¬");
                regional.clear();
                nome = atributo[0];
                dataI = CalendarioAcademico.inserirData(atributo[1]);
                dataF = CalendarioAcademico.inserirData(atributo[2]);
                String[] reg = atributo[3].split(",");
                for (String aux : reg) {
                    regional.add(aux);
                }
                instituto = atributo[4];
                descricao = atributo[5];

                Evento ev = new Evento(nome, dataI, dataF, regional,
                        instituto, descricao);

                evento.add(ev);

                linha = br.readLine();
            }
            return evento;
        } catch (FileNotFoundException e) {

            return evento;
        }

    }

}
