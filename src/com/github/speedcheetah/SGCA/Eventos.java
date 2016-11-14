/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.speedcheetah.SGCA;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 *
 * @author saulognome
 */
public class Eventos implements Comparable<Eventos> {

    private String nome;
    private GregorianCalendar dataInicial;
    private GregorianCalendar dataFinal;
    private String regional;
    private String instituto;
    private String descricao;
    private String identificacaounica;

    public Eventos(String nome, GregorianCalendar dataInicial,
            GregorianCalendar dataFinal, String regional, String instituto,
            String descricao) {
        this.nome = nome;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.regional = regional;
        this.instituto = instituto;
        this.descricao = descricao;
        identificacaounica = nome + dataInicial + instituto;
    }

    @Override
    final public String toString() {
        StringBuilder retorno = new StringBuilder();
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        retorno.append("\n*").append("\nNome do Evento: ").append(nome);
        if(dataInicial.compareTo(dataFinal) == 0){
            retorno.append("\nData do Evento: ")
                    .append(formato.format(dataInicial.getTime()))
                    .append("\nRegional: ").append(regional)
                    .append("\nInstituto sede: ").append(instituto)
                    .append("\nDescrição: ").append(descricao).append("\n");
        } else {
            retorno.append("\nData de Ínicio do Evento: ")
                    .append(formato.format(dataInicial.getTime()))
                    .append("\nData de Término do Evento: ")
                    .append(formato.format(dataFinal.getTime()))
                    .append("\nRegional: ").append(regional)
                    .append("\nInstituto sede: ").append(instituto)
                    .append("\nDescrição: ").append(descricao).append("\n");
        }
        return retorno.toString();
    }

    //esse método que sobreescreve o método hashCode seria útil no caso de
    //usarmos um hashmap ou um hashset. Ele cria um código único para cada
    //identificação única.
    @Override
    final public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.identificacaounica);
        return hash;
    }

    @Override
    final public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Eventos other = (Eventos) obj;
        if (!Objects.equals(this.identificacaounica, other.identificacaounica)) {
            return false;
        }
        return true;
    }

    @Override
    final public int compareTo(Eventos evento) {
        return this.dataInicial.compareTo(evento.dataInicial);
    }

}
