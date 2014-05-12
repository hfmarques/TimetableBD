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
public class Turma {
    @Id
    @Column(name = "idTurma", unique = true, nullable = false)
    private int idTurma;
    @Column(name = "codigo", unique = true, nullable = false)
    private String codigo;
    @Column(name = "turno", unique = false, nullable = false)
    private String turno;
    @Column(name = "maxVagas", unique = false, nullable = false)
    private int maxVagas;
    @Column(name = "idDisciplina", unique = false, nullable = false)
    private int idDisciplina;
    @Column(name = "idSala", unique = false, nullable = false)
    private int idSala;
}
