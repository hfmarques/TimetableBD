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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "horarios_docentes")
public class HorariosDocentes implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int idHorariosDocentes;
	
	@Column(name = "dia", unique = false, nullable = false)
	private String dia;
	
	@Column(name = "oito", unique = false, nullable = false)
	private Boolean oito;
	
	@Column(name = "nove", unique = false, nullable = false)
	private Boolean nove;
	
	@Column(name = "dez", unique = false, nullable = false)
	private Boolean dez;
	
	@Column(name = "onze", unique = false, nullable = false)
	private Boolean onze;
	
	@Column(name = "doze", unique = false, nullable = false)
	private Boolean doze;
	
	@Column(name = "treze", unique = false, nullable = false)
	private Boolean treze;
	
	@Column(name = "quatorze", unique = false, nullable = false)
	private Boolean quatorze;
	
	@Column(name = "quinze", unique = false, nullable = false)
	private Boolean quinze;
	
	@Column(name = "dezesseis", unique = false, nullable = false)
	private Boolean dezesseis;
	
	@Column(name = "dezessete", unique = false, nullable = false)
	private Boolean dezessete;
	
	@Column(name = "dezoito", unique = false, nullable = false)
	private Boolean dezoito;
	
	@Column(name = "dezenove", unique = false, nullable = false)
	private Boolean dezenove;
	
	@Column(name = "vinte", unique = false, nullable = false)
	private Boolean vinte;
	
	@Column(name = "vinte_um", unique = false, nullable = false)
	private Boolean vinteUm;
	
	@Column(name = "vinte_dois", unique = false, nullable = false)
	private Boolean vinteDois;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "docente_fk", nullable = false, unique = false)
	private Docente docente;
	
	public HorariosDocentes() {		
	}
	
	public HorariosDocentes(String dia, Boolean oito, Boolean nove, Boolean dez, Boolean onze, Boolean doze,
			Boolean treze, Boolean quatorze, Boolean quinze, Boolean dezesseis, Boolean dezessete, Boolean dezoito,
			Boolean dezenove, Boolean vinte, Boolean vinteUm) {
		super();
		this.dia = dia;
		this.oito = oito;
		this.nove = nove;
		this.dez = dez;
		this.onze = onze;
		this.doze = doze;
		this.treze = treze;
		this.quatorze = quatorze;
		this.quinze = quinze;
		this.dezesseis = dezesseis;
		this.dezessete = dezessete;
		this.dezoito = dezoito;
		this.dezenove = dezenove;
		this.vinte = vinte;
		this.vinteUm = vinteUm;
	}
	
	public HorariosDocentes(String dia) {
		super();
		this.dia = dia;
		this.oito = true;
		this.nove = true;
		this.dez = true;
		this.onze = true;
		this.doze = true;
		this.treze = true;
		this.quatorze = true;
		this.quinze = true;
		this.dezesseis = true;
		this.dezessete = true;
		this.dezoito = true;
		this.dezenove = true;
		this.vinte = true;
		this.vinteUm = true;
		this.vinteDois = true;
	}
	
	public HorariosDocentes(String dia, Docente docente) {
		super();
		this.dia = dia;
		this.oito = true;
		this.nove = true;
		this.dez = true;
		this.onze = true;
		this.doze = true;
		this.treze = true;
		this.quatorze = true;
		this.quinze = true;
		this.dezesseis = true;
		this.dezessete = true;
		this.dezoito = true;
		this.dezenove = true;
		this.vinte = true;
		this.vinteUm = true;
		this.vinteDois = true;
		this.docente = docente;
	}

	public int getIdHorariosDocentes() {
		return idHorariosDocentes;
	}

	public void setIdHorariosDocentes(int idHorariosDocentes) {
		this.idHorariosDocentes = idHorariosDocentes;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public Boolean isOito() {
		return oito;
	}

	public void setOito(Boolean oito) {
		this.oito = oito;
	}

	public Boolean isNove() {
		return nove;
	}

	public void setNove(Boolean nove) {
		this.nove = nove;
	}

	public Boolean isDez() {
		return dez;
	}

	public void setDez(Boolean dez) {
		this.dez = dez;
	}

	public Boolean isOnze() {
		return onze;
	}

	public void setOnze(Boolean onze) {
		this.onze = onze;
	}

	public Boolean isDoze() {
		return doze;
	}

	public void setDoze(Boolean doze) {
		this.doze = doze;
	}

	public Boolean isTreze() {
		return treze;
	}

	public void setTreze(Boolean treze) {
		this.treze = treze;
	}

	public Boolean isQuatorze() {
		return quatorze;
	}

	public void setQuatorze(Boolean quatorze) {
		this.quatorze = quatorze;
	}

	public Boolean isQuinze() {
		return quinze;
	}

	public void setQuinze(Boolean quinze) {
		this.quinze = quinze;
	}

	public Boolean isDezesseis() {
		return dezesseis;
	}

	public void setDezesseis(Boolean dezesseis) {
		this.dezesseis = dezesseis;
	}

	public Boolean isDezessete() {
		return dezessete;
	}

	public void setDezessete(Boolean dezessete) {
		this.dezessete = dezessete;
	}

	public Boolean isDezoito() {
		return dezoito;
	}

	public void setDezoito(Boolean dezoito) {
		this.dezoito = dezoito;
	}

	public Boolean isDezenove() {
		return dezenove;
	}

	public void setDezenove(Boolean dezenove) {
		this.dezenove = dezenove;
	}

	public Boolean isVinte() {
		return vinte;
	}

	public void setVinte(Boolean vinte) {
		this.vinte = vinte;
	}

	public Boolean isVinteUm() {
		return vinteUm;
	}

	public void setVinteUm(Boolean vinteUm) {
		this.vinteUm = vinteUm;
	}
	
	public Boolean isVinteDois() {
		return vinteDois;
	}

	public void setVinteDois(Boolean vinteDois) {
		this.vinteDois = vinteDois;
	}
	
	public Docente getDocente() {
		return docente;
	}
	
	public void setDocente(Docente docente) {
		this.docente = docente;
	}
	
}
