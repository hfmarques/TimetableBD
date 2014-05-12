/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timetablebd;

import javax.persistence.Column;

/**
 *
 * @author Héber
 */
public class DiscCurr {
    @Column(name = "periodo", unique = false, nullable = false)
    private int periodo;
    @Column(name = "carater", unique = false, nullable = false)
    private String carater;
    @Column(name = "idDisciplina", unique = false, nullable = false)
    private int idDisciplina;
    @Column(name = "idCurriculo", unique = false, nullable = false)
    private int idCurriculo;
    
}
