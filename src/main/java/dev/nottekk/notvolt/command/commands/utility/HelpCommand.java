package dev.nottekk.notvolt.command.commands.utility;

import dev.nottekk.notvolt.builders.CommandEmbedBuilder;
import dev.nottekk.notvolt.command.Command;
import dev.nottekk.notvolt.formatters.formats.MessageFormat;
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
        MessageFormat message1 = new MessageFormat("Utilities:", true, false, false);
        MessageFormat message2 = new MessageFormat("gituser - Shows github user info (use github profile username)", false, false, false);
        MessageFormat message3 = new MessageFormat("ping - Checks the bot connection", false, false, false);
        MessageFormat message4 = new MessageFormat("whois - Provides information about the mentioned user\n", false, false, false);
        MessageFormat message5 = new MessageFormat("Fun:", true, false, false);
        MessageFormat message6 = new MessageFormat("ym - Makes YO MAMMA jokes with the mentioned user", false, false, false);
        MessageFormat message7 = new MessageFormat("gif - search and displays gifs based on users input\n\n", false, false, false);
        MessageEmbed embed = CommandEmbedBuilder.buildEmbed("\uD83D\uDD37   Help\n", "\n\nCreated by NotTekk", "https://cdn.discordapp.com/avatars/571414793454485511/0c8f3a894f28c57ef7fd4b110eb34082?size=1024", Color.BLUE, Arrays.asList(message1, message2, message3, message4, message5, message6, message7));

        event.getChannel().sendMessageEmbeds(embed).queue();
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, List<OptionMapping> options) {
        MessageFormat message1 = new MessageFormat("Utilities:", true, false, false);
        MessageFormat message2 = new MessageFormat("gituser - Shows github user info (use github profile username)", false, false, false);
        MessageFormat message3 = new MessageFormat("ping - Checks the bot connection", false, false, false);
        MessageFormat message4 = new MessageFormat("whois - Provides information about the mentioned user\n", false, false, false);
        MessageFormat message5 = new MessageFormat("Fun:", true, false, false);
        MessageFormat message6 = new MessageFormat("ym - Makes YO MAMMA jokes with the mentioned user", false, false, false);
        MessageFormat message7 = new MessageFormat("gif - search and displays gifs based on users input\n\n", false, false, false);
        MessageEmbed embed = CommandEmbedBuilder.buildEmbed("\uD83D\uDD37   Help\n", "\n\nCreated by NotTekk", "https://cdn.discordapp.com/avatars/571414793454485511/0c8f3a894f28c57ef7fd4b110eb34082?size=1024", Color.BLUE, Arrays.asList(message1, message2, message3, message4, message5, message6, message7));

        event.replyEmbeds(embed).queue();
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

}
