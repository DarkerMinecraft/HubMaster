package com.darkerminecraft.hubmaster.commands;

import com.darkerminecraft.hubmaster.HubMaster;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public abstract class ServerCommand extends BukkitCommand {

    public static final String NO_PERMISSION_MESSAGE = ChatColor.RED + "You don't have permission to execute this command!";

    public final HubMaster plugin;

    private boolean permission = true;
    private CommandSource source;

    public ServerCommand(String name, HubMaster plugin) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(permission && !hasPermission(sender)) {
            sender.sendMessage(NO_PERMISSION_MESSAGE);
            return true;
        }

        Player playerSender = null;
        if(sender instanceof Player) {
            playerSender = (Player) sender;
        }

        if(!onCommand(sender, playerSender, args)) {
            sender.sendMessage(ChatColor.RED + getUsage());
        }
        return true;
    }

    public void setup(CommandParameters params, CommandPermissions perms) {
        setUsage(ChatColor.RED + "Usage: " + params.usage().replaceFirst("<command>", getName()));
        setDescription(params.description());

        List<String> aliases = Arrays.asList(params.aliases());
        setAliases(aliases);

        permission = perms.hasPermission();
        setPermission(perms.permission());
        source = perms.source();
    }

    private boolean hasPermission(CommandSender sender) {
        switch (source) {
            case PLAYER -> {
                if (!(sender instanceof Player)) {
                    return false;
                }
                return sender.isOp() || sender.isPermissionSet(getPermission());
            }
            case CONSOLE -> {
                return !(sender instanceof Player);
            }
            case BOTH -> {
                if (!(sender instanceof Player)) {
                    return true;
                }
                return sender.isOp() || sender.isPermissionSet(getPermission());
            }
            default -> {
                return false;
            }
        }
    }

    public abstract boolean onCommand(CommandSender sender, Player playerSender, String[] args);

}
