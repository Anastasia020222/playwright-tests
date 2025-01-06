package com.playwring.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
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

    private final Locator buttonAdd = page.locator("id=addNewRecordButton");
    private final Locator modal = page.locator("div[role='dialog']");
    private final Locator firstName = page.locator("id=firstName");
    private final Locator lastName = page.locator("id=lastName");
    private final Locator email = page.locator("id=userEmail");
    private final Locator age = page.locator("id=age");
    private final Locator salary = page.locator("id=salary");
    private final Locator department = page.locator("id=department");
    private final Locator submit = page.locator("id=submit");
    private final Locator listUser = page.locator("div[role='rowgroup']");
    private final Locator iconEdit = page.locator("id=edit-record-1");

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
    public WebTablePage addCountUserList(User user) {
        int startCount = getCountUser();

        firstName.fill(user.getFirstName());
        lastName.fill(user.getLastName());
        email.fill(user.getEmail());
        age.fill(user.getAge());
        salary.fill(user.getSalary());
        department.fill(user.getDepartment());
        submit.click();
        allureAttachmentText("User data", user.toString());

        page.waitForCondition(() -> !modal.isVisible());

        assertFalse(modal.isVisible(), "Диалоговое окно не закрылось");

        int resultCount = getCountUser();
        assertTrue(startCount < resultCount, "Список пользователей в таблице не изменился");
        return this;
    }

    @Step("Проверка информации добавленного пользователя")
    public WebTablePage checkNewUser(User user) {
        int numberUser = getCountUser();
        String[] t = listUser.nth(numberUser - 1).innerText().split("\n");
        allureAttachmentText("Actual user data", Arrays.toString(t));
        Assertions.assertAll("Проверка записи пользователя в таблице", () -> {
            assertEquals(t[0], user.getFirstName(), "FirstName пользователя не соответствует firstname в таблице");
            assertEquals(t[1], user.getLastName(), "LastName пользователя не соответствует lastname в таблице");
            assertEquals(t[2], user.getAge(), "Заданное age не соответствует age в таблице");
            assertEquals(t[3], user.getEmail(), "Заданный email не соответствует записи в таблице");
            assertEquals(t[4], user.getSalary(), "Заданный salary не соответствует записи в таблице");
            assertEquals(t[5], user.getDepartment(), "Заданный department не соответствует записи в таблице");
        });
        return this;
    }

    @Step("Открытие диалогового окна редактирования")
    public WebTablePage clickEditDialogUser() {
        iconEdit.click();
        assertTrue(modal.isVisible(), "Диалоговое окно не расскрылось");
        assertThat(modal).hasClass("fade modal show");
        String displayStyle = (String) modal.evaluate("el => getComputedStyle(el).display");
        assertNotEquals("none", displayStyle, "Диалоговое окно не расскрылось");
        return this;
    }

    @Step("Введение новых данных")
    public WebTablePage editFieldsAge(User user) {
        firstName.fill(user.getFirstName());
        lastName.fill(user.getLastName());
        email.fill(user.getEmail());
        age.fill(user.getAge());
        salary.fill(user.getSalary());
        department.fill(user.getDepartment());
        submit.click();

        allureAttachmentText("New user data", user.toString());

        page.waitForCondition(() -> !modal.isVisible());

        assertFalse(modal.isVisible(), "Диалоговое окно не закрылось");

        checkUpdateDataUser(user);
        return this;
    }

    @Step("Проверяем, что у первого пользователя в таблице изменились данные")
    public WebTablePage checkUpdateDataUser(User generateNewUser) {
        String[] getInfoUser = listUser.first().innerText().split("\n");
        Assertions.assertAll("Проверка изменения записей у пользователя в таблице", () -> {
            assertEquals(getInfoUser[0], generateNewUser.getFirstName(), "FirstName пользователя не соответствует firstname в таблице");
            assertEquals(getInfoUser[1], generateNewUser.getLastName(), "LastName пользователя не соответствует lastname в таблице");
            assertEquals(getInfoUser[2], generateNewUser.getAge(), "Заданное age не соответствует age в таблице");
            assertEquals(getInfoUser[3], generateNewUser.getEmail(), "Заданный email не соответствует записи в таблице");
            assertEquals(getInfoUser[4], generateNewUser.getSalary(), "Заданный salary не соответствует записи в таблице");
            assertEquals(getInfoUser[5], generateNewUser.getDepartment(), "Заданный department не соответствует записи в таблице");
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
