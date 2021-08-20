package com.ferick.helpers;

import com.ferick.environment.TestContext;

public class HelperManager {

    private final TestContext context;
    private BaseMethods baseMethods;

    public HelperManager(TestContext context) {
        this.context = context;
    }

    public BaseMethods baseMethods() {
        return (baseMethods == null) ? baseMethods = new BaseMethods(context) : baseMethods;
    }
}
