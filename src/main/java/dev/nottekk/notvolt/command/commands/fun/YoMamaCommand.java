package dev.nottekk.notvolt.command.commands.fun;

import dev.nottekk.notvolt.Bot;
import dev.nottekk.notvolt.command.Command;
import dev.nottekk.notvolt.utils.EAccessLevel;
import dev.nottekk.notvolt.managers.LoggerManager;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class YoMamaCommand implements Command {

    private static final Logger LOGGER = LoggerManager.getLogger(Bot.class);
    @Override
    public void execute(MessageReceivedEvent event, List<String> context) {
        List<User> mentionedUsers = new ArrayList<>();

        for (User user : event.getMessage().getMentions().getUsers()) {
            if (!user.equals(event.getJDA().getSelfUser())) {
                mentionedUsers.add(user);
            }
        }

        if (mentionedUsers.isEmpty()) {
            event.getChannel().sendMessage("No user mentioned.").queue();
        } else {
            for (User u : mentionedUsers) {
                String joke = fetchJoke();
                if (joke == null) {
                    event.getChannel().sendMessage("No joke found").queue();
                } else {
                    event.getChannel().sendMessage(u.getAsMention() + " " + joke).queue();
                }
            }
        }
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, List<OptionMapping> options) {
        User mentionedUser = options.get(0).getAsUser();
        if (mentionedUser == null) {
            event.reply("No user mentioned.").queue();
        } else {
            String joke = fetchJoke();
            if (joke == null) {
                event.reply("No joke found").queue();
            } else {
                event.reply(mentionedUser.getAsMention() + " " + joke).queue();
            }
        }
    }

    private String fetchJoke() {
        OkHttpClient caller = new OkHttpClient();
        Request request = new Request.Builder().url("https://www.yomama-jokes.com/api/v1/jokes/random/").build();
        try {
            Response response = caller.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            return (String) json.get("joke");
        } catch (IOException | NullPointerException e) {
            LOGGER.log(Level.SEVERE, "[ERROR]", e);
            return null;
        }
    }


    @Override
    public String getDescription() {
        return "Sends a Yo Mama joke to the mentioned user - ym @/user";
    }

    @Override
    public String getName() {
        return "ym";
    }

    @Override
    public HashMap<OptionType, HashMap<String, String>> getOptionType() {
        HashMap<OptionType, HashMap<String, String>> res = new HashMap<>();
        HashMap<String, String> desc = new HashMap<>();
        desc.put("user", "User");
        res.put(OptionType.USER, desc);
        return res;
    }

    @Override
    public EAccessLevel getCommandLevel() {
        return EAccessLevel.USER;
    }

}
