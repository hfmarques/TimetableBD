/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timetablebd;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.Query;
import org.hibernate.Session;
import timetablebd.hibernate.util.HibernateUtil;

/**
 *
 * @author Héber
 */

@Entity
@Table(name = "disciplina")
public class Disciplina implements Serializable {
    @Id
    @Column(name = "codigo", unique = true, nullable = false)
    private int codigo;
    @Column(name = "creditos", unique = false, nullable = true)
    private int creditos;

    public Disciplina() {
    }
    
   public Disciplina(int codigo, int creditos) {
        this.codigo = codigo;
        this.creditos = creditos;
    }
    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }    

    public int getCodigo() {
        return codigo;
    }
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public static Disciplina getTableLine(int id) {
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("select u from Disciplina as u where u.codigo = :codigo");
            query.setParameter("codigo", id);

            Disciplina resultado = (Disciplina) query.uniqueResult();

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