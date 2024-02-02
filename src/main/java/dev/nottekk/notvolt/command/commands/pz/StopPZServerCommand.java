package dev.nottekk.notvolt.command.commands.pz;

import dev.nottekk.notvolt.command.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

public class StopPZServerCommand implements Command {
    @Override
    public void execute(MessageReceivedEvent event, List<String> context) {
        try {
            if (isInstanceAlive()) {
                event.getChannel().sendMessage("PZ Server - Parando Server").queue();
                executeStopCommand();
                event.getChannel().sendMessage("PZ Server - Server Fechado").queue();
            } else {
                event.getChannel().sendMessage("PZ Server - [ERRO] - A Instancia EC2 nao esta ativa").queue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, List<OptionMapping> options) {
        try {
            if (isInstanceAlive()) {
                event.reply("PZ Server - Parando Server").queue();
                executeStopCommand();
                event.reply("PZ Server - Server Fechado").queue();
            } else {
                event.reply("PZ Server - [ERRO] - A Instancia EC2 nao esta ativa").queue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isInstanceAlive() throws IOException {
        Process p = Runtime.getRuntime().exec("aws ec2 describe-instance-status --instance-ids i-01b6d00f7f535578c");
        return isInstanceRunning(p);
    }

    private boolean isInstanceRunning(Process p) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String checkInstance;
        boolean insideMethod = false;
        while ((checkInstance = br.readLine()) != null) {
            if (checkInstance.contains("InstanceStatus")) {
                insideMethod = true;
            }
            if (insideMethod && checkInstance.contains("Name")) {
                if (checkInstance.contains("running")) {
                    return true;
                }
            }
        }
        return false;
    }

    private void executeStopCommand() throws IOException {
        String[] cmd = {
                "aws",
                "ssm",
                "send-command",
                "--instance-ids",
                "i-01b6d00f7f535578c",
                "--document-name",
                "AWS-RunShellScript",
                "--parameters",
                "commands=[\"pkill screen\"]",
                "--region",
                "us-east-1",
                "--output",
                "text"
        };
        Process p = Runtime.getRuntime().exec(cmd);


        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String checkCommandRunning;
        while ((checkCommandRunning = br.readLine()) != null) {
            System.out.println(checkCommandRunning);
        }
    }

    private boolean isCommandRunning(Process p) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String checkCommandRunning;
        while ((checkCommandRunning = br.readLine()) != null) {
            if (checkCommandRunning.startsWith("COMMANDS")) {
                if (checkCommandRunning.contains("screen -r pz")) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "Starts the PZ Server directly";
    }

    @Override
    public String getName() {
        return "pzstop";
    }

    @Override
    public HashMap<OptionType, HashMap<String, String>> getOptionType() {
        return null;
    }
}
