package ru.hh.school.hhapi.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.hh.school.hhapi.dto.EmployerDto;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployerResponse {

    List<EmployerDto> items;

    public EmployerResponse() {}

    public List<EmployerDto> getItems() {
        return items;
    }

    public void setItems(List<EmployerDto> items) {
        this.items = items;
    }

}
