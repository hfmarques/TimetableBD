package timetable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.OneToOne;
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
	
	
}
