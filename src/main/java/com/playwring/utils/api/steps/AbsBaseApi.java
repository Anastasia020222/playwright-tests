package com.playwring.utils.api.steps;

import com.microsoft.playwright.APIRequestContext;

public abstract class AbsBaseApi {

    protected APIRequestContext apiRequestContext;

    public AbsBaseApi(APIRequestContext apiRequestContext) {
        this.apiRequestContext = apiRequestContext;
    }
}
