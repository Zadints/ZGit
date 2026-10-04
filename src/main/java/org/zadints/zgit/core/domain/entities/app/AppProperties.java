package org.zadints.zgit.core.domain.entities.app;

import java.util.List;

public class AppProperties {
    private final String name;
    private final String version;
    private List<String> plugins;
    private final String organizationName;
    private AppCustomized customized;

    public AppProperties(String name, String version, String organizationName) {
        this.name = name;
        this.version = version;
        this.organizationName = organizationName;
    }

    public void addPlugins(String plugin) {
        this.plugins.add(plugin);
    }

    public String getName() {
        return name;
    }

    public List<String> getPlugins() {
        return plugins;
    }

    public String getVersion() {
        return version;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public AppCustomized getCustomized() {
        return customized;
    }
}
