package com.playwring.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.playwring.components.ComponentMenu;
import io.qameta.allure.Step;

import static org.junit.jupiter.api.Assertions.*;

public class MobileBooksPage extends AbsBasePage<MobileBooksPage> {

    private final ComponentMenu componentMenu;

    public MobileBooksPage(Page page) {
        super(page);
        this.componentMenu = new ComponentMenu(page);
    }

    private final Locator selectRows = page.locator("select[aria-label='rows per page']");
    private final Locator buttonNext = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next"));
    private final Locator listBooks = page.locator("div[role='rowgroup']");
    private final Locator buttonMenu = page.getByRole(AriaRole.NAVIGATION).getByRole(AriaRole.BUTTON);
    private final Locator accordionList = page.locator(".element-group");

    private static final String countRows = "5";

    @Step("Проверяем select. Сортирует книги по количеству отображения")
    public MobileBooksPage checkSelectRows() {
        String originalValue = selectRows.inputValue();

        assertEquals(originalValue, "10", "Изначальное количество в селекте не равно 10");
        assertTrue(buttonNext.isDisabled(), "Кнопка Next не является активной");

        selectRows.selectOption(countRows);
        String actualValue = selectRows.inputValue();

        assertFalse(buttonNext.isDisabled(), "Кнопка Next является активной");
        assertEquals(actualValue, countRows, "Количество в селекне не стало " + countRows);
        return this;
    }

    @Step("Проверяем, что после сортировки количество книг в списке изменилось")
    public MobileBooksPage checkCountBooks() {
        int countBooks = listBooks.count();
        assertEquals(countRows, String.valueOf(countBooks), "Список книг отсортирован неверно. Количество книг не равно " + countRows);
        return this;
    }

    @Step("Проверяем открытие бокового меню")
    public MobileBooksPage openMenu() {
        buttonMenu.click();
        componentMenu.modalShouldBePresent();
        return this;
    }

    @Step("Проверяем количество категорий в боковом меню")
    public MobileBooksPage checkCountCategoryAccordion() {
        int count = accordionList.count();
        assertEquals(6, count, "Количество категорий в в боковом меню не равно 6");
        return this;
    }
}
