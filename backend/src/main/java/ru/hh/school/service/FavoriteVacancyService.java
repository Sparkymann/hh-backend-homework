package ru.hh.school.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.nab.common.properties.FileSettings;
import ru.hh.school.dao.AreaDao;
import ru.hh.school.dao.BaseFavoriteDao;
import ru.hh.school.dao.FavoriteEmployerDao;
import ru.hh.school.hhapi.dto.VacancyDto;
import ru.hh.school.entity.BaseFavorite;
import ru.hh.school.entity.FavoriteVacancy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FavoriteVacancyService extends BaseService<FavoriteVacancy, VacancyDto> {

    private final FavoriteEmployerDao employerDao;
    private final ModelMapper modelMapper;

    public FavoriteVacancyService(
            ModelMapper modelMapper,
            FavoriteEmployerDao employerDao,
            AreaDao areaDao,
            FileSettings fileSettings,
            BaseFavoriteDao<FavoriteVacancy> dao
    ) {
        super(fileSettings, dao, areaDao);
        this.modelMapper = modelMapper;
        this.employerDao = employerDao;
    }

    @Transactional
    public List<FavoriteVacancy> getList(int page, int perPage) {
        List<FavoriteVacancy> vacancyPage = super.getList(page, perPage);
        Set<Long> employerIds = vacancyPage.stream().map(FavoriteVacancy::getEmployer).map(BaseFavorite::getId).collect(Collectors.toSet());
        if (!employerIds.isEmpty()) {
            employerDao.incrementViewsCount(employerIds);
            employerDao.changePopularity(employerIds, getPopularityValue());
        }
        return vacancyPage;
    }

    @Override
    protected FavoriteVacancy createFromDto(VacancyDto dto) {
        FavoriteVacancy favoriteVacancy = new FavoriteVacancy();
        fillFromDto(favoriteVacancy, dto);
        return favoriteVacancy;
    }

    @Override
    protected void fillFromDto(FavoriteVacancy entity, VacancyDto dto) {
        modelMapper.map(dto, entity);
        entity.setName(dto.getName());
        entity.setArea(areaDao.getOrCreate(entity.getArea()));
    }

}
