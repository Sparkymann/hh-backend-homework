package ru.hh.school.dao;

import ru.hh.nab.hibernate.NabSessionFactoryBean;
import ru.hh.school.entity.FavoriteVacancy;

import java.util.List;

public class FavoriteVacancyDao extends BaseFavoriteDao<FavoriteVacancy> {

    public FavoriteVacancyDao(NabSessionFactoryBean sessionFactoryBean) {
        super(sessionFactoryBean);
    }

    @Override
    String getEntityName() {
        return FavoriteVacancy.ENTITY_NAME;
    }

    @Override
    public FavoriteVacancy get(Long id) {
        return get(id, FavoriteVacancy.class);
    }

    @Override
    public List<FavoriteVacancy> getPage(int page, int perPage) {
        return getPage(page, perPage, FavoriteVacancy.class);
    }

}
