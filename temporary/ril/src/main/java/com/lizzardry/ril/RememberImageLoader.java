package com.lizzardry.ril;

/**
 * the very entrance of remember Image Loader
 */
public class RememberImageLoader {
    public static RememberImageLoader instance;
    public static synchronized RememberImageLoader getInstance() {
        if (instance == null) {
            instance = new RememberImageLoader();
        }
        return instance;
    }

    private boolean hasConfigured = false;
    private RememberImageLoaderConfiguration configuration;

    public void init(RememberImageLoaderConfiguration config) {
        this.configuration = config;
        hasConfigured = true;
    }

    public RememberImageLoaderConfiguration getConfiguration() {
        if (!hasConfigured) {
            throw new IllegalStateException("RIL not configured");
        }

        return configuration;
    }
}
