package com.playwring.playwright.options;

import com.microsoft.playwright.BrowserType;

public class BrowserOptions {

    public BrowserType.LaunchOptions getOptions() {
        return new BrowserType.LaunchOptions()
                .setHeadless(false);
    }
}
