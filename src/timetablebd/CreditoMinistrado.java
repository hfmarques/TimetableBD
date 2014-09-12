/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timetablebd;

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
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Héber
 */

//@Entity
//@Table(name = "creditoministrado")
//public class CreditoMinistrado implements Serializable{
//    @Id
//    @Column(name = "id", unique = true, nullable = false)
//    @GeneratedValue(generator = "increment")
//    @GenericGenerator(name = "increment", strategy = "increment")
//    private int id;
//    @Column(name = "credito", unique = false, nullable = false)
//    private int credito;
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinTable(name = "creditoministrado", joinColumns = { 
//			@JoinColumn(name = "docente_fk", nullable = false, updatable = false) }, 
//			inverseJoinColumns = { @JoinColumn(name = "turma_fk", nullable = false, updatable = false) })
//    private List<Calouros> turma = new ArrayList<Calouros>();
//    
//}
