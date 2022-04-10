package ru.hh.school.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.hh.nab.common.properties.FileSettings;
import ru.hh.school.dao.AreaDao;
import ru.hh.school.dao.FavoriteEmployerDao;
import ru.hh.school.hhapi.dto.EmployerDto;
import ru.hh.school.entity.FavoriteEmployer;

@Service
public class FavoriteEmployerService extends BaseService<FavoriteEmployer, EmployerDto> {

    private final ModelMapper modelMapper;

    public FavoriteEmployerService(ModelMapper modelMapper, FavoriteEmployerDao dao, AreaDao areaDao, FileSettings fileSettings) {
        super(fileSettings, dao, areaDao);
        this.modelMapper = modelMapper;
    }

    @Override
    protected FavoriteEmployer createFromDto(EmployerDto dto) {
        FavoriteEmployer favoriteEmployer = new FavoriteEmployer();
        fillFromDto(favoriteEmployer, dto);
        return favoriteEmployer;
    }

    @Override
    protected void fillFromDto(FavoriteEmployer favoriteEmployer, EmployerDto dto) {
        modelMapper.map(dto, favoriteEmployer);
        favoriteEmployer.setArea(areaDao.getOrCreate(favoriteEmployer.getArea()));
    }
}
