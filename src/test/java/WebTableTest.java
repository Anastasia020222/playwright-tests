import com.microsoft.playwright.Page;
import com.playwring.pages.WebTablePage;
import com.playwring.playwright.Pages;
import com.playwring.playwright.PlaywrightManager;
import com.playwring.user.CreateUser;
import com.playwring.user.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.playwring.utils.common.Path.WEB_TABLES;

@ExtendWith(PlaywrightManager.class)
public class WebTableTest {

    @Pages
    private Page page;

    @CreateUser
    private User user;

    @Test
    @Tags(value = {@Tag("regression"), @Tag("web")})
    @DisplayName("Добавление пользователя в таблицу")
    void addUserTableTest() {
        new WebTablePage(page)
                .open(WEB_TABLES.getPath())
                .openDialogAddUser()
                .addCountUserList(user)
                .checkNewUser(user);
    }

    @Test
    @Tags(value = {@Tag("regression"), @Tag("web")})
    @DisplayName("Редактирование первого пользователя в таблице")
    void editUserTableTest() {
        new WebTablePage(page)
                .open(WEB_TABLES.getPath())
                .clickEditDialogUser()
                .editFieldsAge(user);
    }
}
