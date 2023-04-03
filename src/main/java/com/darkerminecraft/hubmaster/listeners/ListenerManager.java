package com.darkerminecraft.hubmaster.listeners;

import com.darkerminecraft.hubmaster.HubMaster;
import com.darkerminecraft.hubmaster.services.Service;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import org.bukkit.event.Listener;

import java.lang.reflect.Constructor;

public class ListenerManager extends Service {

    public ListenerManager(HubMaster plugin) {
        super(plugin, "Listener Manager");
    }

    @Override
    public void onStart() {
        try(ScanResult scanResult =
                    new ClassGraph()
                            .acceptPackages("com.darkerminecraft.hubmaster.listeners")
                            .enableAllInfo()
                            .scan()) {
            for(ClassInfo classInfo : scanResult.getSubclasses(ServerListener.class)) {
                Class<? extends ServerListener> clazz = classInfo.loadClass(ServerListener.class);

                Constructor<? extends ServerListener> constructor = clazz.getConstructor(HubMaster.class);

                ServerListener listener = (ServerListener) constructor.newInstance(plugin);

                plugin.getServer().getPluginManager().registerEvents(listener, plugin);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {

    }
}
