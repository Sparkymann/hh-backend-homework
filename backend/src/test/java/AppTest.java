import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import ru.hh.nab.starter.NabApplication;
import ru.hh.nab.testbase.NabTestBase;
import ru.hh.school.dto.FavoriteEmployerDto;
import ru.hh.school.dto.FavoriteVacancyDto;
import ru.hh.school.entity.FavoriteEmployer;
import ru.hh.school.entity.FavoriteVacancy;
import ru.hh.school.hhapi.dto.EmployerDto;
import ru.hh.school.hhapi.dto.VacancyDto;
import ru.hh.school.request.FavoriteEmployerRequest;
import ru.hh.school.request.FavoriteVacancyRequest;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@ContextConfiguration(classes = AppTestConfig.class)
public class AppTest extends NabTestBase {

  private static final String FAVORITES_PART = "/favorites";
  private static final String EMPLOYERS_PART = "employer";
  private static final String VACANCIES_PART = "vacancy";

  @Autowired
  private DatabaseHelper helper;

  public AppTest() {}

  @Override
  protected NabApplication getApplication() {
    return NabApplication.builder().configureJersey().bindToRoot().build();
  }

  @Before
  public void before() {}

  @After
  public void cleanTable() {
    helper.clean(FavoriteVacancy.ENTITY_NAME, FavoriteEmployer.ENTITY_NAME);
  }

  @Test
  public void createUserAndCompanyTest() {
    Response response = createRequest("/")
            .buildGet()
            .invoke();

    assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
  }

  @Test(expected = NotFoundException.class)
  public void vacancyNotFound() {
    getFromHH(VACANCIES_PART, -1L, new GenericType<List<VacancyDto>>() {});
  }

  @Test(expected = NotFoundException.class)
  public void employerNotFound() {
    getFromHH(EMPLOYERS_PART, -1L, new GenericType<List<EmployerDto>>() {});
  }

  @Test
  public void addFavoriteVacancyWithoutEmployer() {
    Response notFoundResponse = createFavoriteVacancy(false);

    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), notFoundResponse.getStatus());
  }

  @Test
  public void addFavoriteVacancy() {
    Response favoriteVacancy = createFavoriteVacancy(true);

    assertEquals(Response.Status.CREATED.getStatusCode(), favoriteVacancy.getStatus());
  }

  @Test
  public void addFavoriteEmployer() {
    EmployerDto dto = getFromHH(EMPLOYERS_PART, null, new GenericType<>() {});
    Response favoriteEmployer = createFavoriteEmployer(dto.getId());

    assertEquals(Response.Status.CREATED.getStatusCode(), favoriteEmployer.getStatus());
  }

  @Test
  public void employerCounterTest() {
    createFavoriteEmployer(null);

    FavoriteEmployerDto firstResponse = getFavorite(EMPLOYERS_PART, new GenericType<>() {});
    assertEquals(0, firstResponse.getViewsCount().intValue());

    FavoriteEmployerDto secondResponse = getFavorite(EMPLOYERS_PART, new GenericType<>() {});
    assertEquals(1, secondResponse.getViewsCount().intValue());
  }

  @Test
  public void vacancyCounterTest() {
    createFavoriteVacancy(true);

    FavoriteVacancyDto firstVacancyResponse = getFavorite(VACANCIES_PART, new GenericType<>() {});
    assertEquals(0, firstVacancyResponse.getViewsCount().intValue());

    FavoriteVacancyDto secondVacancyResponse = getFavorite(VACANCIES_PART, new GenericType<>() {});
    assertEquals(1, secondVacancyResponse.getViewsCount().intValue());

    //Просмотры работодателя увеличивались при запросах вакансии
    FavoriteEmployerDto firstEmployerResponse = getFavorite(EMPLOYERS_PART, new GenericType<>() {});
    assertEquals(2, firstEmployerResponse.getViewsCount().intValue());
  }

  @Test
  public void deleteEmployerTest() {
    Response favoriteEmployer = createFavoriteEmployer(null);

    FavoriteEmployerDto firstEmployerResponse = getFavorite(EMPLOYERS_PART, new GenericType<>() {});
    assertNotNull(firstEmployerResponse);

    Response deleteResponse = createRequestFromAbsoluteUrl(favoriteEmployer.getLocation().toString()).buildDelete().invoke();
    assertEquals(Response.Status.OK.getStatusCode(), deleteResponse.getStatus());

    FavoriteEmployerDto secondEmployerResponse = getFavorite(EMPLOYERS_PART, new GenericType<>() {});
    assertNull(secondEmployerResponse);
  }

  @Test
  public void deleteVacancyTest() {
    Response favoriteVacancy = createFavoriteVacancy(true);

    FavoriteVacancyDto firstVacancyResponse = getFavorite(VACANCIES_PART, new GenericType<>() {});
    assertNotNull(firstVacancyResponse);

    Response deleteResponse = createRequestFromAbsoluteUrl(favoriteVacancy.getLocation().toString()).buildDelete().invoke();
    assertEquals(Response.Status.OK.getStatusCode(), deleteResponse.getStatus());

    FavoriteVacancyDto secondVacancyResponse = getFavorite(VACANCIES_PART, new GenericType<>() {});
    assertNull(secondVacancyResponse);
  }

  @Test
  public void updateEmployerTest() {
    Response favoriteEmployer = createFavoriteEmployer(null);

    FavoriteEmployerDto firstEmployerResponse = getFavorite(EMPLOYERS_PART, new GenericType<>() {});
    assertNull(firstEmployerResponse.getComment());

    FavoriteEmployerRequest request = new FavoriteEmployerRequest();
    request.setId(firstEmployerResponse.getId());
    request.setComment("test");
    Response putResponse = createRequestFromAbsoluteUrl(favoriteEmployer.getLocation().toString())
            .buildPut(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE))
            .invoke();
    assertEquals(Response.Status.OK.getStatusCode(), putResponse.getStatus());

    FavoriteEmployerDto secondEmployerResponse = getFavorite(EMPLOYERS_PART, new GenericType<>() {});
    assertEquals("test", secondEmployerResponse.getComment());
  }

  @Test
  public void updateVacancyTest() {
    Response favoriteVacancy = createFavoriteVacancy(true);

    FavoriteVacancyDto firstVacancyResponse = getFavorite(VACANCIES_PART, new GenericType<>() {});
    assertNull(firstVacancyResponse.getComment());

    FavoriteVacancyRequest request = new FavoriteVacancyRequest();
    request.setId(firstVacancyResponse.getId());
    request.setComment("test");

    Response putResponse = createRequestFromAbsoluteUrl(favoriteVacancy.getLocation().toString())
            .buildPut(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE))
            .invoke();

    assertEquals(Response.Status.OK.getStatusCode(), putResponse.getStatus());

    FavoriteVacancyDto secondVacancyResponse = getFavorite(VACANCIES_PART, new GenericType<>() {});
    assertEquals("test", secondVacancyResponse.getComment());
  }

  private <T> T getFromHH(String urlPart, Long id, GenericType<List<T>> responseType) {
    WebTarget target = target("/").path(urlPart);
    if (id != null) {
      target = target.path(id.toString());
    }
    return target.queryParam("per_page", 1)
            .request()
            .accept(MediaType.APPLICATION_JSON_TYPE)
            .buildGet()
            .invoke(responseType)
            .stream().findFirst().orElseThrow();
  }

  private <T> T getFavorite(String urlPart, GenericType<List<T>> responseType) {
    return target(FAVORITES_PART).path(urlPart).request()
            .buildGet()
            .invoke(responseType)
            .stream()
            .findFirst().orElse(null);
  }

  private Response createFavoriteVacancy(boolean withEmployer) {
    VacancyDto dto = getFromHH(VACANCIES_PART, null, new GenericType<>() {});

    if (withEmployer) {
      createFavoriteEmployer(dto.getEmployer().getId());
    }

    FavoriteVacancyRequest vacancyRequest = new FavoriteVacancyRequest();
    vacancyRequest.setId(dto.getId());
    return target(FAVORITES_PART).path(VACANCIES_PART).request()
            .accept(MediaType.APPLICATION_JSON_TYPE)
            .buildPost(Entity.entity(vacancyRequest, MediaType.APPLICATION_JSON_TYPE))
            .invoke();
  }

  private Response createFavoriteEmployer(Long id) {
    if (id == null) {
      EmployerDto dto = getFromHH(EMPLOYERS_PART, null, new GenericType<>() {});
      id = dto.getId();
    }
    FavoriteEmployerRequest favoriteEmployerRequest = new FavoriteEmployerRequest();
    favoriteEmployerRequest.setId(id);
    return target(FAVORITES_PART).path(EMPLOYERS_PART).request()
            .accept(MediaType.APPLICATION_JSON_TYPE)
            .buildPost(Entity.entity(favoriteEmployerRequest, MediaType.APPLICATION_JSON_TYPE))
            .invoke();
  }

}
