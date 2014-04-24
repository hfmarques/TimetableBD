/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timetablebd.hibernate;
import org.hibernate.Session;
import timetablebd.hibernate.util.HibernateUtil;

/**
 *
 * @author Héber
 */

public class EventManager {

    public static void insert(Object obj) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(obj);

        session.getTransaction().commit();
        
        session.close();
        
        HibernateUtil.getSessionFactory().close();
    }
}