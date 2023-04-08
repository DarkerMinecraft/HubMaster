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
        NPC npc = new NPC("Billy Bob", "ewogICJ0aW1lc3RhbXAiIDogMTY4MDU1OTk2ODc1OCwKICAicHJvZmlsZUlkIiA6ICIzYjgwOTg1YWU4ODY0ZWZlYjA3ODg2MmZkOTRhMTVkOSIsCiAgInByb2ZpbGVOYW1lIiA6ICJLaWVyYW5fVmF4aWxpYW4iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQyZGQ4OWYzYmY5Y2QxYjZiMWVhN2MzZWUzNjRlNWRhMDE5NzFkOWZmY2RjZmE1Yjk3ZjNkZWZiYjQ2ZDk3YSIKICAgIH0KICB9Cn0=",
                "EbV0eMX8gUJeEs8xQ+w7MNC//a+yy1Gut23veZOf/+zRL3uz/JNC10u6Tx81VovCkqiU16BKXNtSVeboy2VnbXEYtRDfHni2XWZGtbzQtG0jGJcPH7K59Sehk5YxpJ9XSZkmzJHhrKmZlaZVIbDhblTYIWxD95R21Wt54VjGBPkC912eyJ36juE8Ny3P8nq501bAs124jrAW/EwGdrgyXR82Li6DDmaEucp+cQxlPIRXAExPc5sPUHyYTYFYstrbhOsvLB/tMuDCyYVNxyT0nYScEUA1yxzLpiIkiDof4AESLUsL2M711iCBhZrK+EvmeY59s4uFOfyDD7NDxd/9QEgSQyOTia0uVOM74vnpt+qmQ+Lfq2NCS028QV4ImBMp8scSfQ58q63a5SoJhMRieWW5K55XHssuevFOlVpjsRSswo9m8g+dE3V5CWl0RUFRfF2MiT/HN5mSNNok5peUcVzsWjrozgOffjIHlLPAnDNe4KyW0DNxpRwdkK6H4NJYoLehIURbwgauIGtOhdZRjLNw3V9pLqCGaZeuYj4itDibgFRoB0DGq5uP60qAmkhJndNzjKiMAaQ6/7f/xDy0PhS2aMqB0+sw8XVs1nIwGnO9T2cuevGhZMWetUUxWno/0pv9HOCiSSc/i9dgORaUgNOiLBans0SKDhEIFAOpqg4=");
        plugin.npcManager.addActiveNPC(npc);

        npc.createNPC(playerSender);
        npc.spawnNPC(playerSender);

        return true;
    }
}
