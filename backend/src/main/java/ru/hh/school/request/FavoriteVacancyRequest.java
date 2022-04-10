package ru.hh.school.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FavoriteVacancyRequest extends BaseRequest {

    public FavoriteVacancyRequest() {
    }

    @Override
    @JsonProperty(value = "vacancy_id")
    public void setId(Long id) {
        this.id = id;
    }

}
