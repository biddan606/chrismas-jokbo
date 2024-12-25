package dev.biddan.chrismasjokbo.person.feature.createperson;

import dev.biddan.chrismasjokbo.person.feature.createperson.CreatePersonService.CreatePersonCommand;
import dev.biddan.chrismasjokbo.person.feature.createperson.CreatePersonService.CreatePersonCommand.CreatePersonFeatureCommand;
import dev.biddan.chrismasjokbo.person.repository.PersonRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CreatePersonServiceTest {

    @Autowired
    private CreatePersonService createPersonService;

    @Autowired
    private PersonRepository personRepository;

    @DisplayName("개인 정보를 등록한다")
    @Test
    void create_success() {
        CreatePersonFeatureCommand featureCommand1 = CreatePersonFeatureCommand.builder()
                .name("거주지")
                .description("산본")
                .build();
        CreatePersonFeatureCommand featureCommand2 = CreatePersonFeatureCommand.builder()
                .name("취미")
                .description("축구")
                .build();
        CreatePersonCommand command = CreatePersonCommand.builder()
                .firstName("Wonwoo")
                .lastName("Yu")
                .sex("남성")
                .birthday("1996-06-06")
                .createPersonFeatures(List.of(featureCommand1, featureCommand2))
                .build();

        Long newPersonId = createPersonService.create(command);

        Assertions.assertThat(personRepository.findById(newPersonId))
                .isPresent()
                .hasValueSatisfying(person -> {
                            Assertions.assertThat(person.getFeatures())
                                    .hasSize(2);
                            Assertions.assertThat(person.getFirstName())
                                    .isEqualTo(command.firstName());
                            Assertions.assertThat(person.getLastName())
                                    .isEqualTo(command.lastName());
                            Assertions.assertThat(person.getSex().getDescription())
                                    .isEqualTo(command.sex());
                            Assertions.assertThat(person.getBirthday().getDate())
                                    .isEqualTo(command.birthday());
                        }
                );
    }
}
