package com.ferick.environment;

import com.ferick.config.ConfigLoader;
import com.ferick.config.ConfigUtils;
import com.ferick.config.PropertyLoader;
import org.apache.commons.configuration2.CombinedConfiguration;

import java.util.List;
import java.util.stream.Collectors;

public class TestContext {

    /**
     * Loader for system, common and custom test configurations loaded from scripts
     */
    private final ThreadLocal<ConfigLoader> configLoader = ThreadLocal.withInitial(ConfigLoader::new);

    /**
     * Loader of properties from configurations
     */
    private final ThreadLocal<PropertyLoader> propertyLoader =
            ThreadLocal.withInitial(() -> new PropertyLoader(getConfiguration()));

    public CombinedConfiguration getConfiguration() {
        return getConfigLoader().getConfiguration();
    }

    public void addConfiguration(String configFileName) {
        getConfigLoader().addConfiguration(configFileName);
    }

    public void addConfigurations(List<String> configFileNames) {
        getConfigLoader().addConfigurations(configFileNames);
    }

    public void removeConfiguration(String configFileName) {
        getConfigLoader().removeConfiguration(configFileName);
    }

    public void removeConfigurations(List<String> configFileNames) {
        getConfigLoader().removeConfigurations(configFileNames);
    }

    public void clearAdditionalConfigurations() {
        getConfigLoader().clearAdditionalConfigurations();
    }

    public List<String> getAdditionalConfigurationNames() {
        return getConfigLoader().getAdditionalConfigurationNames();
    }

    public List<String> getRootConfigNames() {
        List<String> configNames = getAdditionalConfigurationNames();
        return configNames.stream().map(ConfigUtils::parseConfigFileName).collect(Collectors.toList());
    }

    public PropertyLoader properties() {
        return propertyLoader.get();
    }

    private ConfigLoader getConfigLoader() {
        return configLoader.get();
    }
}
