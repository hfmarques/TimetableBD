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
public class Sala {
    @Id
    @Column(name = "idSala", unique = true, nullable = false)
    private int idSala;
    @Column(name = "numero", unique = true, nullable = false)
    private String numero;
    @Column(name = "local", unique = false, nullable = false)
    private String local;
    
}