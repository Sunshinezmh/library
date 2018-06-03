package com.dmsdbj.library.app.config;

import org.apache.deltaspike.core.api.config.PropertyFileConfig;

public class ConfigResource implements PropertyFileConfig {

    @Override
    public String getPropertyFileName() {
        return "config/application.properties";
    }

    @Override
    public boolean isOptional() {
        return false;
    }

}
