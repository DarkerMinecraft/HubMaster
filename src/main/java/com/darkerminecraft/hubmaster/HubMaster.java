package com.darkerminecraft.hubmaster;

import com.darkerminecraft.hubmaster.commands.CommandManager;
import com.darkerminecraft.hubmaster.listeners.ListenerManager;
import com.darkerminecraft.hubmaster.npc.NPCManager;
import com.darkerminecraft.hubmaster.services.ServiceManager;
import com.darkerminecraft.hubmaster.votifier.VotifierManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class HubMaster extends JavaPlugin {

    private ServiceManager serviceManager;

    public VotifierManager votifierManager;
    public NPCManager npcManager;

    @Override
    public void onEnable() {
        serviceManager = new ServiceManager(this);

        votifierManager = serviceManager.addService(VotifierManager.class);
        npcManager = serviceManager.addService(NPCManager.class);

        serviceManager.addService(CommandManager.class);
        serviceManager.addService(ListenerManager.class);

        serviceManager.start();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        serviceManager.stop();
        super.onDisable();
    }
}
