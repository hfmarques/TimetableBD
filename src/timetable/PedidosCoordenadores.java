package timetable;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name = "pedidos_coordenadores")
public class PedidosCoordenadores implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int idPedidosCoordenadores;
	@Column(name = "semestre", unique = false, nullable = false)
	private int semestre;
	@Column(name = "ano", unique = false, nullable = false)
	private int ano;
	@Column(name = "data", unique = false, nullable = false)
	private Date data;
	@Column(name = "nome_coordenador", unique = false, nullable = false)
	private String nomeCoordenador;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="curso_fk")
	private Curso curso;
	
	public PedidosCoordenadores() {
		// TODO Auto-generated constructor stub
	}
	
	public PedidosCoordenadores(int semestre, int ano, Date data, String nomeCoordenador, Curso curso) {
		super();
		this.semestre = semestre;
		this.ano = ano;
		this.data = data;
		this.nomeCoordenador = nomeCoordenador;
		this.curso = curso;
	}

	public int getIdPedidosCoordenadores() {
		return idPedidosCoordenadores;
	}

	public void setIdPedidosCoordenadores(int idPedidosCoordenadores) {
		this.idPedidosCoordenadores = idPedidosCoordenadores;
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getNomeCoordenador() {
		return nomeCoordenador;
	}

	public void setNomeCoordenador(String nomeCoordenador) {
		this.nomeCoordenador = nomeCoordenador;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}	
}
