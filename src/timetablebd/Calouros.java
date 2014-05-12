/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timetablebd;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 *
 * @author Héber
 */
public class Calouros {
    @Id
    @Column(name = "idCalouro", unique = true, nullable = false)
    private int idCalouro;
    @Column(name = "semestre", unique = false, nullable = false)
    private int semestre;
    @Column(name = "numVagas", unique = false, nullable = false)
    private int numVagas;
    @Column(name = "idCurso", unique = false, nullable = false)
    private int idCurso;
}
