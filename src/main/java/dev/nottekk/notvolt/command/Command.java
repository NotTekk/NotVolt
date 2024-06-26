package dev.nottekk.notvolt.command;

import dev.nottekk.notvolt.utils.EAccessLevel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.HashMap;
import java.util.List;

public interface Command {

    public void execute(MessageReceivedEvent event, List<String> context);

    public void execute(SlashCommandInteractionEvent event, List<OptionMapping> options);

    public String getDescription();

    public String getName();

    public HashMap<OptionType, HashMap<String, String>> getOptionType();

    public EAccessLevel getCommandLevel();

}
