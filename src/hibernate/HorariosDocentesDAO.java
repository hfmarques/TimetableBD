package hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import timetable.HorariosDocentes;

public class HorariosDocentesDAO extends GenericoDAO{

	public HorariosDocentesDAO() {
		// TODO Auto-generated constructor stub
	}
	
	
	@SuppressWarnings({ "unchecked", "finally" })
	public List<HorariosDocentes> encontraHorariosPorDocente(String codigo) throws HibernateException, Exception {
		List<HorariosDocentes> horarios = null;
			try {
				Criteria criteria = getSession()
						.createCriteria(HorariosDocentes.class)
						.createAlias("docente", "d")	
						.add(Restrictions.eq("d.codigo", codigo));
				horarios = (List<HorariosDocentes>) criteria.list();
			} catch (HibernateException e) {
				System.err.println(e.fillInStackTrace());
			} finally {
				getSession().close();
				return horarios;
		}	
	}
}
