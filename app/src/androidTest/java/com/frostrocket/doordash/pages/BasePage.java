package com.frostrocket.doordash.pages;

public abstract class BasePage {

    protected BasePage() {
        verifyAtPage();
    }

    protected abstract void verifyAtPage();
}
