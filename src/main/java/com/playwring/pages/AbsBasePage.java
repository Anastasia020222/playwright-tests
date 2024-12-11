package com.playwring.pages;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public abstract class AbsBasePage<T> {

    protected Page page;
    private String url = System.getProperty("url");

    public AbsBasePage(Page page) {
        this.page = page;
        page.setDefaultTimeout(120000);
    }

    @Step("Открытие страницы")
    public T open(String path) {
        page.navigate(url + path);
        return (T) this;
    }
}
