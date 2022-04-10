package ru.hh.school.resource;

import org.springframework.stereotype.Controller;
import ru.hh.school.hhapi.dto.EmployerDto;
import ru.hh.school.hhapi.response.EmployerResponse;

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
@Path("/employer")
@Controller
public class EmployerResource {

    private static final String EMPLOYERS_URL = "https://api.hh.ru/employers";

    @GET
    @Produces(value = "application/json")
    public List<EmployerDto> getEmployer(
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
        WebTarget target = getWebTarget(EMPLOYERS_URL)
                .queryParam("page", page)
                .queryParam("per_page", perPage);
        if (query != null && !query.isEmpty()) {
            target = target.queryParam("text", query);
        }
        EmployerResponse response = target.request().buildGet().invoke(EmployerResponse.class);
        return response.getItems();
    }

    @GET
    @Path(value = "/{employerId}")
    @Produces(value = "application/json")
    public EmployerDto getEmployer(
            @PathParam(value = "employerId") Long employerId
    ) {
        WebTarget target = getWebTarget(EMPLOYERS_URL).path(employerId.toString());
        return target.request().buildGet().invoke(EmployerDto.class);
    }

}
