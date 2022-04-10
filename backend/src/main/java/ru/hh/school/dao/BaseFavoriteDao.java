package ru.hh.school.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.hh.nab.hibernate.NabSessionFactoryBean;
import ru.hh.school.entity.Popularity;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public abstract class BaseFavoriteDao<T> {

    private final NabSessionFactoryBean sessionFactoryBean;

    public BaseFavoriteDao(NabSessionFactoryBean sessionFactoryBean) {
        this.sessionFactoryBean = sessionFactoryBean;
    }

    abstract String getEntityName();

    public abstract T get(Long id);

    public abstract List<T> getPage(int page, int perPage);

    public T save(T entity) {
        getSession().save(entity);
        return entity;
    }

    public void update(T entity) {
        getSession().saveOrUpdate(entity);
    }

    public int delete(Long id) {
        Session session = getSession();
        Query query = session.createQuery(
                "delete from " + getEntityName() + " e where e.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public void changePopularity(Collection<Long> ids, Integer border) {
        Session session = getSession();
        Query query = session.createQuery(
                "update " + getEntityName() + " e set e.popularity = :popular where e.id in(:ids) and e.viewsCount > :border");
        query.setParameter("border", border);
        query.setParameter("popular", Popularity.POPULAR);
        query.setParameter("ids", ids);
        query.executeUpdate();
    }

    public void incrementViewsCount(Collection<Long> ids) {
        Session session = getSession();
        Query query = session.createQuery(
                "update " + getEntityName() + " e set e.viewsCount = e.viewsCount + 1 where e.id in(:ids)");
        query.setParameter("ids", ids);
        query.executeUpdate();
    }

    protected T get(Long id, Class<T> clazz) {
        return getSession().get(clazz, id);
    }

    protected List<T> getPage(int page, int perPage, Class<T> clazz) {
        Session session = getSession();
        Query<T> query = session.createQuery("from " + getEntityName() + " order by id", clazz);
        query.setFirstResult(perPage * page);
        query.setMaxResults(perPage);
        return query.list();
    }

    protected Session getSession() {
        return Objects.requireNonNull(sessionFactoryBean.getObject()).getCurrentSession();
    }

}
