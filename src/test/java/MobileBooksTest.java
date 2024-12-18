import com.microsoft.playwright.Page;
import com.playwring.pages.MobileBooksPage;
import com.playwring.playwright.Pages;
import com.playwring.playwright.PlaywrightManager;
import com.playwring.playwright.mobile.Mobile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.playwring.utils.common.Path.BOOKS;

@ExtendWith(PlaywrightManager.class)
public class MobileBooksTest {

    @Pages
    private Page page;

    @Test
    @Mobile
    @Tags(value = {@Tag("regression"), @Tag("mobile")})
    @DisplayName("Сортировка отображения количества книг в таблице")
    void sortCountVisibleBooksTest() {
        new MobileBooksPage(page)
                .open(BOOKS.getPath())
                .checkSelectRows()
                .checkCountBooks();
    }

    @Test
    @Mobile
    @Tags(value = {@Tag("regression"), @Tag("mobile")})
    @DisplayName("Проверка раскрытия бокового меню")
    void checkVisibleMenu() {
        new MobileBooksPage(page)
                .open(BOOKS.getPath())
                .openMenu()
                .checkCountCategoryAccordion();
    }
}
