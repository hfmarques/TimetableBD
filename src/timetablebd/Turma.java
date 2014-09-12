/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timetablebd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Héber
 */

@Entity
@Table(name = "turma")
public class Turma implements Serializable{
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int idTurma;
    @Column(name = "codigo", unique = true, nullable = false)
    private String codigo;
    @Column(name = "turno", unique = false, nullable = false)
    private String turno;
    @Column(name = "max_vagas", unique = false, nullable = false)
    private int maxVagas;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "disciplina_fk", nullable = false)
    private Disciplina disciplina;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sala_fk", nullable = false)
    private Sala sala;
        
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "creditoministrado", joinColumns = {
			@JoinColumn(name = "docente_fk", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "turma_fk", nullable = false, updatable = false) })
    
    private List<Docente> docente = new ArrayList<Docente>();
    

    public Turma() {
    }

    public Turma(String codigo, String turno, int maxVagas, Disciplina disciplina, Sala sala, Docente docente) {
        this.codigo = codigo;
        this.turno = turno;
        this.maxVagas = maxVagas;
//        this.disciplina = disciplina;
//        this.sala = sala;
        this.docente.add(docente);
    }

    public int getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
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

    public int getMaxVagas() {
        return maxVagas;
    }

    public void setMaxVagas(int maxVagas) {
        this.maxVagas = maxVagas;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public List<Docente> getDocente() {
        return docente;
    }

    public void setDocente(List<Docente> docente) {
        this.docente = docente;
    }
    
    
    
    
    
    
}
