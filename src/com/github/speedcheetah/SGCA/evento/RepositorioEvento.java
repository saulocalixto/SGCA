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
import com.github.speedcheetah.SGCA.enums.Interessado;
import com.github.speedcheetah.SGCA.exception.EventoDuplicadoException;
import com.github.speedcheetah.SGCA.exception.EventoNaoLocalizadoException;
import com.github.speedcheetah.SGCA.enums.Regional;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author saulocalixto
 */
public final class RepositorioEvento {

    private RepositorioEvento() {
    }

    public static void addEvento(ArrayList<Evento> eventos, Evento newEvento)
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

    public static List<String> parseData(String data) throws Exception {
        String[] valores;

        valores = data.split("-");
        String dia = valores[0].trim();
        String hora = valores[1].trim();

        String paramDia[] = dia.split("/");
        String paramHora[] = hora.split(":");

        List<String> paramData = new ArrayList();
        paramData.add(0, paramDia[0]);
        paramData.add(1, paramDia[1]);
        paramData.add(2, paramDia[2]);
        paramData.add(3, paramHora[0]);
        paramData.add(4, paramHora[1]);
        return paramData;
    }

    /**
     * Pesquisa eventos de uma regional especifica
     *
     * @param eventos Lista de eventos
     * @param regPesq A regional a ser pesquisada
     * @return uma lista de eventos da regional escolhida
     * @throws EventoNaoLocalizadoException
     */
    public static ArrayList<Evento> pesquisarRegional(ArrayList eventos,
            String regPesq) throws EventoNaoLocalizadoException {
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

    /**
     * Pesquisa o nome de um evento.
     *
     * @param eventos Lista de eventos.
     * @param nomePesquisa Nome do evento a ser pesquisado
     * @return retorna o evento, caso seja encontrado
     * @throws EventoNaoLocalizadoException
     */
    public static Evento pesquisarNome(ArrayList eventos, String nomePesquisa)
            throws EventoNaoLocalizadoException {

        for (Iterator itr = eventos.iterator(); itr.hasNext();) {
            Evento e = (Evento) itr.next();
            if (nomePesquisa.compareToIgnoreCase(e.getNome()) == 0) {
                return e;
            }
        }
        throw new EventoNaoLocalizadoException("Evento não encontrado.");
    }

    /**
     * Escolhe uma regional
     *
     * @param numRegional numero correspondente a regional desejada
     * @return a regional escolhida
     */
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

    /**
     * Escolhe o interessado com relação aos eventos cadastrados.
     *
     * @param numInteressado numero que representa a escolha do interessado.
     * @return o interessado nos eventos.
     */
    public static String escolhaInteressado(int numInteressado) {
        String escolhido = null;
        switch (numInteressado) {
            case 1:
                escolhido = Interessado.P.getRepresentacaoTextual();
                break;
            case 2:
                escolhido = Interessado.A.getRepresentacaoTextual();
                break;
            case 3:
                escolhido = Interessado.S.getRepresentacaoTextual();
                break;
            case 4:
                escolhido = Interessado.C.getRepresentacaoTextual();
                break;
            default:
                break;
        }
        return escolhido;
    }

    /**
     * Pesquisa eventos pela data data especificada e retorna os eventos daquela
     * data.
     *
     * @param eventos Lista de eventos
     * @param dataPesquisa a data a ser pesquisada
     * @return Uma lista de eventos.
     * @throws EventoNaoLocalizadoException
     * @throws IllegalArgumentException
     */
    public static ArrayList<Evento> pesquisarData(ArrayList eventos,
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

    /**
     * Pesquisa eventos entre períodos
     *
     * @param eventos Lista de eventos.
     * @param dataPeriodoInicio Data inicial para a pesquisa.
     * @param dataPeriodoFim Data final da pesquisa.
     * @return Lista de eventos entre as datas definidas.
     * @throws EventoNaoLocalizadoException
     * @throws IllegalArgumentException
     */
    public static ArrayList<Evento> pesquisarEventoPeriodo(ArrayList eventos,
            GregorianCalendar dataPeriodoInicio,
            GregorianCalendar dataPeriodoFim)
            throws EventoNaoLocalizadoException, IllegalArgumentException {
        if (!(testaData(dataPeriodoInicio) && testaData(dataPeriodoFim))) {
            throw new IllegalArgumentException("Data inválida.");
        }
        ArrayList<Evento> eventosData = new ArrayList();
        for (Iterator itr = eventos.iterator(); itr.hasNext();) {
            Evento p = (Evento) itr.next();
            if (!((dataPeriodoFim.before(p.getDataInicial()))
                    || (dataPeriodoInicio.after(p.getDataFinal())))) {
                eventosData.add(p);
            }
        }
        if (eventosData.isEmpty()) {
            throw new EventoNaoLocalizadoException();
        }
        return eventosData;
    }

    /**
     * Pesquisa e remove um evento pelo nome.
     *
     * @param eventos Lista de eventos.
     * @param nomePesquisa Nome do evento a ser pesquisado.
     * @throws EventoNaoLocalizadoException
     */
    public static void removerEvento(ArrayList eventos, String nomePesquisa)
            throws EventoNaoLocalizadoException {
        Evento e = pesquisarNome(eventos, nomePesquisa);
        eventos.remove(e);
    }

    /**
     * Pesquisa e altera um evento.
     *
     * @param eventos Lista de eventos.
     * @param nomePesquisa Nome do evento pesquisado.
     * @throws EventoDuplicadoException
     * @throws EventoNaoLocalizadoException
     */
    public static void alterarEvento(ArrayList eventos, String nomePesquisa)
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
