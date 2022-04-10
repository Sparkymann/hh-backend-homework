package ru.hh.school.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = FavoriteEmployer.ENTITY_NAME)
@Table(name = "favorite_employer")
public class FavoriteEmployer extends BaseFavorite {

    public static final String ENTITY_NAME = "FavoriteEmployer";

    @Column(name = "description")
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "name = " + getName() + ", " +
                "dateCreate = " + getDateCreate() + ", " +
                "area = " + getArea() + ", " +
                "comment = " + getComment() + ", " +
                "popularity = " + getPopularity() + ", " +
                "viewsCount = " + getViewsCount() + ", " +
                "description = " + getDescription() + ")";
    }
}
