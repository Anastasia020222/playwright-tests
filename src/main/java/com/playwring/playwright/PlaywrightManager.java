package com.playwring.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.playwring.playwright.mobile.ContextPlaywright;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Field;

public class PlaywrightManager implements AfterEachCallback, BeforeEachCallback {

    protected Page page;
    private BrowserContext context;
    private Playwright playwright;
    private Browser browser;

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        IPlaywright df = new PlaywrightFactory();
        ContextPlaywright contextPlaywright = new ContextPlaywright();
        playwright = Playwright.create();
        browser = df.create(playwright);
        context = contextPlaywright.getBrowserContext(browser, extensionContext);
        Object testInstance = extensionContext.getRequiredTestInstance();
        for (Field field : testInstance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Pages.class) && field.getType() == Page.class) {
                field.setAccessible(true);
                try {
                    field.set(testInstance, context.newPage());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        if (context != null) context.close();
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
