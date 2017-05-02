package timetable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Héber
 */

@Entity
@Table(name = "Periodo")
public class Periodo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int idPeriodo;
	
	@Column(name = "numero_periodo", unique = false, nullable = false)
	private int numeroPeriodo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Curso_fk", nullable = false, unique = false)
	private Curso curso;
	
	@ManyToMany()
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "periodo_disciplina", joinColumns = { @JoinColumn(name = "periodo_fk", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "turma_fk", nullable = false, updatable = false) })
	private List<Turma> turma = new ArrayList<Turma>();

	public Periodo() {
		
	}
	
	public int getIdPeriodo() {
		return idPeriodo;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public int getNumeroPeriodo() {
		return numeroPeriodo;
	}

	public void setNumeroPeriodo(int numeroPeriodo) {
		this.numeroPeriodo = numeroPeriodo;
	}

	public List<Turma> getTurma() {
		return turma;
	}

	public void setTurma(List<Turma> turma) {
		this.turma = turma;
	}
	
	
}
