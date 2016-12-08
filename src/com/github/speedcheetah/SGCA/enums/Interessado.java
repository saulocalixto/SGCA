/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.speedcheetah.SGCA.enums;

/**
 *
 * @author saulocalixto
 */
public enum Interessado {

    P(1, "PROFESSOR"),
    A(2, "ALUNO"),
    S(3, "SERVIDOR"),
    C(4, "COMUNIDADE");

    private final int representacaoNumerica;

    private final String representacaoTextual;

    /**
     * Dentre uma lista definida recebe um Inteiro e uma String que representam
     * atributos do ENUM Interessados.
     *
     * @param representacaoNumerica Numero que representa o atributo
     * @param representacaoTextual A representação em String do atributo que
     * corresponde a escolha.
     */
    Interessado(int representacaoNumerica, String representacaoTextual) {
        this.representacaoNumerica = representacaoNumerica;
        this.representacaoTextual = representacaoTextual;
    }

    /**
     * Recupera e retorna a representação numerica da lista
     *
     * @return a representação numerica.
     */
    public int getRepresentacaoNumerica() {
        return representacaoNumerica;
    }

    /**
     * Recupera e retorna a representação textual da lista.
     *
     * @return representação textual.
     */
    public String getRepresentacaoTextual() {
        return representacaoTextual;
    }

}
