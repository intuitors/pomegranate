package org.pomegranate.demo.rest.spring.dal.domain;


import java.util.Arrays;
import java.util.Optional;

public enum PhoneType {
    MOBILE(1), HOME(2), WORK(3);

    private Integer value;

    PhoneType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static PhoneType valueOf(Integer value) {
        Optional<PhoneType> entry = Arrays.stream(PhoneType.values())
                .filter(phoneType -> phoneType.getValue().equals(value))
                .findFirst();

        if (entry.isPresent()) {
            return entry.get();
        } else {
            throw new IllegalArgumentException("Invalid type received");
        }


    }
}
