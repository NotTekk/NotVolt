package dev.nottekk.notvolt.listeners;

import dev.nottekk.notvolt.command.Command;
import dev.nottekk.notvolt.managers.CommandManager;
import dev.nottekk.notvolt.managers.LoggerManager;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ReadyStateListener extends ListenerAdapter {

    private final CommandManager commandManager;

    public ReadyStateListener(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    private static final Logger LOGGER = LoggerManager.getLogger(ReadyStateListener.class);

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        LOGGER.info("Registering Global Slash commands");
        List<CommandData> commandData = new ArrayList<>();
        for (Command command : commandManager.getCommands()) {
            if (command.getOptionType() != null && !command.getOptionType().isEmpty()) {
                List<OptionType> optionTypes = new ArrayList<>(command.getOptionType().keySet());
                List<String> optionStrings = new ArrayList<>(new ArrayList<>(command.getOptionType().values()).get(0).keySet());
                List<String> optionDescs = new ArrayList<>(new ArrayList<>(command.getOptionType().values()).get(0).values());
                commandData.add(Commands.slash(command.getName(), command.getDescription()).addOption(optionTypes.get(0), optionStrings.get(0), optionDescs.get(0)));
            } else {
                commandData.add(Commands.slash(command.getName(), command.getDescription()));
            }
        }
        event.getJDA().updateCommands().addCommands(commandData).queue();

        LOGGER.info("voidBot - [READY]");

    }

}
