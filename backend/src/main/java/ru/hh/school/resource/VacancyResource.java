package ru.hh.school.resource;

import org.springframework.stereotype.Controller;
import ru.hh.school.hhapi.dto.VacancyDto;
import ru.hh.school.hhapi.response.VacancyResponse;

import javax.inject.Singleton;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.WebTarget;
import java.util.List;

import static ru.hh.school.utils.CommonResourceUtils.getWebTarget;

@Singleton
@Path("/vacancy")
@Controller
public class VacancyResource {

    private static final String VACANCY_URL = "https://api.hh.ru/vacancies";

    @GET
    @Produces(value = "application/json")
    public List<VacancyDto> getVacancy(
            @QueryParam(value = "query")
                    String query,
            @QueryParam(value = "page")
            @DefaultValue(value = "0")
            @Min(value = 0)
                    Integer page,
            @QueryParam(value = "per_page")
            @DefaultValue(value = "20")
            @Min(value = 0)
            @Max(value = 100)
                    Integer perPage
    ) {
        WebTarget target = getWebTarget(VACANCY_URL)
                .queryParam("page", page)
                .queryParam("per_page", perPage);
        if (query != null && !query.isEmpty()) {
            target = target.queryParam("text", query);
        }
        VacancyResponse response = target.request().buildGet().invoke(VacancyResponse.class);
        return response.getItems();
    }

    @GET
    @Path(value = "/{vacancyId}")
    @Produces(value = "application/json")
    public VacancyDto getVacancy(
            @PathParam(value = "vacancyId") Long vacancyId
    ) {
        WebTarget target = getWebTarget(VACANCY_URL).path(vacancyId.toString());
        return target.request().buildGet().invoke(VacancyDto.class);
    }

}
