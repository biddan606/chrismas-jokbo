package dev.biddan.chrismasjokbo.person.domain;

public enum SexType {
    MALE("남성"),
    FEMALE("여성"),
    UNDEFINED("미정");

    private final String description;

    SexType(String description) {
        this.description = description;
    }
}
