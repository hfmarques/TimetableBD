/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timetablebd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Héber
 */

@Entity
@Table(name = "horario")
public class Horario {
    @Column(name = "horario", unique = false, nullable = false)
    private String horario;
    @Column(name = "idTurma", unique = false, nullable = false)
    private int idTurma;
}
