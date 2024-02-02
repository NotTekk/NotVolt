package dev.nottekk.notvolt.command.commands.fun;

import dev.nottekk.notvolt.command.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GifCommand implements Command {
    @Override
    public void execute(MessageReceivedEvent event, List<String> context) {
        String url;
        JSONArray array;
        String query = "";
        for(String arg : context) {
            query += arg.toLowerCase() + "+";
            query = query.substring(0, query.length()-1);
        }
        OkHttpClient caller = new OkHttpClient();
        Request request = new Request.Builder().url("http://api.giphy.com/v1/gifs/search?q=" + query + "&api_key=CEleBCgisUiRAz0awAFGFcDizTnaN8qx").build();
        try {
            Random rand = new Random();
            Response response = caller.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            array = json.getJSONArray("data");
            //Random GIF returned by the API
            int gifIndex = rand.nextInt(array.length());
            url = (String) array.getJSONObject(gifIndex).get("url");
            event.getChannel().sendMessage(url).queue();
        } catch (IOException | NullPointerException e) {
            event.getChannel().sendMessage("No GIF found :cry:").queue();
        }
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, List<OptionMapping> options) {
        List<String> args = new ArrayList<>();
        String url;
        JSONArray array;
        String query = "";

        for (OptionMapping option : options) {
            args.add(option.toString());
        }

        for(String arg : args) {
            query += arg.toLowerCase() + "+";
            query = query.substring(0, query.length()-1);
        }
        OkHttpClient caller = new OkHttpClient();
        Request request = new Request.Builder().url("http://api.giphy.com/v1/gifs/search?q=" + query + "&api_key=CEleBCgisUiRAz0awAFGFcDizTnaN8qx").build();
        try {
            Random rand = new Random();
            Response response = caller.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            array = json.getJSONArray("data");
            //Random GIF returned by the API
            int gifIndex = rand.nextInt(array.length());
            url = (String) array.getJSONObject(gifIndex).get("url");
            event.reply(url).queue();
        } catch (IOException | NullPointerException e) {
            event.reply("No GIF found :cry:").queue();
        }
    }

    @Override
    public String getDescription() {
        return "Search and display a gif based on user input - gif <search-term>";
    }

    @Override
    public String getName() {
        return "gif";
    }

    @Override
    public HashMap<OptionType, HashMap<String, String>> getOptionType() {
        HashMap<OptionType, HashMap<String, String>> res = new HashMap<>();
        HashMap<String, String> desc = new HashMap<>();
        desc.put("search-term", "Searches from this input");
        res.put(OptionType.STRING, desc);
        return res;
    }
}
