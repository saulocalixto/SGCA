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
package com.github.speedcheetah.SGCA.evento;

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
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Adiciona os atributos necessarios a classe.
     *
     * @param nome String com o nome a ser dado ao evento.
     * @param dataInicial GregorianCalendar indicando a data inicial do evento.
     * @param dataFinal GregorianCalendar Indicando a data final do evento.
     * @param regional Dentre uma lista define uma regional.
     * @param instituto String define um instituto interessado pelo evento.
     * @param descricao String descrições e demais informações que possam ser
     * relevantes.
     */
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

    /**
     * Sobreescreve o método para converter atributos para String.
     *
     * @return String do atributo em questão.
     */
    @Override
    final public String toString() {
        StringBuilder retorno = new StringBuilder();
        retorno.append("\n**********************************")
                .append("\nNome do Evento: ")
                .append(nome)
                .append("\nData do Evento: ")
                .append(formato.format(dataInicial.getTime()))
                .append(" - ").append(formato.format(dataFinal.getTime()))
                .append("\nRegional(ais): ");
        for (Iterator itr = regional.iterator(); itr.hasNext();) {
            retorno.append("\n")
                    .append(itr.next());
        }
        retorno.append("\nInstituto sede: ").append(instituto)
                .append("\nDescrição: ").append(descricao).append("\n")
                .append("**********************************");

        return retorno.toString();
    }

    //esse método que sobreescreve o método hashCode seria útil no caso de
    //usarmos um hashmap ou um hashset. Ele cria um código único para cada
    //identificação única.
    /**
     * Sobrescreve o método hashCode, cria um código de identificação única.
     *
     * @return a identificação unica.
     */
    @Override
    final public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.identificacaounica);
        return hash;
    }

    /**
     *
     *
     * @param obj
     * @return
     */
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

    /**
     * Sobreescreve o método compareTo para comparação entre datas no evento.
     *
     * @param evento
     * @return a comparação entre as datas.
     */
    @Override
    final public int compareTo(Evento evento) {
        return this.dataInicial.compareTo(evento.dataInicial);
    }

    /**
     * Recupera a regional.
     *
     * @return ArrayList com a regional.
     */
    public ArrayList<String> getRegional() {
        return this.regional;
    }

    /**
     * Recupera uma posição especifica da lista e a retorna.
     *
     * @param index posição na lista
     * @return verifica a posição e retorna a posição solicitada.
     */
    public String getRegional(int index) {
        return regional.get(index);
    }

    /**
     * Recupera e retorna a data inicial do evento.
     *
     * @return A data inicial do evento.
     */
    public GregorianCalendar getDataInicial() {
        return dataInicial;
    }

    public String getDataInicialStr() {
        return formato.format(dataInicial.getTime());
    }

    public String getDataFinalStr() {
        return formato.format(dataFinal.getTime());
    }

    /**
     * Recupera e retorna o instituto interessado no evento.
     *
     * @return o instituto interessado.
     */
    public String getInteressado() {
        return instituto;
    }

    /**
     * Recupera e retorna a descrição do evento.
     *
     * @return a descrição do evento.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Recupera e retorna a data final do evento.
     *
     * @return a data final do evento
     */
    public GregorianCalendar getDataFinal() {
        return dataFinal;
    }

    /**
     * Verifica e retorna o tamanho da lista regional.
     *
     * @return tamanho da regional
     */
    public int tamRegional() {
        return regional.size();
    }

    /**
     * Recupera e retorna o nome do evento.
     *
     * @return o nome do evento
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Altera o valor do atributo nome.
     *
     * @param nome String com o novo valor a ser atribuido ao atributo;
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Modifica/altera o valor do atributo dataInicial.
     *
     * @param dataInicial A data inicial a ser atribuida.
     */
    public void setDataInicio(GregorianCalendar dataInicial) {
        this.dataInicial = dataInicial;
    }

    /**
     * Modifica/altera o valor do atributo dataFinal.
     *
     * @param dataFinal A data final a ser atribuida
     */
    public void setDataFinal(GregorianCalendar dataFinal) {
        this.dataFinal = dataFinal;
    }

    /**
     * Modifica o valor do atributo instituto.
     *
     * @param instituto O instituto a ser atribuido.
     */
    public void setInstituto(String instituto) {
        this.instituto = instituto;
    }

    /**
     * Altera o valor do atributo descricao.
     *
     * @param descricao Novo valor a ser atribuido.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Altera o valor do atributo regional.
     *
     * @param regional nova regional.
     */
    public void setRegional(ArrayList regional) {
        this.regional = regional;
    }
}
