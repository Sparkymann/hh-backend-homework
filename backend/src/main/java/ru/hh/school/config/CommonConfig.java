package ru.hh.school.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.nab.starter.NabCommonConfig;
import ru.hh.school.dao.AreaDao;
import ru.hh.school.dao.FavoriteEmployerDao;
import ru.hh.school.dao.FavoriteVacancyDao;
import ru.hh.school.resource.EmployerResource;
import ru.hh.school.resource.ExampleResource;
import ru.hh.school.resource.FavoriteEmployerResource;
import ru.hh.school.resource.FavoriteVacancyResource;
import ru.hh.school.resource.VacancyResource;
import ru.hh.school.service.FavoriteEmployerService;
import ru.hh.school.service.FavoriteVacancyService;

@Configuration
@Import({
        // import your beans here
        EmployerResource.class,
        VacancyResource.class,
        FavoriteEmployerResource.class,
        FavoriteEmployerDao.class,
        AreaDao.class,
        FavoriteEmployerService.class,
        FavoriteVacancyDao.class,
        FavoriteVacancyService.class,
        FavoriteVacancyResource.class,
        ExampleResource.class,
        NabCommonConfig.class
})
public class CommonConfig {

  @Bean
  public MappingConfig mappingConfig() {
    MappingConfig mappingConfig = new MappingConfig();
    mappingConfig.addPackagesToScan("ru.hh.school.entity");
    return mappingConfig;
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

}
