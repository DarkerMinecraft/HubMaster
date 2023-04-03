package com.darkerminecraft.hubmaster.commands;

import com.darkerminecraft.hubmaster.HubMaster;
import com.darkerminecraft.hubmaster.services.Service;
import com.google.common.reflect.ClassPath;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class CommandManager extends Service {
    public CommandManager(HubMaster plugin) {
        super(plugin, "Command Manager");
    }

    @Override
    public void onStart() {
        try {
            ClassPath classpath = ClassPath.from(Thread.currentThread().getContextClassLoader());

            for(ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses("com.darkerminecraft.hubmaster.commands")) {
                Class<?> clazz = classInfo.load();

                if(ServerCommand.class.isAssignableFrom(clazz)) {
                    Constructor<?> constructor = clazz.getConstructor(String.class, HubMaster.class);

                    ServerCommand cmd = (ServerCommand) constructor.newInstance(clazz.getSimpleName().replace("Command", "").toLowerCase(), plugin);
                    loadCommand(cmd);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCommand(ServerCommand cmd) {
        CommandParameters params = cmd.getClass().getAnnotation(CommandParameters.class);
        if(params == null) {
            plugin.getLogger().severe("Could not load command " + cmd.getName() + ". No parameters!");
            return;
        }
        CommandPermissions perms = cmd.getClass().getAnnotation(CommandPermissions.class);
        if(perms == null) {
            plugin.getLogger().severe("Could not load command " + cmd.getName() + ". No permissions!");
            return;
        }

        cmd.setup(params, perms);

        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(cmd.getName(), cmd);
        } catch (IllegalAccessException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onStop() {

    }
}
