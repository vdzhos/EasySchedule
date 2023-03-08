package com.easyschedule.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
