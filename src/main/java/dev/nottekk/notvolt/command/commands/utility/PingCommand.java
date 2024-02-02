package dev.nottekk.notvolt.command.commands.utility;

import dev.nottekk.notvolt.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

public class PingCommand implements Command {

    private Color getColorByPing(long ping) {
        if (ping < 100)
            return Color.cyan;
        if (ping < 400)
            return Color.green;
        if (ping < 700)
            return Color.yellow;
        if (ping < 1000)
            return Color.orange;
        return Color.red;
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashEvent, List<OptionMapping> options) {

        long ping = slashEvent.getJDA().getGatewayPing();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(getColorByPing(ping));
        builder.setTitle("\uD83C\uDFD3  Pong!");
        builder.setDescription("Ping : " + slashEvent.getJDA().getGatewayPing() + " ms");

        slashEvent.replyEmbeds(builder.build()).queue();


    }

    @Override
    public void execute(MessageReceivedEvent messageEvent, List<String> context) {
        long ping = messageEvent.getJDA().getGatewayPing();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(getColorByPing(ping));
        builder.setTitle("\uD83C\uDFD3  Pong!");
        builder.setDescription("Ping : " + messageEvent.getJDA().getGatewayPing() + " ms");

        messageEvent.getChannel().sendMessageEmbeds(builder.build()).queue();
    }

    public String getDescription() {
        return "Return voids's ping";
    }

    public String getName() {
        return "ping";
    }

    @Override
    public HashMap<OptionType, HashMap<String, String>> getOptionType() {
        return null;
    }

}
