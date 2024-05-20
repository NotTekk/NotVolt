package dev.nottekk.notvolt.command.commands.utility;

import dev.nottekk.notvolt.builders.CommandEmbedBuilder;
import dev.nottekk.notvolt.command.Command;
import dev.nottekk.notvolt.utils.EAccessLevel;
import dev.nottekk.notvolt.formatters.formats.MessageFormat;
import dev.nottekk.notvolt.handlers.ConfigHandler;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HelpCommand implements Command {

    @Override
    public void execute(MessageReceivedEvent event, List<String> context) {
        event.getChannel().sendMessageEmbeds(buildEmbed()).queue();
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, List<OptionMapping> options) {
        event.replyEmbeds(buildEmbed()).queue();
    }

    private MessageEmbed buildEmbed() {
        MessageFormat message1 = new MessageFormat("Bot:", true, false, false);
        MessageFormat message2 = new MessageFormat("help - Shows the available commands", false, false, false);
        MessageFormat message3 = new MessageFormat("info - Shows current bot status\n", false, false, false);
        MessageFormat message4 = new MessageFormat("Utilities:", true, false, false);
        MessageFormat message5 = new MessageFormat("gituser - Shows github user info (use github profile username)", false, false, false);
        MessageFormat message6 = new MessageFormat("ping - Checks the bot connection", false, false, false);
        MessageFormat message7 = new MessageFormat("whois - Provides information about the mentioned user\n", false, false, false);
        MessageFormat message8 = new MessageFormat("Fun:", true, false, false);
        MessageFormat message9 = new MessageFormat("ym - Makes YO MAMMA jokes with the mentioned user", false, false, false);
        MessageFormat message10 = new MessageFormat("gif - search and displays gifs based on users input\n\n", false, false, false);
        MessageEmbed embed = CommandEmbedBuilder.buildEmbed("\uD83D\uDD37   Help\n", "\n\nCreated by NotTekk", ConfigHandler.get("OWNER_AVATAR"), Color.BLUE, Arrays.asList(message1, message2, message3, message4, message5, message6, message7, message8, message9, message10));

        return embed;
    }

    @Override
    public String getDescription() {
        return "Shows all available commands";
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public HashMap<OptionType, HashMap<String, String>> getOptionType() {
        return null;
    }

    @Override
    public EAccessLevel getCommandLevel() {
        return EAccessLevel.USER;
    }

}
