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

    public Area getOrCreate(Area area) {
        Area tmp = getSession().get(Area.class, area.getId());
        if (tmp != null) return tmp;
        getSession().save(area);
        return area;
    }

    private Session getSession() {
        return Objects.requireNonNull(sessionFactoryBean.getObject()).getCurrentSession();
    }

}
