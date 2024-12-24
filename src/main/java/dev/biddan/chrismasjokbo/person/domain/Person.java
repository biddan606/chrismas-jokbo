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
import lombok.Getter;

@Entity
@Table(name = "people")
@Getter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private final List<PersonFeature> features = new ArrayList<>();


    @Entity
    @Table(name = "person_features")
    @Getter
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
    }
}
