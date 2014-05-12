/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timetablebd;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 *
 * @author Héber
 */
public class Curso {
    @Id
    @Column(name = "idCurso", unique = true, nullable = false)
    private int idCurso;
    @Column(name = "nome", unique = true, nullable = false)
    private String nome;
    @Column(name = "codigo", unique = true, nullable = false)
    private String codigo;
    @Column(name = "turno", unique = false, nullable = false)
    private String turno;
    @Column(name = "idCalouro", unique = false, nullable = false)
    private int idCalouro;

    public Curso(int idCurso, String nome, String codigo, String turno, int idCalouro) {
        this.idCurso = idCurso;
        this.nome = nome;
        this.codigo = codigo;
        this.turno = turno;
        this.idCalouro = idCalouro;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getIdCalouro() {
        return idCalouro;
    }

    public void setIdCalouro(int idCalouro) {
        this.idCalouro = idCalouro;
    }
}
