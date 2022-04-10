package ru.hh.school.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

@Entity(name = FavoriteVacancy.ENTITY_NAME)
@Table(name = "favorite_vacancy")
public class FavoriteVacancy extends BaseFavorite {

    public static final String ENTITY_NAME = "FavoriteVacancy";

    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employer_id")
    private FavoriteEmployer employer;

    @Embedded
    private Salary salary;

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public FavoriteEmployer getEmployer() {
        return employer;
    }

    public void setEmployer(FavoriteEmployer employer) {
        this.employer = employer;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
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
                "createdAt = " + getCreatedAt() + ", " +
                "employer = " + getEmployer() + ", " +
                "salary = " + getSalary() + ")";
    }
}
