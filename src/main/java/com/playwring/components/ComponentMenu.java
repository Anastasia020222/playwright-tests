package com.playwring.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ComponentMenu extends AbsComponents implements IMenu {

    public ComponentMenu(Page page) {
        super(page);
    }

    private final Locator menu = page.locator(".left-pannel.left-pannel-wrapper-enter-done");

    @Override
    public void modalShouldBePresent() {
        String display = (String) menu.evaluate("el => window.getComputedStyle(el).display");
        assertNotEquals("none", display, "Боковое меню не расскрылось");
    }

    @Override
    public void modalShouldNotBePresent() {
    }
}
