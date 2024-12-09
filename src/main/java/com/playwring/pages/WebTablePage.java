package com.playwring.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.playwring.user.GenerateUser;
import com.playwring.user.User;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.playwring.utils.allure.AllureAttachments.allureAttachmentText;
import static org.junit.jupiter.api.Assertions.*;

public class WebTablePage extends AbsBasePage<WebTablePage> {

    public WebTablePage(Page page) {
        super(page);
    }

    private final User generateUser = new GenerateUser().generateUser();

    Locator buttonAdd = page.locator("id=addNewRecordButton");
    Locator modal = page.locator("div[role='dialog']");
    Locator firstName = page.locator("id=firstName");
    Locator lastName = page.locator("id=lastName");
    Locator email = page.locator("id=userEmail");
    Locator age = page.locator("id=age");
    Locator salary = page.locator("id=salary");
    Locator department = page.locator("id=department");
    Locator submit = page.locator("id=submit");
    Locator listUser = page.locator("div[role='rowgroup']");

    @Step("Открытие диалогового окна регистрации пользователя")
    public WebTablePage openDialogAddUser() {
        buttonAdd.click();
        assertTrue(modal.isVisible(), "Диалоговое окно не расскрылось");
        assertThat(modal).hasClass("fade modal show");
        String displayStyle = (String) modal.evaluate("el => getComputedStyle(el).display");
        assertNotEquals("none", displayStyle, "Диалоговое окно не расскрылось");
        return this;
    }

    @Step("Добавление пользователя в таблицу")
    public WebTablePage addCountUserList() {
        int startCount = getCountUser();

        firstName.fill(generateUser.getFirstName());
        lastName.fill(generateUser.getLastName());
        email.fill(generateUser.getEmail());
        age.fill(generateUser.getAge());
        salary.fill(generateUser.getSalary());
        department.fill(generateUser.getDepartment());
        submit.click();
        allureAttachmentText("User data", generateUser.toString());

        page.waitForCondition(() -> !modal.isVisible());

        assertFalse(modal.isVisible(), "Диалоговое окно не закрылось");

        int resultCount = getCountUser();
        assertTrue(startCount < resultCount, "Список пользователей в таблице не изменился");
        return this;
    }

    @Step("Проверка информации добавленного пользователя")
    public WebTablePage checkNewUser() {
        int numberUser = getCountUser();
        String[] t = listUser.nth(numberUser - 1).innerText().split("\n");
        allureAttachmentText("Actual user data", Arrays.toString(t));
        Assertions.assertAll("Проверка записи пользователя в таблице", () -> {
            assertEquals(t[0], generateUser.getFirstName(), "FirstName пользователя не соответствует firstname в таблице");
            assertEquals(t[1], generateUser.getLastName(), "LastName пользователя не соответствует lastname в таблице");
            assertEquals(t[2], generateUser.getAge(), "Заданное age не соответствует age в таблице");
            assertEquals(t[3], generateUser.getEmail(), "Заданный email не соответствует записи в таблице");
            assertEquals(t[4], generateUser.getSalary(), "Заданный salary не соответствует записи в таблице");
            assertEquals(t[5], generateUser.getDepartment(), "Заданный department не соответствует записи в таблице");
        });
        return this;
    }

    private int getCountUser() {
        int count = 0;
        for (Locator c : listUser.all()) {
            String r = c.textContent();
            if (!r.replace("\u00A0", "").trim().isEmpty()) {
                count = count + 1;
            }
        }
        return count;
    }
}
