package dev.biddan.chrismasjokbo.person.domain;

import lombok.Getter;

@Getter
public enum SexType {
    MALE("남성"),
    FEMALE("여성"),
    UNDEFINED("미정");

    private final String description;

    SexType(String description) {
        this.description = description;
    }


    public static SexType fromDescription(String sexDescription) {
        for (SexType sexType : SexType.values()) {
            if (sexType.description.equals(sexDescription)) {
                return sexType;
            }
        }

        throw new IllegalStateException("매핑되는 성별이 존재하지 않습니다: " + sexDescription);
    }
}
