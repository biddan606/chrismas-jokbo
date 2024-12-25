package dev.biddan.chrismasjokbo.person.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "people")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private final List<PersonFeature> features = new ArrayList<>();

    @Builder
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addFeature(PersonFeature newPersonFeature) {
        features.add(newPersonFeature);
        newPersonFeature.setPerson(this);
    }

    @Entity
    @Table(name = "person_features")
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PersonFeature {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "feature_id")
        private Long id;

        private String name;
        private String description;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "person_id", nullable = false, updatable = false)
        private Person person;

        @Builder
        public PersonFeature(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public void setPerson(Person person) {
            this.person = person;
        }
    }
}
