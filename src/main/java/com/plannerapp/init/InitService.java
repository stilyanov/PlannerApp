package com.plannerapp.init;

import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.entity.PriorityEnum;
import com.plannerapp.repo.PriorityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InitService implements CommandLineRunner {

    private final Map<PriorityEnum, String> descriptions = Map.of(
            PriorityEnum.URGENT, "An urgent problem that blocks the system use until the issue is resolved.",
            PriorityEnum.IMPORTANT, "A core functionality that your product is explicitly supposed to perform is compromised",
            PriorityEnum.LOW, "Should be fixed if time permits but can be postponed."
    );

    private final PriorityRepository priorityRepository;

    public InitService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (this.priorityRepository.count() > 0) {
            return;
        }

        List<Priority> toInsert = Arrays.stream(PriorityEnum.values())
                .map(priority -> new Priority(priority, descriptions.get(priority)))
                .collect(Collectors.toList());

        this.priorityRepository.saveAll(toInsert);
    }
}
