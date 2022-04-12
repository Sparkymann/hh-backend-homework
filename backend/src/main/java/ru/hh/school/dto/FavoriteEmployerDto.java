package ru.hh.school.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.hh.school.entity.Popularity;
import ru.hh.school.hhapi.dto.AreaDto;
import ru.hh.school.utils.DefaultInstantDeserializer;
import ru.hh.school.utils.DefaultInstantSerializer;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class FavoriteEmployerDto implements Serializable {

    private Long id;

    private String name;

    @JsonSerialize(using = DefaultInstantSerializer.class)
    @JsonDeserialize(using = DefaultInstantDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant dateCreate;

    private AreaDto area;

    private String comment;

    private Popularity popularity = Popularity.REGULAR;

    private Integer viewsCount = 0;

    private String description;

    public FavoriteEmployerDto() {
    }

    public FavoriteEmployerDto(Long id, String name, Instant dateCreate, AreaDto area, String comment, Popularity popularity, Integer viewsCount, String description) {
        this.id = id;
        this.name = name;
        this.dateCreate = dateCreate;
        this.area = area;
        this.comment = comment;
        this.popularity = popularity;
        this.viewsCount = viewsCount;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteEmployerDto entity = (FavoriteEmployerDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.dateCreate, entity.dateCreate) &&
                Objects.equals(this.area, entity.area) &&
                Objects.equals(this.comment, entity.comment) &&
                Objects.equals(this.popularity, entity.popularity) &&
                Objects.equals(this.viewsCount, entity.viewsCount) &&
                Objects.equals(this.description, entity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateCreate, area, comment, popularity, viewsCount, description);
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
                "description = " + description + ")";
    }
}
