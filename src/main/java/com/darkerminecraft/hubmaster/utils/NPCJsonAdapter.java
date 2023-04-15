package com.darkerminecraft.hubmaster.utils;

import com.darkerminecraft.hubmaster.npc.NPC;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.bukkit.Location;

import java.lang.reflect.Type;

public class NPCJsonAdapter implements JsonDeserializer<NPC>, JsonSerializer<NPC> {

    private final Gson gson = new GsonBuilder().registerTypeAdapter(Location.class, new LocationJsonAdapter()).create();

    @Override
    public JsonElement serialize(NPC npc, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("Name", npc.getName());
        jsonObject.add("Location", gson.toJsonTree(npc.getLocation()));
        jsonObject.add("Textures", gson.toJsonTree(npc.getTextures()));
        jsonObject.addProperty("Command", npc.getCommand());

        return jsonObject;
    }

    @Override
    public NPC deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String name = jsonObject.get("Name").getAsString();
        Location location = gson.fromJson(jsonObject.getAsJsonObject("Location"), Location.class);

        JsonObject textures = jsonObject.getAsJsonObject("Textures");
        String textureValue = textures.get("Value").getAsString();
        String textureSignature = textures.get("Signature").getAsString();

        String command = jsonObject.get("Command").getAsString();

        return new NPC(name, location, textureValue, textureSignature, command);
    }
}
