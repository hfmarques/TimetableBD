/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timetablebd;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "disciplina")
public class Disciplina implements Serializable {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int idDisciplina;
    @Column(name = "codigo", unique = true, nullable = false)
    private String codigo;
    @Column(name = "creditos", unique = false, nullable = true)
    private int creditos;
    @Column(name = "nome", unique = false, nullable = true)
    private String nome;
    @Column(name = "perfil", unique = false, nullable = true)
    private String perfil;


    public Disciplina() {
    }

    public Disciplina(int idDisciplina, String codigo, int creditos, String nome, String perfil) {
        this.idDisciplina = idDisciplina;
        this.codigo = codigo;
        this.creditos = creditos;
        this.nome = nome;
        this.perfil = perfil;
    }
    
    public int getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    
    
    
    public static Disciplina getTableLine(int id) {
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("select u from Disciplina as u where u.idDisciplina = :idDisciplina");
            query.setParameter("idDisciplina", id);

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