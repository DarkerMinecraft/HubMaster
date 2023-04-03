package com.darkerminecraft.hubmaster.commands;

import com.darkerminecraft.hubmaster.HubMaster;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(usage = "/<command>", description = "Create a NPC")
@CommandPermissions(source = CommandSource.PLAYER)
public class CommandCreateNPC extends ServerCommand {
    protected CommandCreateNPC(String name, HubMaster plugin) {
        super(name, plugin);
    }

    @Override
    protected boolean onCommand(CommandSender sender, Player playerSender, String[] args) {
        ServerPlayer serverPlayer = new ServerPlayer();
        return true;
    }
}
