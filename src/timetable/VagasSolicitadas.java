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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "vagas_solicitadas")
public class VagasSolicitadas implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int idVagasSolicitadas;
	
	@Column(name = "total_vagas", unique = false, nullable = false)
	private int totalVagas;	
	@Column(name = "vagas_periotizadas_ofert", unique = false, nullable = false)
	private int vagasPeriotOfert;	
	@Column(name = "vagas_periotizadas_nao_ofert", unique = false, nullable = false)
	private int vagasPeriotNaoOfert;
	@Column(name = "vagas_desperiotizadas_ofert", unique = false, nullable = false)
	private int vagasDesperiotOfert;	
	@Column(name = "vagas_desperiotizadas_nao_ofert", unique = false, nullable = false)
	private int vagasDesperiotNaoOfert;
	@Column(name = "descricao", unique = false, nullable = false)
	private String descricao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="disciplina_fk")
	private Disciplina disciplina;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="pedidos_coordenadores_fk")
	private PedidosCoordenadores pedidosCoordenadores;

}
