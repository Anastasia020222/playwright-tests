package com.playwring.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

public interface IPlaywright {
    Browser create(Playwright playwright);
}
