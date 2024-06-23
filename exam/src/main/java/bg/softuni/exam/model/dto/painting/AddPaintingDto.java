package bg.softuni.exam.model.dto.painting;

import bg.softuni.exam.model.entity.enums.StyleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class AddPaintingDto {

    @Length(min = 5,max = 40)
    private String name;

    @Length(min = 5,max = 40)
    private String author;

    @NotNull
    private StyleName styleName;

    @NotBlank
    private String imageUrl;


}
