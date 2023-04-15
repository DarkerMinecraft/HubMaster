package com.darkerminecraft.hubmaster.menu.gui;

import com.darkerminecraft.hubmaster.HubMaster;
import com.darkerminecraft.hubmaster.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class ServerSelection extends Menu {

    private final Inventory inventory;

    public ServerSelection(HubMaster plugin) {
        super(plugin, "Server Selection", 4);
        inventory = plugin.getServer().createInventory(this, getRows() * 9, getInventoryName());

    }

    @Override
    public void showGui(Player p) {
        p.openInventory(inventory);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
