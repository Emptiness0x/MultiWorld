package com.dev7ex.multiworld.command;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.CommandProperties;
import com.dev7ex.multiworld.MultiWorldPlugin;
import com.dev7ex.multiworld.command.world.*;

import com.google.common.collect.Lists;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;
import java.util.Optional;

/**
 * @author Dev7ex
 * @since 19.05.2021
 */
@CommandProperties(name = "world", permission = "multiworld.command.world")
public final class WorldCommand extends BukkitCommand implements TabCompleter {

    public WorldCommand(final MultiWorldPlugin plugin) {
        super(plugin);

        super.registerSubCommand(new BackCommand(plugin));
        super.registerSubCommand(new CloneCommand(plugin));
        super.registerSubCommand(new CreateCommand(plugin));
        super.registerSubCommand(new DeleteCommand(plugin));
        super.registerSubCommand(new InfoCommand(plugin));
        super.registerSubCommand(new HelpCommand(plugin));
        super.registerSubCommand(new ImportCommand(plugin));
        super.registerSubCommand(new ListCommand(plugin));
        super.registerSubCommand(new LoadCommand(plugin));
        super.registerSubCommand(new OptionsCommand(plugin));
        super.registerSubCommand(new ReloadCommand(plugin));
        super.registerSubCommand(new TeleportCommand(plugin));
        super.registerSubCommand(new UnloadCommand(plugin));
    }

    @Override
    public boolean execute(final CommandSender commandSender, final String[] arguments) {
        if ((arguments.length == 0) || (arguments.length > 4)) {
            return super.getSubCommand("help").orElseThrow().execute(commandSender, arguments);
        }
        final Optional<BukkitCommand> commandOptional = super.getSubCommand(arguments[0].toLowerCase());

        if (commandOptional.isEmpty()) {
            return super.getSubCommand("help").orElseThrow().execute(commandSender, arguments);
        }
        return commandOptional.get().execute(commandOptional.get(), commandSender, arguments);
    }

    @Override
    public List<String> onTabComplete(final CommandSender commandSender, final Command command, final String commandLabel, final String[] arguments) {
        if (arguments.length == 1) {
            return Lists.newArrayList(super.getSubCommands().keySet());
        }
        final Optional<BukkitCommand> commandOptional = super.getSubCommand(arguments[0].toLowerCase());

        if ((commandOptional.isEmpty()) || (!(commandOptional.get() instanceof TabCompleter))) {
            return null;
        }
        return ((TabCompleter) commandOptional.get()).onTabComplete(commandSender, command, commandLabel, arguments);
    }

}
