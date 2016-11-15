/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.speedcheetah.SGCA;

/**
 *
 * @author saulocalixto
 */
public enum Regionais {

    CATALAO(1, "CATALÃO"),
    GOIAS(2, "GOIÁS"),
    JATAI(3, "JATAÍ"),
    GOIANIA(4, "GOIÂNIA");

    Regionais(int representacaoNumerica, String representacaoTextual
    ) {
        this.representacaoNumerica = representacaoNumerica;
        this.representacaoTextual = representacaoTextual;
    }

    private int representacaoNumerica;

    private String representacaoTextual;

    public int getRepresentacaoNumerica() {
        return representacaoNumerica;
    }

    public String getRepresentacaoTextual() {
        return representacaoTextual;
    }

}
