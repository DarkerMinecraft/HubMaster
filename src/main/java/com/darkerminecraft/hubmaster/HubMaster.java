package com.darkerminecraft.hubmaster;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.darkerminecraft.hubmaster.commands.CommandManager;
import com.darkerminecraft.hubmaster.listeners.ListenerManager;
import com.darkerminecraft.hubmaster.npc.NPCManager;
import com.darkerminecraft.hubmaster.services.ServiceManager;
import com.darkerminecraft.hubmaster.utils.NPCStorageUtil;
import com.darkerminecraft.hubmaster.votifier.VotifierManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class HubMaster extends JavaPlugin {

    private ServiceManager serviceManager;

    public VotifierManager votifierManager;
    public NPCManager npcManager;

    public ProtocolManager protocolManager;

    public void onLoad() {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    public void onEnable() {
        if(getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().log(Level.SEVERE, "Could not find PlaceholderAPI! This plugin is required.");
            getServer().getPluginManager().disablePlugin(this);
        }

        NPCStorageUtil.setPlugin(this);

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
