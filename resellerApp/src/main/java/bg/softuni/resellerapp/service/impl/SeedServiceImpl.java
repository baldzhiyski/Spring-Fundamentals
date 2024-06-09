package bg.softuni.resellerapp.service.impl;

import bg.softuni.resellerapp.model.Condition;
import bg.softuni.resellerapp.model.enums.ConditionName;
import bg.softuni.resellerapp.repository.ConditionRepository;
import bg.softuni.resellerapp.service.SeedService;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {

    private ConditionRepository conditionRepository;

    public SeedServiceImpl(ConditionRepository conditionRepository) {
        this.conditionRepository = conditionRepository;
    }

    @Override
    public void seedConditions() {
        if(this.conditionRepository.count() == 0){
            List<Condition> conditions = Arrays.stream(ConditionName.values())
                    .map(Condition::new)
                    .collect(Collectors.toList());

            this.conditionRepository.saveAllAndFlush(conditions);
        }
    }
}
