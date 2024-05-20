package dev.nottekk.notvolt.command.commands.utility;

import dev.nottekk.notvolt.command.Command;
import dev.nottekk.notvolt.utils.EAccessLevel;
import dev.nottekk.notvolt.handlers.ConfigHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class BotInfoCommand implements Command {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");

    @Override
    public void execute(MessageReceivedEvent event, List<String> context) {
        List<Guild> guilds = event.getJDA().getGuilds();
        String avatar = event.getJDA().getSelfUser().getAvatarUrl();
        String id = event.getJDA().getSelfUser().getId();
        String serverCount = String.valueOf(guilds.size());
        String creationDate = event.getJDA().getSelfUser().getTimeCreated().format(formatter);

        EmbedBuilder embedMessage = buildEmbed(avatar, id, serverCount, creationDate);

        event.getChannel().sendMessageEmbeds(embedMessage.build()).queue();
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, List<OptionMapping> options) {
        List<Guild> guilds = event.getJDA().getGuilds();
        String avatar = event.getJDA().getSelfUser().getAvatarUrl();
        String id = event.getJDA().getSelfUser().getId();
        String serverCount = String.valueOf(guilds.size());
        String creationDate = event.getJDA().getSelfUser().getTimeCreated().format(formatter);

        EmbedBuilder embedMessage = buildEmbed(avatar, id, serverCount, creationDate);

        event.replyEmbeds(embedMessage.build()).queue();
    }

    private EmbedBuilder buildEmbed(String avatar, String id, String serverCount, String creationDate) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.BLUE);
        embed.setTitle("\uD83E\uDD16   Bot Info\n");
        embed.setThumbnail(avatar);
        embed.addField(":id: Bot ID", id, true);
        embed.addField(":id: Version", ConfigHandler.get("VERSION"), true);
        embed.addField("\uD83C\uDF10 Server Count", "In " + serverCount + " servers",true);
        embed.addField(":clock2: Creation date", creationDate, true);
        embed.setFooter("\n\nCreated by NotTekk", ConfigHandler.get("OWNER_AVATAR"));
        return embed;
    }

    @Override
    public String getDescription() {
        return "Shows current bot info - info";
    }

    @Override
    public String getName() {
        return "info";
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
