package net.aeroclient.aeroclientapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Notification {
    INFO("info"),
    ERROR("error"),
    NEUTRAL("neutral");

    private String type;
}
