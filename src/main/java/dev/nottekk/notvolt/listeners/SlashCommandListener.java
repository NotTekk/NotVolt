package dev.nottekk.notvolt.listeners;

import dev.nottekk.notvolt.managers.CommandManager;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandListener extends ListenerAdapter {

    CommandManager commandManager = new CommandManager();

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        User user = event.getUser();

        if (user.isBot()) {
            return;
        }

        commandManager.handleCommand(event);
    }

}
