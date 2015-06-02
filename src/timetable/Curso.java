package timetable;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;

import hibernate.HibernateUtil;

/**
 *
 * @author H�ber
 */

@Entity
@Table(name = "Curso")
public class Curso implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int idCurso;
	@Column(name = "nome", unique = true, nullable = false)
	private String nome;
	@Column(name = "codigo", unique = true, nullable = false)
	private String codigo;
	@Column(name = "turno", unique = false, nullable = false)
	private String turno;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "calouros_curso", joinColumns = { @JoinColumn(name = "curso_fk", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "calouros_fk", nullable = false, updatable = false) })
	private List<Calouros> calouros = new ArrayList<Calouros>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "curso")
	private List<Curso> curso = new ArrayList<Curso>();


	public Curso(String nome, String codigo, String turno, Calouros calourosPrimSem, Calouros calourosSegSem) {
		this.nome = nome;
		this.codigo = codigo;
		this.turno = turno;
		this.calouros.add(calourosPrimSem);
		this.calouros.add(calourosSegSem);
	}

	public Curso() {

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

	public List<Calouros> getCalouros() {
		return calouros;
	}

	public void setCalouros(List<Calouros> calouros) {
		this.calouros = calouros;
	}
}
