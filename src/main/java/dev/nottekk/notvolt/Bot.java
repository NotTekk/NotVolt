package dev.nottekk.notvolt;

import dev.nottekk.notvolt.handlers.ConfigHandler;
import dev.nottekk.notvolt.listeners.MessageCommandListener;
import dev.nottekk.notvolt.listeners.ReadyStateListener;
import dev.nottekk.notvolt.listeners.SlashCommandListener;
import dev.nottekk.notvolt.managers.CommandManager;
import dev.nottekk.notvolt.managers.LoggerManager;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bot {

    private static final Logger LOGGER = LoggerManager.getLogger(Bot.class);

    public static void main(String[] args) {

        ConfigHandler configHandler = new ConfigHandler(".env");
        configHandler.load();

        LOGGER.info("NotVolt - [STARTING]");

        CommandManager commandManager = new CommandManager();

        try {

            JDABuilder.createDefault(
                            ConfigHandler.get("TOKEN"),
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.GUILD_VOICE_STATES,
                            GatewayIntent.MESSAGE_CONTENT
                    )
                    .disableCache(EnumSet.of(
                            CacheFlag.CLIENT_STATUS,
                            CacheFlag.ACTIVITY
                    ))
                    .enableCache(CacheFlag.VOICE_STATE)
                    .addEventListeners(
                            new ReadyStateListener(commandManager),
                            new MessageCommandListener(commandManager),
                            new SlashCommandListener(commandManager))
                    .setStatus(OnlineStatus.DO_NOT_DISTURB)
                    .setActivity(Activity.watching(ConfigHandler.get("WATCHING")))
                    .build();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "[ERROR]", e);
        }
    }

}
