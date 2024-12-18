package com.playwring.components;

import com.microsoft.playwright.Page;
import com.playwring.pages.AbsBasePage;

public abstract class AbsComponents extends AbsBasePage<AbsComponents> {
    public AbsComponents(Page page) {
        super(page);
    }
}
