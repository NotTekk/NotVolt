package dev.nottekk.notvolt.managers;

import dev.nottekk.notvolt.command.Command;
import dev.nottekk.notvolt.command.commands.fun.GifCommand;
import dev.nottekk.notvolt.command.commands.fun.YoMamaCommand;
import dev.nottekk.notvolt.command.commands.pz.StartPZServerCommand;
import dev.nottekk.notvolt.command.commands.pz.StopPZServerCommand;
import dev.nottekk.notvolt.command.commands.utility.*;
import dev.nottekk.notvolt.command.commands.pz.StartPZInstanceCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class CommandManager {

    private final List<Command> commands = new ArrayList<>();

    public CommandManager() {

        addCommand(new PingCommand());
        addCommand(new HelpCommand());
        addCommand(new GifCommand());
        addCommand(new YoMamaCommand());
        addCommand(new GitHubCommand());
        addCommand(new WhoIsCommand());
        addCommand(new BotInfoCommand());

    }

    private void addCommand(Command command) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(command.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present");
        }

        commands.add(command);
    }

    public List<Command> getCommands() {
        return commands;
    }

    @Nullable
    public Command getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (Command command : this.commands) {
            if (command.getName().equals(searchLower)) {
                return command;
            }
        }

        return null;
    }

    public void handleCommand(MessageReceivedEvent event, String prefix) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(prefix), "")
                .split("\\s+");

        Command command = this.getCommand(split[1].toLowerCase());

        if (command != null) {
            event.getChannel().sendTyping().queue();
            command.execute(event, Arrays.asList(split).subList(1, split.length));
        }
    }

    public void handleCommand(SlashCommandInteractionEvent event) {
        String name = event.getName();
        Optional<Command> commandOptional = Optional.ofNullable(this.getCommand(name));

        commandOptional.ifPresent(command -> {
            List<OptionMapping> options = event.getOptions();
            command.execute(event, options);
        });
    }


}
