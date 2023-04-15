package com.darkerminecraft.hubmaster.utils;

import com.darkerminecraft.hubmaster.HubMaster;
import com.darkerminecraft.hubmaster.npc.NPC;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Setter;
import org.bukkit.Location;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class NPCStorageUtil {

    private static List<NPC> npcs = new ArrayList<>();

    @Setter
    private static HubMaster plugin;

    public static void createNPC(NPC npc) {
        npcs.add(npc);
    }

    public static NPC findNPC(int id) {
        for(NPC npc : npcs) {
            if(npc.getNpc().getId() == id) {
                return npc;
            }
        }
        return null;
    }

    public static NPC updateNPC(int id, NPC newNPC) {
        for(NPC npc : npcs) {
            if(npc.getNpc().getId() == id) {
                npc.setName(newNPC.getName());
                npc.setTextures(newNPC.getTextures());
                npc.setCommand(newNPC.getCommand());
                return npc;
            }
        }
        return null;
    }

    public static void deleteNPC(int id) {
        for(NPC npc : npcs) {
            if(npc.getNpc().getId() == id) {
                npcs.remove(npc);
                break;
            }
        }
    }

    public static void saveNPCs() {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(Location.class, new LocationJsonAdapter())
                .registerTypeAdapter(NPC.class, new NPCJsonAdapter())
                .create();
        File file = new File(plugin.getDataFolder().getAbsolutePath() + "/npcs.json");

        file.getParentFile().mkdir();

        try {
            file.createNewFile();
            Writer writer = new FileWriter(file, false);

            gson.toJson(npcs, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<NPC> loadNPCs() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(NPC.class, new NPCJsonAdapter())
                .registerTypeAdapter(Location.class, new LocationJsonAdapter()).create();

        File file = new File(plugin.getDataFolder().getAbsolutePath() + "/npcs.json");
        if(file.exists()) {
            Reader reader = null;
            try {
                reader = new FileReader(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert reader != null;

            List<NPC> npcsJson = gson.fromJson(reader, new TypeToken<List<NPC>>() {
            }.getType());

            if(npcsJson != null)
                npcs = npcsJson;

            return npcsJson;
        } else {
            npcs = new ArrayList<>();
            return null;
        }
    }

}
