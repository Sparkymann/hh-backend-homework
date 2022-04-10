package ru.hh.school.resource;

import ru.hh.school.entity.BaseFavorite;
import ru.hh.school.request.BaseRequest;
import ru.hh.school.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class BaseFavoriteResource<T extends BaseFavorite, S, U> {

    protected final BaseService<T, U> service;
    protected final HttpServletRequest httpServletRequest;

    public BaseFavoriteResource(BaseService<T, U> service, HttpServletRequest httpServletRequest) {
        this.service = service;
        this.httpServletRequest = httpServletRequest;
    }

    protected abstract S convert(T source);

    protected Response createFav(BaseRequest req, Supplier<U> dtoSupplier) {
        Long id = req.getId();
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (service.favoriteExists(id)) {
            return Response.noContent().build();
        }
        service.saveFavorite(req, id, dtoSupplier.get());
        return Response.created(UriBuilder.fromPath(httpServletRequest.getRequestURI()).path(id.toString()).build()).build();
    }

    protected Response refreshFav(Long employerId, Supplier<U> dtoSupplier) {
        Optional.ofNullable(service.get(employerId))
                .ifPresentOrElse(
                        employer -> service.refreshFavorite(employer, dtoSupplier.get()),
                        () -> {throw new NotFoundException();});
        return Response.ok().build();
    }

    protected Response deleteFav(Long employerId) {
        service.delete(employerId);
        return Response.ok().build();
    }

    protected Response changeFav(BaseRequest req, Long employerId) {
        return Optional.ofNullable(service.get(employerId))
                .map(employer -> {
                    employer.setComment(req.getComment());
                    service.update(employer);
                    return employer;
                })
                .map(ignored -> Response.ok().build())
                .orElse(Response.noContent().build());
    }

    protected List<S> getFav(Integer page, Integer perPage) {
        return service.getList(page, perPage)
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
