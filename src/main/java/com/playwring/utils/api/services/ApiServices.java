package com.playwring.utils.api.services;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import com.playwring.user.User;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import static com.playwring.utils.allure.AllureAttachments.allureAttachmentText;
import static com.playwring.utils.api.PathRequest.*;

@Slf4j
public class ApiServices {

    private final APIRequestContext apiRequestContext;
    private final String baseUrl = System.getProperty("url");

    public ApiServices(APIRequestContext apiRequestContext) {
        this.apiRequestContext = apiRequestContext;
    }

    public APIResponse getListBook() {
        log.info("Fetching list of books from: {}", baseUrl + pathGetBooks);
        return apiRequestContext.get(baseUrl + pathGetBooks);
    }

    public APIResponse addListOfBooks(User user) {
        log.info("Adding books for user: {}", user);
        Map<String, Object> requestBody = Map.of(
                "userId", user.getUserId(),
                "collectionOfIsbns", List.of(Map.of("isbn", "9781449331818"))
        );
        log.debug("Request body: {}", requestBody);

        allureAttachmentText("Data", String.format("UserId: %s; ISBN: 9781449331818", user.getUserId()));
        APIResponse response = apiRequestContext.post(
                baseUrl + addListBooks,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(requestBody)
        );
        validateResponse(response, "Failed to add books to user's collection");
        return response;
    }

    public APIResponse getUser(User user) {
        log.info("Fetching user details for userId: {}", user.getUserId());
        return apiRequestContext.get(baseUrl + pathCreateUser + "/" + user.getUserId());
    }

    public APIResponse getBook(String isbn) {
        log.info("Fetching book details for ISBN: {}", isbn);
        return apiRequestContext.get(baseUrl + pathGetBook + "?ISBN=" + isbn);
    }

    private void validateResponse(APIResponse response, String errorMessage) {
        if (!response.ok()) {
            log.error("{}: Status {} - {}", errorMessage, response.status(), response.statusText());
            throw new IllegalStateException(errorMessage);
        }
    }
}
