package spittr.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spittr.dao.SpittleRepository;
import spittr.dto.Spittle;

@Repository
@Transactional
public class HibernateSpittleRepository implements SpittleRepository {
	private final HashMap<Long, Spittle> spitterMap = new HashMap<Long, Spittle>();

	@Autowired
	private SessionFactory sessionFactory;

	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public long count() {
		return findAll().size();
	}

	@Override
	public List<Spittle> findRecent() {
		return findRecentSpittles(10);
	}

	@Override
	public List<Spittle> findRecentSpittles(int count) {
		return spittleCriteria().setMaxResults(count).list();
	}

	@Override
	public Spittle findOne(long id) {
		return (Spittle) currentSession().get(Spittle.class, id);
	}

	@Override
	public Spittle save(Spittle spittle) {
		Serializable id = currentSession().save(spittle);
		return new Spittle((Long) id, spittle.getSpitter(), spittle.getMessage(), spittle.getPostedTime());
	}

	public List<Spittle> findBySpitterId(long spitterId) {
		return spittleCriteria().add(Restrictions.eq("spitter.id", spitterId)).list();
	}

	public void delete(long id) {
		currentSession().delete(findOne(id));
	}

	public List<Spittle> findAll() {
		return spittleCriteria().list();
	}

	private Criteria spittleCriteria() {
		return currentSession().createCriteria(Spittle.class).addOrder(Order.desc("postedTime"));
	}
}
