package dev.biddan.chrismasjokbo.person.feature.findpeople;

import dev.biddan.chrismasjokbo.person.domain.Person;
import dev.biddan.chrismasjokbo.person.domain.Person.Birthdate;
import dev.biddan.chrismasjokbo.person.domain.Person.PersonFeature;
import dev.biddan.chrismasjokbo.person.feature.PersonInfo;
import dev.biddan.chrismasjokbo.person.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindPeopleService {

    private final PersonRepository personRepository;

    @Transactional(readOnly = true)
    public FindPeopleInfo findByNameAndBirthdate(FindPeopleQuery query) {
        List<Person> people = personRepository.findByFirstNameAndLastNameAndBirthdate(
                query.firstName, query.lastName, new Birthdate(query.birthdate));
        return FindPeopleInfo.from(people);
    }

    @Builder
    public record FindPeopleQuery(
            String firstName,
            String lastName,
            String birthdate
    ) {

    }

    public record FindPeopleInfo(List<PersonInfo> people) {

        public static FindPeopleInfo from(List<Person> people) {
            List<PersonInfo> Infos = new ArrayList<>();

            for (Person person : people) {
                Infos.add(PersonInfo.of(person));
            }
            return new FindPeopleInfo(Infos);
        }
    }
}
