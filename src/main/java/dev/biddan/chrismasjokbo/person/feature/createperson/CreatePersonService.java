package dev.biddan.chrismasjokbo.person.feature.createperson;

import dev.biddan.chrismasjokbo.person.domain.Person;
import dev.biddan.chrismasjokbo.person.domain.Person.PersonFeature;
import dev.biddan.chrismasjokbo.person.feature.createperson.CreatePersonService.CreatePersonCommand.CreatePersonFeatureCommand;
import dev.biddan.chrismasjokbo.person.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePersonService {

    private final PersonRepository personRepository;

    @Transactional
    public Long create(CreatePersonCommand command) {
        Person newPerson = Person.builder()
                .firstName(command.firstName)
                .lastName(command.lastName)
                .build();

        for (CreatePersonFeatureCommand personFeatureCommand : command.createPersonFeatures) {
            PersonFeature newPersonFeature = PersonFeature.builder()
                    .name(personFeatureCommand.name)
                    .description(personFeatureCommand.description)
                    .build();

            newPerson.addFeature(newPersonFeature);
        }

        return personRepository.save(newPerson).getId();
    }

    @Builder
    public record CreatePersonCommand(
            String firstName,
            String lastName,
            List<CreatePersonFeatureCommand> createPersonFeatures
    ) {

        @Builder
        public record CreatePersonFeatureCommand(
                String name,
                String description
        ) {
        }
    }
}
