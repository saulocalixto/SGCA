/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.speedcheetah.SGCA.persistencia;

import com.github.speedcheetah.SGCA.evento.Evento;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author saulocalixto
 */
public class GravarArquivo {

    public static void gravar(ArrayList<Evento> eventos) throws IOException {
        String gravar = "";

        try {
            for (Evento aux : eventos) {
                ArrayList <String> reg = aux.getRegional();
                ArrayList <String> interessado = aux.getInteressados();
                gravar += aux.getNome() + "¬" + aux.getDataInicialStr() + "¬"
                        + aux.getDataFinalStr() + "¬";
                for (Iterator itr = reg.iterator(); itr.hasNext();) {
                    gravar = gravar + itr.next() + ",";
                }
                gravar = gravar + "¬" + aux.getInstituto()+ "¬";
                for (Iterator itr = interessado.iterator(); itr.hasNext();) {
                    gravar = gravar + itr.next() + ",";
                }
                gravar = gravar + "¬" + aux.getDescricao() + "\n";
            }

            FileWriter arquivo;
            arquivo = new FileWriter(new File("./src/evento"));
            PrintWriter gravarArq = new PrintWriter(arquivo);
            gravarArq.printf(gravar);
            arquivo.close();

        } catch (IOException e) {
            throw new IOException("Não foi possível gravar o arquivo.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void gravar() throws IOException {

        try {
            FileWriter arquivo;
            arquivo = new FileWriter(new File("./src/evento"));
            PrintWriter gravarArq = new PrintWriter(arquivo);
            gravarArq.printf("");
            arquivo.close();
        } catch (IOException e) {
            throw new IOException("Não foi possível gravar o arquivo.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
