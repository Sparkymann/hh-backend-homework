package ru.hh.school.dao;

import org.hibernate.Session;
import ru.hh.nab.hibernate.NabSessionFactoryBean;
import ru.hh.school.entity.Area;

import java.util.Objects;

public class AreaDao {

    private final NabSessionFactoryBean sessionFactoryBean;

    public AreaDao(NabSessionFactoryBean sessionFactoryBean) {
        this.sessionFactoryBean = sessionFactoryBean;
    }

    private Area save(Area entity) {
        getSession().save(entity);
        return entity;
    }

    private Area get(Long id) {
        return getSession().get(Area.class, id);
    }

    public Area getOrCreate(Area area) {
        Area tmp = get(area.getId());
        if (tmp != null) return tmp;
        return save(area);
    }

    private Session getSession() {
        return Objects.requireNonNull(sessionFactoryBean.getObject()).getCurrentSession();
    }

}
