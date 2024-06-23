package bg.softuni.exam.service.impl;

import bg.softuni.exam.model.entity.Style;
import bg.softuni.exam.model.entity.enums.StyleName;
import bg.softuni.exam.repository.StyleRepository;
import bg.softuni.exam.service.SeedService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {

    private StyleRepository styleRepository;

    public SeedServiceImpl(StyleRepository styleRepository) {
        this.styleRepository = styleRepository;
    }

    @Override
    public void seedStyle() {
        if(this.styleRepository.count()==0) {
            List<Style> styles = Arrays.stream(StyleName.values())
                    .map(val -> {
                        Style style = new Style();
                        style.setStyleName(val);
                        return style;
                    }).toList();
            this.styleRepository.saveAllAndFlush(styles);
        }
    }
}
