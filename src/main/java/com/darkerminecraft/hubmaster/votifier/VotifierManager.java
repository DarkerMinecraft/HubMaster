package com.darkerminecraft.hubmaster.votifier;

import com.darkerminecraft.hubmaster.HubMaster;
import com.darkerminecraft.hubmaster.services.Service;

public class VotifierManager extends Service {
    public VotifierManager(HubMaster plugin) {
        super(plugin, "Votifier Manager");
    }

    @Override
    public void onStart() {
        if(plugin.getServer().getPluginManager().getPlugin("Votifier") != null)
            plugin.getServer().getPluginManager().registerEvents(new VotifierListener(), plugin);
    }

    @Override
    public void onStop() {

    }
}
