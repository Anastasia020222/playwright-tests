package com.playwring.playwright;

import lombok.Getter;

@Getter
public enum TypeBrowser {

    CHROME("chrome"),
    FIREFOX("firefox"),
    WEBKIT("webkit");

    private String browser;

    TypeBrowser(String browser) {
        this.browser = browser;
    }

    public static TypeBrowser getTypeBrowser() {
        try {
            return TypeBrowser.valueOf(System.getProperty("browser").trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Указанный тип браузера не был найден. Возможные варианты: chrome, firefox");
        }
    }
}
