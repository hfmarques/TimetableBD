package timetable;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Héber
 */

@Entity
@Table(name = "diccurr")
public class DiscCurr implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;
	@Column(name = "periodo", unique = false, nullable = false)
	private int periodo;
	@Column(name = "carater", unique = false, nullable = false)
	private String carater;
	@Column(name = "idDisciplina", unique = false, nullable = false)
	private int idDisciplina;
	@Column(name = "idCurriculo", unique = false, nullable = false)
	private int idCurriculo;

}
