package dev.biddan.chrismasjokbo.person.feature;

import dev.biddan.chrismasjokbo.person.domain.Person;
import dev.biddan.chrismasjokbo.person.domain.Person.PersonFeature;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;

@Builder
public record PersonInfo(
        String firstName,
        String lastName,
        String sex,
        String birthdate,
        List<PersonFeatureInfo> features
) {

    public static PersonInfo of(Person person) {
        List<PersonFeatureInfo> features = new ArrayList<>();
        for (PersonFeature feature : person.getFeatures()) {
            features.add(PersonFeatureInfo.of(feature));
        }

        return PersonInfo.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .sex(person.getSex().getDescription())
                .birthdate(person.getBirthdate().getDate())
                .features(features)
                .build();
    }

    @Builder
    public record PersonFeatureInfo(
            String name,
            String description
    ) {

        public static PersonFeatureInfo of(PersonFeature feature) {
            return PersonFeatureInfo.builder()
                    .name(feature.getName())
                    .description(feature.getDescription())
                    .build();
        }
    }


}
