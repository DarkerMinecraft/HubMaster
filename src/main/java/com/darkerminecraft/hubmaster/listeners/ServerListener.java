package com.darkerminecraft.hubmaster.listeners;

import com.darkerminecraft.hubmaster.HubMaster;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Listener;

@RequiredArgsConstructor
public abstract class ServerListener implements Listener {

    public final HubMaster plugin;

}
