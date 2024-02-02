package dev.nottekk.notvolt.listeners;

import dev.nottekk.notvolt.handlers.ConfigHandler;
import dev.nottekk.notvolt.managers.CommandManager;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageCommandListener extends ListenerAdapter {

    private final CommandManager commandManager = new CommandManager();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        String selfMention = "<@" + event.getJDA().getSelfUser().getId() + ">";
        String selfMentionWithNickname = "<@!" + event.getJDA().getSelfUser().getId() + ">";

        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }

        String raw = event.getMessage().getContentRaw();
        String prefix = raw.startsWith(selfMention) ? selfMention : selfMentionWithNickname;

        if (raw.equalsIgnoreCase(prefix + "shutdown")
                && user.getId().equals(ConfigHandler.get("OWNER_ID"))) {
            System.err.println("Shutting down");
            event.getJDA().shutdown();
            return;
        }

        if (raw.startsWith(prefix)) {
            commandManager.handleCommand(event, prefix);
        }
    }
}
