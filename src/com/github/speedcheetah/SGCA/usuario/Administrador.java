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
package com.github.speedcheetah.SGCA.usuario;

/**
 *
 * @author Fenro
 */
public class Administrador {
    private String user;
    private String key;
    private boolean online = false;

    public Administrador(String user, String key){
        this.user = user.trim();
        this.key = key.trim();
        this.online = true;
    }

    public void login(String user, String key){
        this.online = this.key.equals(key.trim())
                && this.user.equals(user.trim());
    }

    public void logoff(){
        this.online = false;
    }

    public boolean isOnline() {
        return online;
    }
    
    public String getUser() {
        return this.user;
    }
    
    public boolean confirmaSenha(String key){
        return this.key.equals(key.trim());
    }
    
    public void alteraSenha(String key, String newKey) {
        if (confirmaSenha(key.trim())) {
            this.key = newKey.trim();
        }
    }
}
