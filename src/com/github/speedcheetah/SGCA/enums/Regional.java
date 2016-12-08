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
package com.github.speedcheetah.SGCA.enums;

/**
 *
 * @author saulocalixto
 */
public enum Regional {

    CATALAO(1, "CATALÃO"),
    GOIAS(2, "GOIÁS"),
    JATAI(3, "JATAÍ"),
    GOIANIA(4, "GOIÂNIA");

    /**
     * Dentre uma lista definida recebe um Inteiro e uma String que representam
     * atributos do ENUM Regional.
     *
     * @param representacaoNumerica Numero que representa o atributo.
     * @param representacaoTextual A representação em String do atributo que
     * corresponde a escolha.
     */
    Regional(int representacaoNumerica, String representacaoTextual
    ) {
        this.representacaoNumerica = representacaoNumerica;
        this.representacaoTextual = representacaoTextual;
    }

    private final int representacaoNumerica;

    private final String representacaoTextual;

    /**
     * Armazena a representação numerica da lista
     *
     * @return a representação numerica.
     */
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
