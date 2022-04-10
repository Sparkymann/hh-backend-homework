package ru.hh.school.service;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.nab.common.properties.FileSettings;
import ru.hh.school.dao.AreaDao;
import ru.hh.school.dao.BaseFavoriteDao;
import ru.hh.school.entity.BaseFavorite;
import ru.hh.school.request.BaseRequest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BaseService<T extends BaseFavorite, S> {

    protected static final String POPULARITY_PROP = "popularity";
    protected static final Integer DEFAULT_POPULARITY_VALUE = 50;
    protected final FileSettings fileSettings;
    private final BaseFavoriteDao<T> dao;
    protected final AreaDao areaDao;

    public BaseService(FileSettings fileSettings, BaseFavoriteDao<T> dao, AreaDao areaDao) {
        this.fileSettings = fileSettings;
        this.dao = dao;
        this.areaDao = areaDao;
    }

    protected abstract T createFromDto(S dto);

    protected abstract void fillFromDto(T entity, S dto);

    protected Integer getPopularityValue() {
        return Optional.ofNullable(fileSettings.getInteger(POPULARITY_PROP)).orElse(DEFAULT_POPULARITY_VALUE);
    }

    @Transactional
    public void saveFavorite(BaseRequest req, Long id, S dto) {
        T entity = createFromDto(dto);
        entity.setId(id);
        entity.setComment(req.getComment());
        entity.setDateCreate(Instant.now());
        dao.save(entity);
    }

    @Transactional
    public void refreshFavorite(T entity, S dto) {
        fillFromDto(entity, dto);
        update(entity);
    }

    @Transactional(readOnly = true)
    public boolean favoriteExists(Long id) {
        return dao.get(id) != null;
    }

    @Transactional
    public T get(Long id) {
        return dao.get(id);
    }

    @Transactional
    public int delete(Long id) {
        return dao.delete(id);
    }

    @Transactional
    public List<T> getList(int page, int perPage) {
        List<T> employerDaoPage = dao.getPage(page, perPage);
        Set<Long> ids = employerDaoPage.stream().map(BaseFavorite::getId).collect(Collectors.toSet());
        if (!ids.isEmpty()) {
            dao.incrementViewsCount(ids);
            dao.changePopularity(ids, getPopularityValue());
        }
        return employerDaoPage;
    }

    @Transactional
    public void update(T entity) {
        dao.update(entity);
    }
}
