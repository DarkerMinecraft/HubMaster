package com.darkerminecraft.hubmaster.listeners;

import com.darkerminecraft.hubmaster.HubMaster;
import com.darkerminecraft.hubmaster.npc.NPC;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener extends ServerListener {
    public PlayerListener(HubMaster plugin) {
        super(plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        plugin.npcManager.getActiveNPCs().forEach(npc -> {
            CraftPlayer craftPlayer = (CraftPlayer) e.getPlayer();
            ServerPlayer sp = craftPlayer.getHandle();

            ServerGamePacketListenerImpl ps = sp.connection;

            Location location = npc.getNpc().getBukkitEntity().getLocation();
            location.setDirection(e.getPlayer().getLocation().subtract(location).toVector());

            float yaw = location.getYaw();
            float pitch  = location.getPitch();

            ps.send(new ClientboundRotateHeadPacket(npc.getNpc(), (byte) ((yaw % 360) * 256 / 360)));
            ps.send(new ClientboundMoveEntityPacket.Rot(npc.getNpc().getBukkitEntity().getEntityId(),
                    (byte) ((yaw % 360) * 256 / 360), (byte) ((pitch % 360) * 256 / 360), false));
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        for(NPC npc : plugin.npcManager.getActiveNPCs()) {
            npc.spawnNPC(e.getPlayer());
        }
    }
}
