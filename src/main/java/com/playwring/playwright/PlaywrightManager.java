package com.playwring.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;

public class PlaywrightManager {

    protected Page page;
    private BrowserContext context;
    private Playwright playwright;
    private Browser browser;

    @BeforeEach
    public void create() {
        IPlaywright df = new PlaywrightFactory();
        playwright = Playwright.create();
        browser = df.create(playwright);
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080));
        page = context.newPage();
    }

    @AfterEach
    public void close() {
        if (context != null) context.close();
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
