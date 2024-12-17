package com.playwring.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.playwring.playwright.options.BrowserOptions;

import static com.playwring.playwright.TypeBrowser.getTypeBrowser;

public class PlaywrightFactory implements IPlaywright {

    @Override
    public Browser create(Playwright playwright) {
        TypeBrowser browser = getTypeBrowser();
        switch (browser) {
            case CHROME:
                BrowserType chromium = playwright.chromium();
                return chromium.launch(new BrowserOptions().getOptions());
            case FIREFOX:
                BrowserType firefox = playwright.firefox();
                return firefox.launch(new BrowserOptions().getOptions());
            case WEBKIT:
                BrowserType webkit = playwright.webkit();
                return webkit.launch(new BrowserOptions().getOptions());
            default:
                throw new RuntimeException("Указанный тип браузера не был найден.");
        }
    }
}
