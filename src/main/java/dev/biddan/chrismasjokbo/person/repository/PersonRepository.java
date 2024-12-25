package dev.biddan.chrismasjokbo.person.repository;

import dev.biddan.chrismasjokbo.person.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
