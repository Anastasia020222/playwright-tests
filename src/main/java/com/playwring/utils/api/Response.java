package com.playwring.utils.api;

import com.microsoft.playwright.APIResponse;
import com.playwring.utils.api.dto.Books;
import com.playwring.utils.api.dto.BooksResponse;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;

public class Response {

    private static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    public static BooksResponse getResponse(APIResponse apiResponse) {
        try {
            return objectMapper.readValue(apiResponse.text(), BooksResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Не удалось распарсить api response. " + e);
        }
    }

    public static Books getResponseBooks(APIResponse apiResponse) {
        try {
            return objectMapper.readValue(apiResponse.text(), Books.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Не удалось распарсить api response. " + e);
        }
    }
}
