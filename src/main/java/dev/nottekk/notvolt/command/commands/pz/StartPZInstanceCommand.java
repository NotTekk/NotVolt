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

public class StartPZInstanceCommand implements Command {

    public void execute(SlashCommandInteractionEvent slashEvent, List<OptionMapping> options) {
        try {
            slashEvent.getChannel().sendMessage("PZ Server - Checando Instancia AWS").queue();
            if (!isInstanceAlive()) {
                slashEvent.reply("PZ Server - Executando Comando - Iniciando Instancia AWS (**1:30 min!**)").queue();
                startInstance();
                slashEvent.reply("PZ Server - Instancia AWS Iniciada").queue();
                slashEvent.reply("PZ Server - Executando Comando - Iniciando PZ Server (**Max. 5 min!**)").queue();
            } else {
                slashEvent.reply("PZ Server - Instancia AWS já está ativa").queue();
            }
        } catch (Exception e) {
            slashEvent.reply("Um erro ocorreu na execução do comando! - NotTekk for notificado!").queue();
        }
    }

    public void execute(MessageReceivedEvent messageEvent, List<String> options) {
        try {
            messageEvent.getChannel().sendMessage("PZ Server - Checando Instancia AWS").queue();
            if (!isInstanceAlive()) {
                messageEvent.getChannel().sendMessage("PZ Server - Executando Comando - Iniciando Instancia AWS (**1:30 min!**)").queue();
                startInstance();
                messageEvent.getChannel().sendMessage("PZ Server - Instancia AWS Iniciada").queue();
                messageEvent.getChannel().sendMessage("PZ Server - Executando Comando - Iniciando PZ Server (**Max. 5 min!**)").queue();
            } else {
                messageEvent.getChannel().sendMessage("PZ Server - Instancia AWS já está ativa").queue();
            }
        } catch (Exception e) {
            messageEvent.getChannel().sendMessage("Um erro ocorreu na execução do comando! - NotTekk for notificado!").queue();
        }
    }

    private boolean isInstanceAlive() throws IOException {
        Process p = Runtime.getRuntime().exec("aws ec2 describe-instance-status --instance-ids i-01b6d00f7f535578c");
        return isInstanceRunning(p);
    }

    private void startInstance() throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec("aws ec2 start-instances --instance-ids i-01b6d00f7f535578c");
        while (!isInstanceRunning(p)) {
            Thread.sleep(30000);
            p = Runtime.getRuntime().exec("aws ec2 describe-instance-status --instance-ids i-01b6d00f7f535578c");
        }
        p.destroy();
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

    @Override
    public String getName() {
        return "pzstec";
    }

    @Override
    public String getDescription() {
        return "Starts AWS Instance && PZ Server";
    }

    @Override
    public HashMap<OptionType, HashMap<String, String>> getOptionType() {
        return null;
    }

}
