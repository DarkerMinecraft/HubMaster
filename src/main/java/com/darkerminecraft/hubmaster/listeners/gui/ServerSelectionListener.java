package com.darkerminecraft.hubmaster.listeners.gui;

import com.darkerminecraft.hubmaster.HubMaster;
import com.darkerminecraft.hubmaster.listeners.ServerListener;
import com.darkerminecraft.hubmaster.menu.gui.ServerSelection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class ServerSelectionListener extends ServerListener {
    public ServerSelectionListener(HubMaster plugin) {
        super(plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(!(e.getInventory().getHolder() instanceof ServerSelection)) return;
    }

    @EventHandler
    public void onInventoryClick(InventoryDragEvent e) {
        if(!(e.getInventory().getHolder() instanceof ServerSelection))
            e.setCancelled(true);
    }

}
