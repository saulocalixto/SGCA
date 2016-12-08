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
public enum Interessados {

    P(1, "PROFESSOR"),
    A(2, "ALUNO"),
    S(3, "SERVIDOR"),
    C(4, "COMUNIDADE");

    private final int representacaoNumerica;

    private final String representacaoTextual;

    Interessados(int representacaoNumerica, String representacaoTextual) {
        this.representacaoNumerica = representacaoNumerica;
        this.representacaoTextual = representacaoTextual;
    }

    public int getRepresentacaoNumerica() {
        return representacaoNumerica;
    }

    /**
     * Armazena a representação textual da lista.
     *
     * @return representação numerica.
     */
    public String getRepresentacaoTextual() {
        return representacaoTextual;
    }

}
