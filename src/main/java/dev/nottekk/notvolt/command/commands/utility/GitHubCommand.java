package dev.nottekk.notvolt.command.commands.utility;

import dev.nottekk.notvolt.Bot;
import dev.nottekk.notvolt.command.Command;
import dev.nottekk.notvolt.managers.LoggerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GitHubCommand implements Command {

    private static final Logger LOGGER = LoggerManager.getLogger(Bot.class);
    @Override
    public void execute(MessageReceivedEvent event, List<String> context) {
        String msg = context.get(1);
        EmbedBuilder builder = getGithubUserInfo(msg);
        if (builder != null) {
            event.getChannel().sendMessageEmbeds(builder.build()).queue();
        } else {
            event.getChannel().sendMessage("Failed to fetch GitHub user info.").queue();
        }
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, List<OptionMapping> options) {
        String msg = options.get(1).getAsString();
        EmbedBuilder builder = getGithubUserInfo(msg);
        if (builder != null) {
            event.replyEmbeds(builder.build()).queue();
        } else {
            event.reply("Failed to fetch GitHub user info.").queue();
        }
    }

    private EmbedBuilder getGithubUserInfo(String username) {
        JSONObject json = fetchGithubUserJson(username);
        if (json != null) {
            return buildGithubUserInfo(json, username);
        } else {
            return null;
        }
    }

    private JSONObject fetchGithubUserJson(String username) {
        OkHttpClient caller = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.github.com/users/" + URLEncoder.encode(username, StandardCharsets.UTF_8)).build();
        try {
            Response response = caller.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException | NullPointerException | JSONException e) {
            LOGGER.log(Level.SEVERE, "[ERROR]", e);
            return null;
        }
    }

    private EmbedBuilder buildGithubUserInfo(JSONObject json, String username) {
        String pseudonym = json.getString("login");
        String bio = json.optString("bio", "None");
        String location = json.optString("location", "Unknown");
        String website = json.optString("blog", "None");

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.blue);
        builder.setAuthor("Information about " + pseudonym + " (" + username + ")", "https://github.com/" + username, "http://i.imgur.com/pH59eAC.png");
        builder.setThumbnail(json.getString("avatar_url"));

        builder.addField("User bio", bio, false);
        builder.addField("Location", location, true);
        builder.addField("Website", website, true);
        builder.addField("Public repositories", String.valueOf(json.getInt("public_repos")), true);
        builder.addField("Public gists", "" + String.valueOf(json.getInt("public_gists")), true);

        builder.addField("Followers", "" + String.valueOf(json.getInt("followers")), true);
        builder.addField("Following", "" + String.valueOf(json.getInt("following")), true);

        return builder;
    }

    @Override
    public String getDescription() {
        return "Search and display info on a GitHub User - gituser <username>";
    }

    @Override
    public String getName() {
        return "gituser";
    }

    @Override
    public HashMap<OptionType, HashMap<String, String>> getOptionType() {
        return null;
    }
}
