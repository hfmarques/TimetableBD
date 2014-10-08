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
import javax.persistence.Table;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;
import hibernate.HibernateUtil;

/**
 *
 * @author Héber
 */

@Entity
@Table(name = "docente")
public class Docente implements Serializable {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int idDocente;
	@Column(name = "codigo", unique = true, nullable = false)
	private String codigo;
	@Column(name = "nome", unique = false, nullable = false)
	private String nome;
	@Column(name = "nome_completo", unique = false, nullable = false)
	private String nomeCompleto;
	@Column(name = "creditacao_esperada", unique = false, nullable = false)
	private int creditacaoEsperada;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "docente")
	private List<Turma> turma = new ArrayList<Turma>();

	public Docente() {
	}

	public Docente(String codigo, String nome, String nomeCompleto,
			int creditacaoEsperada) {
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

	public static Docente getTableLine(int id) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session
					.createQuery("select u from Docente as u where u.id = :id");
			query.setParameter("id", id);

			Docente resultado = (Docente) query.uniqueResult();

			session.close();

			HibernateUtil.getSessionFactory().close();

			if (resultado != null) {
				return resultado;
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
