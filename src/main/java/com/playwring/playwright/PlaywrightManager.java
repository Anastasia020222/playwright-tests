package com.playwring.playwright;

import com.microsoft.playwright.*;
import com.playwring.playwright.mobile.ContextPlaywright;
import com.playwring.user.User;
import com.playwring.user.CreateUserContext;
import com.playwring.utils.api.services.ApiContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Field;
import java.util.function.Consumer;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlaywrightManager implements BeforeEachCallback, AfterEachCallback, BeforeAllCallback, AfterAllCallback, ParameterResolver {

    private Page page;
    private BrowserContext context;
    private Playwright playwright;
    private Browser browser;
    private APIRequestContext apiRequestContext;
    private CreateUserContext userContext;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        PlaywrightFactory factory = new PlaywrightFactory();
        playwright = Playwright.create();
        browser = factory.create(playwright);
        userContext = new CreateUserContext();
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        setupBrowserContext(extensionContext);
        injectDependencies(extensionContext);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        safelyClose(userContext, CreateUserContext::deleteUser);
        safelyClose(context, BrowserContext::close);
        safelyClose(page, Page::close);
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        safelyClose(apiRequestContext, APIRequestContext::dispose);
        safelyClose(browser, Browser::close);
        safelyClose(playwright, Playwright::close);
    }

    private void setupBrowserContext(ExtensionContext extensionContext) {
        context = new ContextPlaywright().getBrowserContext(browser, extensionContext);
        page = context.newPage();
    }

    private void injectDependencies(ExtensionContext extensionContext) {
        Object testInstance = extensionContext.getRequiredTestInstance();

        for (Field field : testInstance.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.isAnnotationPresent(Pages.class) && field.getType().equals(Page.class)) {
                    field.set(testInstance, page);
                }
                if (field.isAnnotationPresent(ApiContext.class) && field.getType().equals(APIRequestContext.class)) {
                    apiRequestContext = playwright.request().newContext();
                    field.set(testInstance, apiRequestContext);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to inject dependencies into the test instance", e);
            }
        }
    }

    private void initializeUserContextIfNeeded() {
        userContext.generateNewUser();
    }

    private void initializeApiContextIfNeeded() {
        userContext = new CreateUserContext(apiRequestContext);
        userContext.generateNewUser();
        userContext.createUser();
        apiRequestContext = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://demoqa.com")
                .setExtraHTTPHeaders(userContext.getHeaders()));
        userContext.setApiRequestContext(apiRequestContext);
    }

    private <T> void safelyClose(T resource, Consumer<T> closeMethod) {
        if (resource != null) {
            try {
                closeMethod.accept(resource);
            } catch (Exception e) {
                System.err.println("Failed to close resource: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == User.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Object testInstance = extensionContext.getRequiredTestInstance();

        for (Field field : testInstance.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.isAnnotationPresent(ApiContext.class) && field.getType().equals(APIRequestContext.class)) {
                    initializeApiContextIfNeeded();
                    field.set(testInstance, apiRequestContext);
                } else {
                    initializeUserContextIfNeeded();
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to inject dependencies into the test instance", e);
            }
        }
        return userContext.getUser();
    }
}

