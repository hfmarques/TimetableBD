/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timetablebd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Héber
 */

@Entity
@Table(name = "docente")
public class Docente {
    
    @Id
    @Column(name = "idDocente", unique = true, nullable = false)
    private int idDocente;
    @Column(name = "codigo", unique = true, nullable = false)
    private String codigo;
    @Column(name = "nome", unique = false, nullable = false)
    private String nome;
    @Column(name = "nomeCompleto", unique = false, nullable = false)
    private String nomeCompleto;
    @Column(name = "creditacaoEsperada", unique = false, nullable = false)
    private int creditacaoEsperada;

    public Docente() {
    }

    public Docente(int idDocente, String codigo, String nome, String nomeCompleto, int creditacaoEsperada) {
        this.idDocente = idDocente;
        this.codigo = codigo;
        this.nome = nome;
        this.nomeCompleto = nomeCompleto;
        this.creditacaoEsperada = creditacaoEsperada;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public int getCreditacaoEsperada() {
        return creditacaoEsperada;
    }

    public void setCreditacaoEsperada(int creditacaoEsperada) {
        this.creditacaoEsperada = creditacaoEsperada;
    }
    
    
}
