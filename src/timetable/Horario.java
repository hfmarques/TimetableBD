package timetable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Héber
 */

@Entity
@Table(name = "horario")
public class Horario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;
	
	@Column(name = "dia1", unique = false, nullable = true)
	private Date dia1;
	
	@Column(name = "dia2", unique = false, nullable = true)
	private Date dia2;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "horario")
	private List<Turma> turma = new ArrayList<Turma>();
	
	public Horario() {
		super();
		this.dia1 = null;
		this.dia2 = null;
	}

	public Horario(Date dia1, Date dia2) {
		super();
		this.dia1 = dia1;
		this.dia2 = dia2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDia1() {
		return dia1;
	}

	public void setDia1(Date dia1) {
		this.dia1 = dia1;
	}

	public Date getDia2() {
		return dia2;
	}

	public void setDia2(Date dia2) {
		this.dia2 = dia2;
	}

	public List<Turma> getTurma() {
		return turma;
	}

	public void setTurma(List<Turma> turma) {
		this.turma = turma;
	}
	
	
}
