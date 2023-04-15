package com.darkerminecraft.hubmaster.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLib;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.darkerminecraft.hubmaster.HubMaster;
import com.darkerminecraft.hubmaster.npc.NPC;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class PlayerListener extends ServerListener {
    public PlayerListener(HubMaster plugin) {
        super(plugin);

        plugin.protocolManager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();

                int entityID = packet.getIntegers().read(0);
                NPC npc = ((HubMaster) plugin).npcManager.getNPCFromID(entityID);

                if(npc != null) {
                    String command = npc.getCommand();
                    plugin.getServer().getScheduler().runTask(plugin, () -> event.getPlayer().performCommand(command));
                }
            }
        });
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
            npc.createNPC(e.getPlayer());
            npc.spawnNPC(e.getPlayer());
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(!e.getPlayer().hasPermission("hubmaster.build"))
            e.setCancelled(false);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if(!e.getPlayer().hasPermission("hubmaster.build"))
            e.setCancelled(false);
    }
}
