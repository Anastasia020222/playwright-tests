package com.playwring.user;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static com.playwring.utils.allure.AllureAttachments.allureAttachmentJson;
import static com.playwring.utils.allure.AllureAttachments.allureAttachmentText;
import static com.playwring.utils.api.PathRequest.*;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class CreateUserContext {

    private APIRequestContext apiRequestContext;
    private User user;
    private final String baseUrl = System.getProperty("url");

    public CreateUserContext(APIRequestContext apiRequestContext) {
        this.apiRequestContext = apiRequestContext;
    }

    public User generateNewUser() {
        this.user = new User();
        this.user.fillInFields();
        log.info("Generate user: {}", user);
        return user;
    }

    public User createUser() {
        log.info("Creating user: {}", user);
        APIResponse result = apiRequestContext.post(
                baseUrl + pathCreateUser,
                RequestOptions.create().setData(Map.of(
                        "userName", user.getFirstName(),
                        "password", user.getPassword()
                ))
        );
        validateResponse(result, "Failed to create user");

        JsonObject jsonObject = JsonParser.parseString(result.text()).getAsJsonObject();
        user.setUserId(jsonObject.get("userID").getAsString());

        log.info("User created successfully: {}", user);
        allureAttachmentText("Creating user:", user.toString());
        return user;
    }

    private String getToken() {
        log.info("Generating token for user: {}", user);
        APIResponse result = apiRequestContext.post(
                baseUrl + generateToken,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(Map.of(
                                "userName", user.getFirstName(),
                                "password", user.getPassword()
                        ))
        );
        validateResponse(result, "Failed to generate token");

        JsonObject jsonObject = JsonParser.parseString(result.text()).getAsJsonObject();
        if (jsonObject.get("token").isJsonNull()) {
            throw new IllegalStateException("Token is null");
        }

        String token = jsonObject.get("token").getAsString();
        log.info("Token generated successfully: {}", token);
        allureAttachmentText("Ger token:", token);
        return token;
    }

    public Map<String, String> getHeaders() {
        return Map.of("Authorization", "Bearer " + getToken());
    }

    public void deleteUser() {
        if (apiRequestContext != null && user.getUserId() != null) {
            log.info("Deleting user: {}", user);
            APIResponse result = apiRequestContext.delete(baseUrl + deleteUser + user.getUserId());
            validateResponse(result, "Failed to delete user");

            log.info("User deleted successfully: {}", user);
            allureAttachmentJson("Delete user:", "Response delete user: " + result.text() + ", status: " + result.status());
        }
    }

    private void validateResponse(APIResponse response, String errorMessage) {
        if (!response.ok()) {
            log.error("{}: Status {} - {}", errorMessage, response.status(), response.statusText());
            throw new IllegalStateException(errorMessage);
        }
    }
}

