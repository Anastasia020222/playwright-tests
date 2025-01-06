package com.playwring.utils.api.steps;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.playwring.user.User;
import com.playwring.utils.api.services.ApiServices;
import com.playwring.utils.api.dto.Books;
import com.playwring.utils.api.dto.BooksResponse;
import io.qameta.allure.Step;

import java.util.List;

import static com.playwring.utils.allure.AllureAttachments.allureAttachmentJson;
import static com.playwring.utils.allure.AllureAttachments.allureAttachmentText;
import static com.playwring.utils.api.Data.data;
import static com.playwring.utils.api.Response.getResponse;
import static com.playwring.utils.api.Response.getResponseBooks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiBooks extends AbsBaseApi {

    private final ApiServices apiServices;

    public ApiBooks(APIRequestContext apiRequestContext) {
        super(apiRequestContext);
        this.apiServices = new ApiServices(apiRequestContext);
    }

    private final String isbnBook = "9781449331818";

    @Step("Проверяем список полученных книг")
    public ApiBooks checkValidateFields() {
        APIResponse apiResponse = apiServices.getListBook();
        BooksResponse booksResponse = getResponse(apiResponse);
        allureAttachmentJson("json полученных книг", apiResponse.text());
        assertTrue(apiResponse.ok());

        List<Books> books = booksResponse.getBooks();

        int i = 0;
        for (; i < books.size(); i++) {
            assertEquals(data().get(i), books.get(i).getTitle(), "Тайтл у книги " + books.get(i).getIsbn() + " не совпал");
        }

        assertEquals(8, books.size(), "Количество полученных книг не равно 8");
        return this;
    }

    @Step("Добавление книги")
    public ApiBooks addBooks(User user) {
        APIResponse apiResponse = apiServices.addListOfBooks(user);
        assertTrue(apiResponse.ok(), "Не удалось получить список книг. " + apiResponse.text() + apiResponse.status());

        JsonObject rootObject = JsonParser.parseString(apiResponse.text()).getAsJsonObject();
        JsonArray books = rootObject.getAsJsonArray("books");
        String isbn = books.get(0).getAsJsonObject().get("isbn").getAsString();

        assertEquals(isbnBook, isbn, "isb полученной книги у пользователя не соответсвует добавленной");
        return this;
    }

    @Step("Проверка, что пользователю добавлена книга")
    public ApiBooks checkAddingBook(User user) {
        APIResponse apiResponse = apiServices.getUser(user);
        assertTrue(apiResponse.ok(), "Не удалось выполнить запрос на получение юзера, status " + apiResponse.status());
        BooksResponse booksResponse = getResponse(apiResponse);
        List<Books> books = booksResponse.getBooks();
        allureAttachmentText("Data user:", apiResponse.text());

        APIResponse apiResponseBook = apiServices.getBook(isbnBook);
        assertTrue(apiResponse.ok(), "Не удалось выполнить запрос на получение книги, status " + apiResponse.status());
        Books booksResponse1 = getResponseBooks(apiResponseBook);
        allureAttachmentText("Data book isbn=" + isbnBook + ":", apiResponseBook.text());

        String getIsbn = booksResponse1.getIsbn();
        String getTitle = booksResponse1.getTitle();

        for (Books b : books) {
            assertEquals(getIsbn, b.getIsbn(), "isbn у полученной книги не совпадает, isbn книги у юзера: " + b.getIsbn());
            assertEquals(getTitle, b.getTitle(), "Title у полученной книги не соответсвует: " + getTitle);
        }
        return this;
    }
}
