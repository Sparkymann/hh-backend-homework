package ru.hh.school.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import ru.hh.school.entity.Popularity;
import ru.hh.school.hhapi.dto.AreaDto;
import ru.hh.school.hhapi.dto.SalaryDto;
import ru.hh.school.utils.DefaultInstantDeserializer;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class FavoriteVacancyDto implements Serializable {

    private Long id;

    private String name;

    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = DefaultInstantDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant dateCreate;

    private AreaDto area;

    private String comment;

    private Popularity popularity = Popularity.REGULAR;

    private Integer viewsCount = 0;

    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = DefaultInstantDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant createdAt;

    private FavoriteEmployerDto employer;

    private SalaryDto salary;

    public FavoriteVacancyDto() {
    }

    public FavoriteVacancyDto(Long id, String name, Instant dateCreate, AreaDto area, String comment, Popularity popularity, Integer viewsCount, Instant createdAt, FavoriteEmployerDto employer, SalaryDto salary) {
        this.id = id;
        this.name = name;
        this.dateCreate = dateCreate;
        this.area = area;
        this.comment = comment;
        this.popularity = popularity;
        this.viewsCount = viewsCount;
        this.createdAt = createdAt;
        this.employer = employer;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Instant dateCreate) {
        this.dateCreate = dateCreate;
    }

    public AreaDto getArea() {
        return area;
    }

    public void setArea(AreaDto area) {
        this.area = area;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Popularity getPopularity() {
        return popularity;
    }

    public void setPopularity(Popularity popularity) {
        this.popularity = popularity;
    }

    public Integer getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(Integer viewsCount) {
        this.viewsCount = viewsCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public FavoriteEmployerDto getEmployer() {
        return employer;
    }

    public void setEmployer(FavoriteEmployerDto employer) {
        this.employer = employer;
    }

    public SalaryDto getSalary() {
        return salary;
    }

    public void setSalary(SalaryDto salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteVacancyDto entity = (FavoriteVacancyDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.dateCreate, entity.dateCreate) &&
                Objects.equals(this.area, entity.area) &&
                Objects.equals(this.comment, entity.comment) &&
                Objects.equals(this.popularity, entity.popularity) &&
                Objects.equals(this.viewsCount, entity.viewsCount) &&
                Objects.equals(this.createdAt, entity.createdAt) &&
                Objects.equals(this.employer, entity.employer) &&
                Objects.equals(this.salary, entity.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateCreate, area, comment, popularity, viewsCount, createdAt, employer, salary);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "dateCreate = " + dateCreate + ", " +
                "area = " + area + ", " +
                "comment = " + comment + ", " +
                "popularity = " + popularity + ", " +
                "viewsCount = " + viewsCount + ", " +
                "createdAt = " + createdAt + ", " +
                "employer = " + employer + ", " +
                "salary = " + salary + ")";
    }
}
