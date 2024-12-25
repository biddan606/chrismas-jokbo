package dev.biddan.chrismasjokbo.person.feature.findpeople;

import static org.junit.jupiter.api.Assertions.*;

import dev.biddan.chrismasjokbo.person.domain.Person;
import dev.biddan.chrismasjokbo.person.domain.Person.Birthdate;
import dev.biddan.chrismasjokbo.person.domain.SexType;
import dev.biddan.chrismasjokbo.person.feature.createperson.CreatePersonService;
import dev.biddan.chrismasjokbo.person.feature.createperson.CreatePersonService.CreatePersonCommand;
import dev.biddan.chrismasjokbo.person.feature.findpeople.FindPeopleService.FindPeopleInfo;
import dev.biddan.chrismasjokbo.person.feature.findpeople.FindPeopleService.FindPeopleQuery;
import dev.biddan.chrismasjokbo.person.repository.PersonRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledForJreRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class FindPeopleServiceTest {

    @Autowired
    private FindPeopleService findPeopleService;

    @Autowired
    private PersonRepository personRepository;

    @DisplayName("이름과 생년월일이 매칭되는 사람들을 찾는다")
    @Test
    void findByNameAndBirthdate_success() {
        Person person1 = Person.builder()
                .firstName("유")
                .lastName("원우")
                .sex(SexType.MALE)
                .birthdate(new Birthdate("2000-01-01"))
                .build();

        Person person2 = Person.builder()
                .firstName("유")
                .lastName("원우")
                .sex(SexType.MALE)
                .birthdate(new Birthdate("2000-01-02"))
                .build();

        Person person3 = Person.builder()
                .firstName("강")
                .lastName("원우")
                .sex(SexType.MALE)
                .birthdate(new Birthdate("2000-01-01"))
                .build();

        personRepository.saveAll(List.of(person1, person2, person3));

        FindPeopleQuery query = FindPeopleQuery.builder()
                .firstName("유")
                .lastName("원우")
                .birthdate("2000-01-01")
                .build();

        FindPeopleInfo actual = findPeopleService.findByNameAndBirthdate(query);

        Assertions.assertThat(actual.people()).size().isEqualTo(1);
    }
}
