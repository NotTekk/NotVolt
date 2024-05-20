package dev.nottekk.notvolt.command.commands.administrator;

import dev.nottekk.notvolt.command.Command;
import dev.nottekk.notvolt.utils.EAccessLevel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.HashMap;
import java.util.List;

public class ChangeAvatarCommand implements Command {
    @Override
    public void execute(MessageReceivedEvent event, List<String> context) {

    }

    @Override
    public void execute(SlashCommandInteractionEvent event, List<OptionMapping> options) {

    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public HashMap<OptionType, HashMap<String, String>> getOptionType() {
        return null;
    }

    @Override
    public EAccessLevel getCommandLevel() {
        return EAccessLevel.OWNER;
    }
}
