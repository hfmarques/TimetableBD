/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timetablebd;

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

/**
 *
 * @author Héber
 */

@Entity
@Table(name = "sala")
public class Sala implements Serializable{
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int idSala;
    @Column(name = "numero", unique = true, nullable = false)
    private String numero;
    @Column(name = "local", unique = false, nullable = false)
    private String local;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sala")
	private List<Turma> turma = new ArrayList<Turma>();
    
    
    
}