package dev.biddan.chrismasjokbo.person.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Table(name = "people")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SexType sex;

    @Column(nullable = false)
    @Embedded
    private Birthdate birthdate;


    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private final List<PersonFeature> features = new ArrayList<>();

    @Builder
    public Person(String firstName, String lastName, SexType sex, Birthdate birthdate) {
        Assert.hasText(firstName, "firstName이 비어있습니다.");
        Assert.hasText(lastName, "firstName이 비어있습니다.");
        Assert.notNull(sex, "성별이 비어있습니다.");
        Assert.notNull(birthdate, "생년월일이 비어있습니다.");

        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.birthdate = birthdate;
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

    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class Birthdate {

        private static final String REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
        private static final Pattern PATTERN = Pattern.compile(REGEX);

        private String date;

        public Birthdate(String date) {
            Assert.hasText(date, "생년월일이 비어있습니다.");
            if (!PATTERN.matcher(date).matches()) {
                throw new IllegalArgumentException(
                        "잘못된 생년월일 형식입니다: " + date +
                        "%n생년월일 형식은 xxxx-xx-xx입니다.");
            }

            this.date = date;
        }
    }
}
