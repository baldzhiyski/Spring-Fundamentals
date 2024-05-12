package org.softuni.mobilele.domain.dtos.brand;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {

    private String name;
    private Date created;
    private Date modified;
}
