package ru.hh.school.dao;

import ru.hh.nab.hibernate.NabSessionFactoryBean;
import ru.hh.school.entity.FavoriteEmployer;

import java.util.List;

public class FavoriteEmployerDao extends BaseFavoriteDao<FavoriteEmployer> {

    public FavoriteEmployerDao(NabSessionFactoryBean sessionFactoryBean) {
        super(sessionFactoryBean);
    }

    @Override
    String getEntityName() {
        return FavoriteEmployer.ENTITY_NAME;
    }

    @Override
    public FavoriteEmployer get(Long id) {
        return get(id, FavoriteEmployer.class);
    }

    @Override
    public List<FavoriteEmployer> getPage(int page, int perPage) {
        return getPage(page, perPage, FavoriteEmployer.class);
    }

}
