package ru.hh.school.hhapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.hh.school.utils.DefaultInstantDeserializer;
import ru.hh.school.utils.DefaultInstantSerializer;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VacancyDto {

    private Long id;

    private String name;

    private AreaDto area;

    private SalaryDto salary;

    @JsonSerialize(using = DefaultInstantSerializer.class)
    @JsonDeserialize(using = DefaultInstantDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty(value = "created_at")
    private Instant createdAt;

    private EmployerDto employer;

    public VacancyDto() {}

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

    public AreaDto getArea() {
        return area;
    }

    public void setArea(AreaDto area) {
        this.area = area;
    }

    public SalaryDto getSalary() {
        return salary;
    }

    public void setSalary(SalaryDto salary) {
        this.salary = salary;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public EmployerDto getEmployer() {
        return employer;
    }

    public void setEmployer(EmployerDto employer) {
        this.employer = employer;
    }
}
