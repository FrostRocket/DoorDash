package com.frostrocket.doordash.assertions;

import com.frostrocket.doordash.pages.BasePage;

public class PageAssertion {

    public static void assertAt(Class pageClass) throws Exception {
        pageClass.asSubclass(BasePage.class).getConstructors()[0].newInstance();
    }
}
