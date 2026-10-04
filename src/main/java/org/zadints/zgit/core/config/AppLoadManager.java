package org.zadints.zgit.core.config;

public class AppLoadManager {

    public static String getName() {
        return AppLoadManager.class
                .getPackage()
                .getImplementationTitle();
    }

    public static String getVersion() {
        return AppLoadManager.class
                .getPackage()
                .getImplementationVersion();
    }

}
