package ru.hh.school.resource;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import ru.hh.school.dto.FavoriteVacancyDto;
import ru.hh.school.entity.FavoriteVacancy;
import ru.hh.school.hhapi.dto.VacancyDto;
import ru.hh.school.request.FavoriteVacancyRequest;
import ru.hh.school.service.FavoriteEmployerService;
import ru.hh.school.service.FavoriteVacancyService;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Singleton
@Path("/favorites/vacancy")
@Controller
public class FavoriteVacancyResource extends BaseFavoriteResource<FavoriteVacancy, FavoriteVacancyDto, VacancyDto> {

    private final VacancyResource vacancyResource;
    private final ModelMapper modelMapper;
    private final FavoriteEmployerService employerService;

    public FavoriteVacancyResource(
            VacancyResource vacancyResource,
            FavoriteVacancyService vacancyService,
            ModelMapper modelMapper,
            HttpServletRequest httpServletRequest,
            FavoriteEmployerService favoriteEmployerService
    ) {
        super(vacancyService, httpServletRequest);
        this.vacancyResource = vacancyResource;
        this.modelMapper = modelMapper;
        this.employerService = favoriteEmployerService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createFavorite(FavoriteVacancyRequest req) {
        VacancyDto vacancy = vacancyResource.getVacancy(req.getId());
        Long employerId = vacancy.getEmployer().getId();
        if (!employerService.favoriteExists(employerId)) {
            return Response
                    .status(Response.Status.NOT_FOUND.getStatusCode(), "Employer with id " + employerId + " does not exist")
                    .build();
        }
        return createFav(req, () -> vacancy);
    }

    @POST
    @Path("/{vacancyId}/refresh")
    public Response refresh(
            @PathParam("vacancyId") Long vacancyId
    ) {
        return refreshFav(vacancyId, () -> vacancyResource.getVacancy(vacancyId));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FavoriteVacancyDto> getFavorite(
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
        return getFav(page, perPage);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{vacancyId}")
    public Response changeComment(
            FavoriteVacancyRequest req,
            @PathParam("vacancyId") Long vacancyId
    ) {
        return changeFav(req, vacancyId);
    }

    @DELETE
    @Path("/{vacancyId}")
    public Response deleteFavorite(
            @PathParam("vacancyId") Long vacancyId
    ) {
        return deleteFav(vacancyId);
    }

    @Override
    protected FavoriteVacancyDto convert(FavoriteVacancy source) {
        return modelMapper.map(source, FavoriteVacancyDto.class);
    }
}
