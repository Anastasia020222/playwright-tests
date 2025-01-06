import com.microsoft.playwright.APIRequestContext;
import com.playwring.playwright.PlaywrightManager;
import com.playwring.user.CreateUser;
import com.playwring.user.User;
import com.playwring.utils.api.services.ApiContext;
import com.playwring.utils.api.steps.ApiBooks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(PlaywrightManager.class)
public class ApiBookTest {

    @ApiContext
    private APIRequestContext apiRequestContext;

    @CreateUser
    private User user;

    @Test
    @Tags(value = {@Tag("regression"), @Tag("api")})
    @DisplayName("Получение списка книг из таблицы")
    void sortCountVisibleBooksTest() {
        new ApiBooks(apiRequestContext)
                .checkValidateFields();
    }

    @Test
    @Tags(value = {@Tag("regression"), @Tag("api")})
    @DisplayName("Добавление книги пользователю")
    void addListOfBooksUser() {
        new ApiBooks(apiRequestContext)
                .addBooks(user)
                .checkAddingBook(user);
    }
}
