package org.pomegranate.demo.dal.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author sylenthira
 */
public enum PhoneType {
    MOBILE(1), HOME(2), WORK(3);

    private Integer type;

    PhoneType(Integer type) {
        this.type = type;
    }

    @JsonValue
    public Integer getType() {
        return type;
    }

    @JsonCreator
    public static PhoneType valueOf(Integer type) {
        Optional<PhoneType> entry = Arrays.stream(PhoneType.values())
                .filter(phoneType -> phoneType.getType().equals(type))
                .findFirst();

        if (entry.isPresent()) {
            return entry.get();
        } else {
            throw new IllegalArgumentException("Invalid type received");
        }


    }
}
