package com.playwring.utils.common;

import lombok.Getter;

@Getter
public enum Path {

    WEB_TABLES("/webtables");

    private final String path;

    Path(String path) {
        this.path = path;
    }
}
