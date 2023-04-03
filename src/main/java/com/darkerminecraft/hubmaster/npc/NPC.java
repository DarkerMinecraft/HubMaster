package com.darkerminecraft.hubmaster.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
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

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class NPC {

    private final String name, texture, signature;

    @Getter
    private ServerPlayer npc;

    public void createNPC(Player player, Location location) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ServerPlayer sp = craftPlayer.getHandle();

        MinecraftServer server = sp.getServer();
        ServerLevel serverLevel = sp.getLevel();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);

        if(!texture.isEmpty() && !signature.isEmpty())
            gameProfile.getProperties().put("textures", new Property("textures", texture, signature));

        npc = new ServerPlayer(server, serverLevel, gameProfile);
        npc.setPos(location.getX(), location.getY(), location.getZ());
    }

    public void spawnNPC(Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ServerPlayer sp = craftPlayer.getHandle();

        ServerGamePacketListenerImpl ps = sp.connection;

        ps.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, npc));
        ps.send(new ClientboundAddPlayerPacket(npc));
        ps.send(new ClientboundSetEquipmentPacket(npc.getBukkitEntity().getEntityId(),
                List.of(new Pair<>(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(new ItemStack(Material.DIAMOND_AXE))))));
    }

}
