package ru.hh.school.resource;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import ru.hh.school.hhapi.dto.EmployerDto;
import ru.hh.school.dto.FavoriteEmployerDto;
import ru.hh.school.entity.FavoriteEmployer;
import ru.hh.school.request.FavoriteEmployerRequest;
import ru.hh.school.service.FavoriteEmployerService;

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
@Path("/favorites/employer")
@Controller
public class FavoriteEmployerResource extends BaseFavoriteResource<FavoriteEmployer, FavoriteEmployerDto, EmployerDto> {

    private final EmployerResource employerResource;
    private final ModelMapper modelMapper;

    public FavoriteEmployerResource(EmployerResource employerResource, FavoriteEmployerService employerService, ModelMapper modelMapper, HttpServletRequest httpServletRequest) {
        super(employerService, httpServletRequest);
        this.employerResource = employerResource;
        this.modelMapper = modelMapper;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createFavorite(FavoriteEmployerRequest req) {
        return createFav(req, () -> employerResource.getEmployer(req.getId()));
    }

    @POST
    @Path("/{employerId}/refresh")
    public Response refresh(
            @PathParam("employerId") Long employerId
    ) {
         return refreshFav(employerId, () -> employerResource.getEmployer(employerId));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FavoriteEmployerDto> getFavorite(
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
    @Path("/{employerId}")
    public Response changeComment(
            FavoriteEmployerRequest req,
            @PathParam("employerId") Long employerId
    ) {
        return changeFav(req, employerId);
    }

    @DELETE
    @Path("/{employerId}")
    public Response deleteFavorite(
            @PathParam("employerId") Long employerId
    ) {
        return deleteFav(employerId);
    }

    @Override
    protected FavoriteEmployerDto convert(FavoriteEmployer source) {
        return modelMapper.map(source, FavoriteEmployerDto.class);
    }

}
