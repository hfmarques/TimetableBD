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
public class Curriculo {
    @Id
    @Column(name = "idCurriculo", unique = true, nullable = false)
    private int idCurriculo;
    @Column(name = "anoInicio", unique = false, nullable = false)
    private int anoInicio;
    @Column(name = "ativo", unique = false, nullable = false)
    private boolean ativo;
    @Column(name = "idCurso", unique = false, nullable = false)
    private int idCurso;
}
