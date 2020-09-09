package com.cloud.blog.data.model.enums;

import lombok.Getter;

@Getter
public enum EnumLock {

    UNLOCK((byte)0,"未锁定"),
    LOCKED((byte)1,"锁定");

    private final Byte value;

    private final String name;

    EnumLock(Byte value, String name) {
        this.value = value;
        this.name = name;
    }
}
