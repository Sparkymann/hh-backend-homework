package ru.hh.school.hhapi.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.hh.school.hhapi.dto.VacancyDto;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VacancyResponse {

    private List<VacancyDto> items;

    public VacancyResponse() {}

    public List<VacancyDto> getItems() {
        return items;
    }

    public void setItems(List<VacancyDto> items) {
        this.items = items;
    }
}
