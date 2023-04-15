package com.darkerminecraft.hubmaster.menu;

import com.darkerminecraft.hubmaster.HubMaster;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;

@RequiredArgsConstructor
public abstract class Menu implements InventoryHolder {


    public final HubMaster plugin;

    @Getter
    private final String inventoryName;
    @Getter
    private final int rows;

    @SneakyThrows
    public static <T extends Menu> T createNewGui(HubMaster plugin, Class<T> clazz) {
        Constructor<T> constructor = clazz.getConstructor(HubMaster.class);
        return constructor.newInstance(plugin);
    }

    protected void addItem(Inventory inventory, int index, ItemStack itemStack) {
        inventory.setItem(index - 1, itemStack);
    }

    public abstract void showGui(Player p);

}
