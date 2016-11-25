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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Objects;

/**
 *
 * @author saulognome
 */
public class Evento implements Comparable<Evento> {

    private String nome;
    private GregorianCalendar dataInicial;
    private GregorianCalendar dataFinal;
    private ArrayList<String> regional;
    private String instituto;
    private String descricao;
    private String identificacaounica;

    public Evento(String nome, GregorianCalendar dataInicial,
            GregorianCalendar dataFinal, ArrayList<String> regional,
            String instituto, String descricao) {
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
        retorno.append("\n**********************************")
                .append("\nNome do Evento: ")
                .append(nome)
                .append("\nData do Evento: ")
                .append(formato.format(dataInicial.getTime()));
        if ((dataInicial.compareTo(dataFinal)) != 0) {
            retorno.append(" - ")
                    .append(formato.format(dataFinal.getTime()));
        }
        retorno.append("\nRegional(ais): ");
        int cont = 1;
        for (Iterator itr = regional.iterator(); itr.hasNext();) {
            retorno.append("\n")
                    .append(cont).append(". ")
                    .append(itr.next());
            cont++;
        }
        retorno.append("\nInstituto sede: ").append(instituto)
                .append("\nDescrição: ").append(descricao).append("\n")
                .append("**********************************");

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
        final Evento other = (Evento) obj;
        if (!Objects.equals(this.identificacaounica, other.identificacaounica)) {
            return false;
        }
        return true;
    }

    @Override
    final public int compareTo(Evento evento) {
        return this.dataInicial.compareTo(evento.dataInicial);
    }
    
    public ArrayList<String> getRegional() {
        return this.regional;
    }

    public String getRegional(int index) {
        return regional.get(index);
    }
    
    public GregorianCalendar getDataInicial() {
        return dataInicial;
    }

    public GregorianCalendar getDataFinal() {
        return dataFinal;
    }

    public int tamRegional() {
        return regional.size();
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public void setNome(String nome) {  
        this.nome = nome;  
    }  
    
    public void setDataInicio(GregorianCalendar dataInicial) {  
        this.dataInicial = dataInicial;  
    }  
    
    public void setDataFinal(GregorianCalendar dataFinal) {  
        this.dataFinal = dataFinal;  
    }  
    
    public void setInstituto(String instituto) {  
        this.instituto = instituto;  
    }
    
    public void setDescricao(String descricao) {  
        this.descricao = descricao;  
    }  
    
    public void setRegional(ArrayList regional){
        this.regional = regional;
    }
}