/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timetablebd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Héber
 */

@Entity
@Table(name = "creditoministrado")
public class CreditoMinistrado {
    @Id
    @Column(name = "credito", unique = false, nullable = false)
    private int credito;
    @Column(name = "idDocente", unique = false, nullable = false)
    private int idDocente;
    @Column(name = "idTurma", unique = false, nullable = false)
    private int idTurma;
}
