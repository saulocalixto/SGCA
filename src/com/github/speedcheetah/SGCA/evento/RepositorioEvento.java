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

import com.github.speedcheetah.SGCA.CalendarioAcademico;
import com.github.speedcheetah.SGCA.exception.EventoDuplicadoException;
import com.github.speedcheetah.SGCA.exception.EventoNaoLocalizadoException;
import com.github.speedcheetah.SGCA.enums.Regional;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
/**
 *
 * @author saulocalixto
 */
public class RepositorioEvento {

    public void addEvento(ArrayList<Evento> eventos, Evento newEvento)
            throws EventoDuplicadoException {
        if (eventos.contains(newEvento)) {
            throw new EventoDuplicadoException("Esse produto já foi adicionado."
                    + " Cadastre outro Evento!");
        } else {
            eventos.add(newEvento);
        }
    }

    public static boolean testaData(GregorianCalendar cal) {
        try {
            cal.getTime();
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    public static String[] parseData(String data) {
        String[] valores;

        valores = data.split("/");

        return valores;
    }

    public ArrayList<Evento> pesquisarRegional(ArrayList eventos, String regPesq)
            throws EventoNaoLocalizadoException {
        ArrayList<Evento> eventosRegionais = new ArrayList();
        for (Iterator itr = eventos.iterator(); itr.hasNext();) {
            Evento e = (Evento) itr.next();
            if (e.getRegional().contains(regPesq)) {
                eventosRegionais.add(e);
            }
        }
        if (eventosRegionais.isEmpty()) {
            throw new EventoNaoLocalizadoException();
        }
        return eventosRegionais;
    }

    public Evento pesquisarNome(ArrayList eventos, String nomePesquisa)
            throws EventoNaoLocalizadoException {

        for (Iterator itr = eventos.iterator(); itr.hasNext();) {
            Evento e = (Evento) itr.next();
            if (nomePesquisa.compareToIgnoreCase(e.getNome()) == 0) {
                return e;
            }
        }
        throw new EventoNaoLocalizadoException("Evento não encontrado.");
    }
    
    public static String escolhaRegional(int numRegional) {
        String escolhido = null;
        switch (numRegional) {
            case 1:
                escolhido = Regional.CATALAO.getRepresentacaoTextual();
                break;
            case 2:
                escolhido = Regional.GOIAS.getRepresentacaoTextual();
                break;
            case 3:
                escolhido = Regional.JATAI.getRepresentacaoTextual();
                break;
            case 4:
                escolhido = Regional.GOIANIA.getRepresentacaoTextual();
                break;
            default:
                break;
        }
        return escolhido;
    }

    public ArrayList<Evento> pesquisarData(ArrayList eventos,
            GregorianCalendar dataPesquisa)
            throws EventoNaoLocalizadoException, IllegalArgumentException {
        if (!testaData(dataPesquisa)) {
            throw new IllegalArgumentException("Data inválida.");
        }
        ArrayList<Evento> eventosData = new ArrayList();
        for (Iterator itr = eventos.iterator(); itr.hasNext();) {
            Evento e = (Evento) itr.next();
            if (!(dataPesquisa.before(e.getDataInicial())
                    || dataPesquisa.after(e.getDataFinal()))) {
                eventosData.add(e);
            }
        }
        if (eventosData.isEmpty()) {
            throw new EventoNaoLocalizadoException();
        }
        return eventosData;
    }

    public ArrayList<Evento> pesquisarEventoperiodo(ArrayList eventos,
            GregorianCalendar dataPeriodoinicio,
            GregorianCalendar dataPeriodofim )
            throws EventoNaoLocalizadoException, IllegalArgumentException {
        if (!(testaData(dataPeriodoinicio) || testaData(dataPeriodofim))) {
            throw new IllegalArgumentException("Data inválida.");
        }
        ArrayList<Evento> eventosData = new ArrayList();
        for (Iterator itr = eventos.iterator(); itr.hasNext();) {
            Evento p = (Evento) itr.next();
            if ((dataPeriodoinicio.before(p.getDataInicial())
                    && dataPeriodofim.after(p.getDataFinal())) || 
                    !dataPeriodoinicio.before(p.getDataInicial()) && 
                    dataPeriodoinicio.before(p.getDataFinal()) || 
                    dataPeriodofim.after(p.getDataInicial()) && 
                    dataPeriodofim.before(p.getDataFinal()) || 
                    !dataPeriodoinicio.before(p.getDataInicial()) && 
                    !dataPeriodoinicio.before(p.getDataFinal())) 
                    eventosData.add(p);     
            
                                            
        }
        if (eventosData.isEmpty()) {
            throw new EventoNaoLocalizadoException();
        }
        return eventosData;
    }

    
    public void removerEvento(ArrayList eventos, String nomePesquisa)
            throws EventoNaoLocalizadoException {
        Evento e = pesquisarNome(eventos, nomePesquisa);
        eventos.remove(e);
    }

    public void alterarEvento(ArrayList eventos, String nomePesquisa)
            throws EventoDuplicadoException, EventoNaoLocalizadoException {
        int opcao = -1;
        while (opcao != 0) {
            opcao = CalendarioAcademico.menuModificacao();

            Evento mod = pesquisarNome(eventos, nomePesquisa);
            int index = eventos.indexOf(mod);
            Evento e = (Evento) eventos.get(index);

            switch (opcao) {
                case 1:
                    String novoNome = CalendarioAcademico.cadastrarNome();
                    e.setNome(novoNome);
                    break;

                case 2:
                    GregorianCalendar novaDataI
                            = CalendarioAcademico.cadastrarData();
                    e.setDataInicio(novaDataI);
                    break;

                case 3:
                    GregorianCalendar novaDataF
                            = CalendarioAcademico.cadastrarData();
                    e.setDataFinal(novaDataF);
                    break;

                case 4:
                    ArrayList<String> novaRegionalList
                            = CalendarioAcademico.cadastrarRegional();
                    e.setRegional(novaRegionalList);
                    break;

                case 5:
                    String novoInstituto
                            = CalendarioAcademico.cadastrarInstituto();
                    e.setInstituto(novoInstituto);
                    break;

                case 6:
                    String novaDescricao
                            = CalendarioAcademico.cadastrarDescricao();
                    e.setDescricao(novaDescricao);
                    break;

                default:
                    break;
            }
            eventos.set(index, e);
        }
    }
}
