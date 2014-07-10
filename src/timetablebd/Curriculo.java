/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timetablebd;

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
@Table(name = "curriculo")
public class Curriculo {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int idCurriculo;
    @Column(name = "anoInicio", unique = false, nullable = false)
    private int anoInicio;
    @Column(name = "ativo", unique = false, nullable = false)
    private boolean ativo;
    @Column(name = "idCurso", unique = false, nullable = false)
    private int idCurso;
}
