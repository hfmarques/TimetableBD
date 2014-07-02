/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timetablebd;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;
import timetablebd.hibernate.util.HibernateUtil;

/**
 *
 * @author Héber
 */

@Entity
@Table(name = "Curso")
public class Curso implements Serializable{
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int idCurso;
    @Column(name = "nome", unique = true, nullable = false)
    private String nome;
    @Column(name = "codigo", unique = true, nullable = false)
    private String codigo;
    @Column(name = "turno", unique = false, nullable = false)
    private String turno;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "timetablebd_calouros_fk", nullable = false)
    private Calouros calouros;

    public Curso(String nome, String codigo, String turno, Calouros calouros) {
        this.nome = nome;
        this.codigo = codigo;
        this.turno = turno;
        this.calouros = calouros;
    }
    
    public Curso(){
    
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Calouros getIdCalouro() {
        return calouros;
    }

    public void setIdCalouro(Calouros calouros) {
        this.calouros = calouros;
    }
    
    public static Curso getTableLine(int id) {
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("select u from Curso as u where u.idCurso = :idCurso");
            query.setParameter("idCurso", id);

            Curso resultado = (Curso) query.uniqueResult();

            session.close();

            HibernateUtil.getSessionFactory().close();

            if (resultado != null) {
                return resultado;
            }

        }
            
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
