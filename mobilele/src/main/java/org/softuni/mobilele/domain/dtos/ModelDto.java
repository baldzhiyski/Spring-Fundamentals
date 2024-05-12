package org.softuni.mobilele.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelDto {
    private String name;
    private String category;

    private String imageUrl;
    private Long startYear;
    private Long endYear;

    private String created;
    private String modified;

    private BrandIdDto brand;
}
