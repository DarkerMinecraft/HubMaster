package com.darkerminecraft.hubmaster.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.EquipmentSlot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@RequiredArgsConstructor
public class NPC {

    private final String name, texture, signature;

    @Getter
    private ServerPlayer npc;

    private Map<EquipmentSlot, Material> itemsOnNPC;

    public void createNPC(Player player, Location location) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ServerPlayer sp = craftPlayer.getHandle();

        MinecraftServer server = sp.getServer();
        ServerLevel serverLevel = sp.getLevel();

        itemsOnNPC = new HashMap<>();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);

        if(!texture.isEmpty() && !signature.isEmpty())
            gameProfile.getProperties().put("textures", new Property("textures", texture, signature));

        npc = new ServerPlayer(server, serverLevel, gameProfile);
        npc.setPos(location.getX(), location.getY(), location.getZ());
    }

    public void createNPC(Player player) {
        createNPC(player, player.getLocation());
    }

    public void spawnNPC(Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ServerPlayer sp = craftPlayer.getHandle();

        ServerGamePacketListenerImpl ps = sp.connection;

        ps.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, npc));
        ps.send(new ClientboundAddPlayerPacket(npc));

        Location location = npc.getBukkitEntity().getLocation();
        location.setDirection(player.getLocation().subtract(location).toVector());

        float yaw = location.getYaw();
        float pitch  = location.getPitch();

        ps.send(new ClientboundRotateHeadPacket(npc, (byte) ((yaw % 360) * 256 / 360)));
        ps.send(new ClientboundMoveEntityPacket.Rot(npc.getBukkitEntity().getEntityId(),
                (byte) ((yaw % 360) * 256 / 360), (byte) ((pitch % 360) * 256 / 360), false));

        List<Pair<EquipmentSlot, net.minecraft.world.item.ItemStack>> equipmentList = new ArrayList<>();

        for(EquipmentSlot slot : itemsOnNPC.keySet()) {
            equipmentList.add(new Pair<>(slot, CraftItemStack.asNMSCopy(new ItemStack(itemsOnNPC.get(slot)))));
        }


        if(!equipmentList.isEmpty())
            ps.send(new ClientboundSetEquipmentPacket(npc.getBukkitEntity().getEntityId(), equipmentList));
    }

    public void destroyNPC(Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ServerPlayer sp = craftPlayer.getHandle();

        ServerGamePacketListenerImpl ps = sp.connection;

        ps.send(new ClientboundPlayerInfoRemovePacket(List.of(npc.getGameProfile().getId())));
    }

    public void addItem(EquipmentSlot slot, Material material) {
        itemsOnNPC.remove(slot);
        itemsOnNPC.put(slot, material);
    }

}
