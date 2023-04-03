package com.darkerminecraft.hubmaster.commands;

import com.darkerminecraft.hubmaster.HubMaster;
import com.darkerminecraft.hubmaster.npc.NPC;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

@CommandParameters(usage = "/<command>", description = "Create a NPC")
@CommandPermissions(source = CommandSource.PLAYER)
public class CommandCreateNPC extends ServerCommand {
    public CommandCreateNPC(String name, HubMaster plugin) {
        super(name, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Player playerSender, String[] args) {
        NPC npc = new NPC("Billy Bob", "", "");
        plugin.npcManager.addActiveNPC(npc);

        npc.createNPC(playerSender,  playerSender.getLocation());
        npc.spawnNPC(playerSender);

        return true;
    }
}
