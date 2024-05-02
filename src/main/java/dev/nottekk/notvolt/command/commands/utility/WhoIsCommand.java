package dev.nottekk.notvolt.command.commands.utility;

import dev.nottekk.notvolt.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class WhoIsCommand implements Command {
    @Override
    public void execute(MessageReceivedEvent event, List<String> context) {
        if(event.getMessage().getMentions().getUsers().isEmpty()) {
            event.getChannel().sendMessage("No user mentioned.").queue();
        } else {
            User user = event.getMessage().getMentions().getUsers().get(1);
            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor("Information about " + user.getGlobalName(), null, "http://i.imgur.com/880AyL6.png");
            builder.setColor(event.getGuild().getMemberById(user.getId()).getColor());
            builder.setThumbnail(user.getAvatarUrl());
            builder.addField(":id: User ID", user.getId(), true);

            String nickname = "None";
            if (event.getGuild().getMemberById(user.getId()).getNickname() != null) {
                nickname = event.getGuild().getMemberById(user.getId()).getNickname();
            }
            builder.addField(":information_source: Nickname", nickname, true);
            //builder.addField(":computer: Status", event.getJDA().getUserById(user.getIdLong()).get, true);

            String activity = "None";
            if (!event.getGuild().getMemberById(user.getId()).getActivities().isEmpty()) {
                activity = event.getGuild().getMemberById(user.getId()).getActivities().get(0).getName();
            }
            builder.addField(":video_game: Activity", activity, true);

            String isOwner = "No";
            if (event.getGuild().getMemberById(user.getId()).isOwner()) {
                isOwner = "Yes";
            }
            builder.addField(":white_check_mark: Owner", isOwner, true);

            String role = "No role";
            if (!event.getGuild().getMemberById(user.getId()).getRoles().isEmpty()) {
                role = event.getGuild().getMemberById(user.getId()).getRoles().get(0).getAsMention();
            }
            builder.addField(":medal: Highest role", role, true);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
            builder.addField(":clock2: Creation date", user.getTimeCreated().format(formatter), true);
            builder.addField(":inbox_tray:  Join date", event.getGuild().getMemberById(user.getId()).getTimeJoined().format(formatter), true);

            event.getChannel().sendMessageEmbeds(builder.build()).queue();
        }
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, List<OptionMapping> options) {
        if(options.isEmpty()) {
            event.getChannel().sendMessage("No user mentioned.").queue();
        } else {
            User user = options.get(0).getAsUser();
            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor("Information about " + user.getGlobalName(), null, "http://i.imgur.com/880AyL6.png");
            builder.setColor(event.getGuild().getMemberById(user.getId()).getColor());
            builder.setThumbnail(user.getAvatarUrl());
            builder.addField(":id: User ID", user.getId(), true);

            String nickname = "None";
            if (event.getGuild().getMemberById(user.getId()).getNickname() != null) {
                nickname = event.getGuild().getMemberById(user.getId()).getNickname();
            }
            builder.addField(":information_source: Nickname", nickname, true);
            //builder.addField(":computer: Status", event.getJDA().getUserById(user.getIdLong()).get, true);

            String activity = "None";
            if (!event.getGuild().getMemberById(user.getId()).getActivities().isEmpty()) {
                activity = event.getGuild().getMemberById(user.getId()).getActivities().get(0).getName();
            }
            builder.addField(":video_game: Activity", activity, true);

            String isOwner = "No";
            if (event.getGuild().getMemberById(user.getId()).isOwner()) {
                isOwner = "Yes";
            }
            builder.addField(":white_check_mark: Owner", isOwner, true);

            String role = "No role";
            if (!event.getGuild().getMemberById(user.getId()).getRoles().isEmpty()) {
                role = event.getGuild().getMemberById(user.getId()).getRoles().get(0).getAsMention();
            }
            builder.addField(":medal: Highest role", role, true);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
            builder.addField(":clock2: Creation date", user.getTimeCreated().format(formatter), true);
            builder.addField(":inbox_tray:  Join date", event.getGuild().getMemberById(user.getId()).getTimeJoined().format(formatter), true);

            event.replyEmbeds(builder.build()).queue();
        }
    }

    @Override
    public String getDescription() {
        return "Gets discord user information - whois @/user";
    }

    @Override
    public String getName() {
        return "whois";
    }

    @Override
    public HashMap<OptionType, HashMap<String, String>> getOptionType() {
        HashMap<OptionType, HashMap<String, String>> res = new HashMap<>();
        HashMap<String, String> desc = new HashMap<>();
        desc.put("user", "User");
        res.put(OptionType.USER, desc);
        return res;
    }
}
