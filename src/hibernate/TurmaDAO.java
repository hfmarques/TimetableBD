package hibernate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Query;

import timetable.Docente;
import timetable.Turma;
import hibernate.GenericoDAO;
import hibernate.HibernateUtil;

public class TurmaDAO extends GenericoDAO{

	public TurmaDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List<timetable.Turma> procuraTodos() throws HibernateException, Exception {
		List<timetable.Turma> lista = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Turma.class);		
			lista = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return lista;
		}
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	public List<timetable.Turma> encontraTurmasSemProf() throws HibernateException, Exception {
		List<timetable.Turma> lista = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Turma.class)
					.add(Restrictions.isEmpty("docente"));	
			lista = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return lista;
		}
	}
	
	public Turma encontraTurmasPorCodigo(String disciplinaCode, String turmaCode) throws HibernateException, Exception{
		Turma turma = null;
		try {			
			Criteria criteria = getSession()
					.createCriteria(Turma.class)
					.add(Restrictions.like("codigo", turmaCode))
					.createAlias("disciplina", "d")					
					.add(Restrictions.like("d.codigo", disciplinaCode));
			turma = (Turma) criteria.uniqueResult();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return turma;
		}
	}

}
