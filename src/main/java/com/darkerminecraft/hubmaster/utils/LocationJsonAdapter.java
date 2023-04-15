package com.darkerminecraft.hubmaster.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.Type;
import java.util.Objects;

public class LocationJsonAdapter implements JsonDeserializer<Location>, JsonSerializer<Location> {
    @Override
    public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Location location = new Location(Bukkit.getWorld(jsonObject.get("World").getAsString())
        , jsonObject.get("X").getAsDouble(), jsonObject.get("Y").getAsDouble(), jsonObject.get("Z").getAsDouble());
        return location;
    }

    @Override
    public JsonElement serialize(Location src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("World", Objects.requireNonNull(src.getWorld()).getName());
        jsonObject.addProperty("X", src.getX());
        jsonObject.addProperty("Y", src.getY());
        jsonObject.addProperty("Z", src.getZ());
        return jsonObject;
    }
}
