package com.playwring.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.playwring.playwright.mobile.ContextPlaywright;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Field;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlaywrightManager implements BeforeEachCallback, AfterEachCallback, BeforeAllCallback, AfterAllCallback {

    private Page page;
    private BrowserContext context;
    private Playwright playwright;
    private Browser browser;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        IPlaywright df = new PlaywrightFactory();
        playwright = Playwright.create();
        browser = df.create(playwright);
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        ContextPlaywright contextPlaywright = new ContextPlaywright();
        context = contextPlaywright.getBrowserContext(browser, extensionContext);
        page = context.newPage();

        Object testInstance = extensionContext.getRequiredTestInstance();
        for (Field field : testInstance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Pages.class) && field.getType().equals(Page.class)) {
                field.setAccessible(true);
                field.set(testInstance, page);
            }
        }
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        if (context != null) context.close();
        if (page != null) page.close();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
