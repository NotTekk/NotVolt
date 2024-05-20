package dev.nottekk.notvolt.command.commands.administrator;

import dev.nottekk.notvolt.command.Command;
import dev.nottekk.notvolt.utils.EAccessLevel;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.HashMap;
import java.util.List;

public class ChangeStatusCommand implements Command {
    @Override
    public void execute(MessageReceivedEvent event, List<String> context) {
        if (!context.isEmpty()) {
            changeActivity(event.getJDA(), context.get(0), context.get(1), context.get(2));
        }
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, List<OptionMapping> options) {
        if (!options.isEmpty()) {
            changeActivity(event.getJDA(), options.get(0).getAsString(), options.get(1).getAsString(), options.get(2).getAsString());
        }
    }

    private void changeActivity(JDA jda, String activityType, String activity, String URL) {
        switch (activityType) {
            case "watching":
                jda.getPresence().setActivity(Activity.watching(activity));
                break;
            case "competing":
                jda.getPresence().setActivity(Activity.competing(activity));
                break;
            case "streaming":
                if (!URL.isEmpty()) {
                    jda.getPresence().setActivity(Activity.streaming(activity, URL));
                }
                break;
            case "listening":
                jda.getPresence().setActivity(Activity.listening(activity));
                break;
        }
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getName() {
        return "";
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
