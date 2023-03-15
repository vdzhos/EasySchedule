package com.easyschedule.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectPutDTO {

    @Column(nullable = false)
    @NotBlank(message = "Field shouldn't be empty!")
    @NotNull(message = "Mandatory field!")
    private String name;

    @Column(nullable = false)
    @Min(1)
    @Max(30)
    @NotNull(message = "Mandatory field!")
    private int quantOfGroups;

}
