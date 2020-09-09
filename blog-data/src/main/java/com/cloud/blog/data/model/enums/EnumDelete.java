package com.cloud.blog.data.model.enums;

import lombok.Getter;

@Getter
public enum EnumDelete {

    UNDELETE((byte)0,"未删除"),
    DELETED((byte)1,"删除");

    private final Byte value;

    private final String name;

    EnumDelete(Byte value, String name) {
        this.value = value;
        this.name = name;
    }

}
