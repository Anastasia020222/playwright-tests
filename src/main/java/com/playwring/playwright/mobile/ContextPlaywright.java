package com.playwring.playwright.mobile;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ContextPlaywright {

    public BrowserContext getBrowserContext(Browser browser, ExtensionContext extensionContext) {
        Mobile mobile = extensionContext.getRequiredTestMethod().getAnnotation(Mobile.class);
        if (mobile != null) {
            Browser.NewContextOptions newContextOptions = new Browser.NewContextOptions()
                    .setUserAgent("Mozilla/5.0 (Linux; Android 8.0.0; SM-G955U Build/R16NW) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Mobile Safari/537.36")
                    .setViewportSize(360, 740)
                    .setIsMobile(true);
            return browser.newContext(newContextOptions);
        } else {
            return browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080));
        }
    }
}
