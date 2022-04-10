package ru.hh.school.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FavoriteEmployerRequest extends BaseRequest {

    public FavoriteEmployerRequest() {
    }

    @Override
    @JsonProperty(value = "employer_id")
    public void setId(Long id) {
        this.id = id;
    }

}
