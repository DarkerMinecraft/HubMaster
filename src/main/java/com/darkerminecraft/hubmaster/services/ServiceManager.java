package com.darkerminecraft.hubmaster.services;

import com.darkerminecraft.hubmaster.HubMaster;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class ServiceManager {

    private final HubMaster plugin;
    private final List<IService> services;

    public ServiceManager(HubMaster plugin) {
        this.plugin = plugin;
        services = new ArrayList<>();
    }

    public <T extends Service> T addService(Class<T> clazz)  {
        try {
            Constructor<T> constructor = clazz.getConstructor(HubMaster.class);

            T service = constructor.newInstance(plugin);
            services.add(service);
            return service;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void start() {
        services.forEach(IService::onStart);
    }

    public void stop() {
        services.forEach(IService::onStop);
        services.clear();
    }

}
