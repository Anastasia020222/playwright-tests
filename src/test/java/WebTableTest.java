import com.microsoft.playwright.Page;
import com.playwring.pages.WebTablePage;
import com.playwring.playwright.Pages;
import com.playwring.playwright.PlaywrightManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.playwring.utils.common.Path.WEB_TABLES;

@ExtendWith(PlaywrightManager.class)
public class WebTableTest {

    @Pages
    private Page page;

    @Test
    @Tags(value = {@Tag("regression"), @Tag("web")})
    @DisplayName("Добавление пользователя в таблицу")
    void addUserTableTest() {
        new WebTablePage(page)
                .open(WEB_TABLES.getPath())
                .openDialogAddUser()
                .addCountUserList()
                .checkNewUser();
    }

    @Test
    @Tags(value = {@Tag("regression"), @Tag("web")})
    @DisplayName("Редактирование первого пользователя в таблице")
    void editUserTableTest() {
        new WebTablePage(page)
                .open(WEB_TABLES.getPath())
                .clickEditDialogUser()
                .editFieldsAge();
    }
}
