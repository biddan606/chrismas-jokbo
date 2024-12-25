package dev.biddan.chrismasjokbo.person.repository;

import dev.biddan.chrismasjokbo.person.domain.Person;
import dev.biddan.chrismasjokbo.person.domain.Person.Birthdate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByFirstNameAndLastNameAndBirthdate(String firstName, String lastName, Birthdate birthdate);
}
