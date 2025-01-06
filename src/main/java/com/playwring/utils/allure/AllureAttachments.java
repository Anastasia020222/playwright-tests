package com.playwring.utils.allure;

import io.qameta.allure.Attachment;

public class AllureAttachments {

    @Attachment(value = "{name}", type = "text/plain", fileExtension = ".txt")
    public static String allureAttachmentText(String name, String text) {
        return text;
    }

    @Attachment(value = "{name}", type = "application/json", fileExtension = ".json")
    public static String allureAttachmentJson(String name, String jsonContent) {
        return jsonContent;
    }
}
