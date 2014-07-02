/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timetablebd;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Héber
 */
@Entity
@Table(name = "Calouros")
public class Calouros {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int idCalouro;
    @Column(name = "semestre", unique = false, nullable = false)
    private int semestre;
    @Column(name = "num_vagas", unique = false, nullable = false)
    private int numVagas;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "calouros")
    private List<Curso> cursos = new ArrayList<Curso>();

    public Calouros() {
    }

    public Calouros(int semestre, int numVagas) {
        this.semestre = semestre;
        this.numVagas = numVagas;
    }

    public int getIdCalouro() {
        return idCalouro;
    }

    public void setIdCalouro(int idCalouro) {
        this.idCalouro = idCalouro;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getNumVagas() {
        return numVagas;
    }

    public void setNumVagas(int numVagas) {
        this.numVagas = numVagas;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }
}
