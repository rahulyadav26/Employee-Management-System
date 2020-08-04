package com.assignment.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "industry_type")
    private String industryType;

    @JsonProperty(value = "head_office")
    private String headOffice;

    @JsonProperty(value = "founder")
    private String founder;

}
