import com.playwring.pages.WebTablePage;
import com.playwring.playwright.PlaywrightManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.playwring.utils.common.Path.WEB_TABLES;

public class WebTableTest extends PlaywrightManager {

    @Test
    @DisplayName("Добавление пользователя в таблицу")
    void addUser() {
        new WebTablePage(page)
                .open(WEB_TABLES.getPath())
                .openDialogAddUser()
                .addCountUserList()
                .checkNewUser();
    }
}
