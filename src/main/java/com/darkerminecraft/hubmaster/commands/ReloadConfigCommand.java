package com.darkerminecraft.hubmaster.commands;

import com.darkerminecraft.hubmaster.HubMaster;
import com.darkerminecraft.hubmaster.utils.NPCStorageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@CommandPermissions(source = CommandSource.BOTH, permission = "hubmaster.reloadconfig")
@CommandParameters(description = "Reload Configuration File", usage = "/<command> [config]", aliases = {"rc"})
public class ReloadConfigCommand extends ServerCommand {
    public ReloadConfigCommand(String name, HubMaster plugin) {
        super(name, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Player playerSender, String[] args) {
        if(args.length < 1) sender.sendMessage(ChatColor.RED + "Incorrect Arguments! /reloadconfig [config]");
        else {
            if (args[0].equals("npcs")) {
                NPCStorageUtil.loadNPCs();
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of("npcs");
    }
}
