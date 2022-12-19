package net.aariy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;


public class Main extends ListenerAdapter
{
    public static File FILE;
    public static ObjectNode node;
    public static void main(String[] args) throws IOException
    {
        JDA jda = JDABuilder.createDefault("MTA1NDQ0OTYyMTM5ODcyMDY5NA.GbAZ9s.u8NJCQdqg2k3-sR8Qpc2DDstACCHawWzhyId0M").enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_PRESENCES).setMemberCachePolicy(MemberCachePolicy.ALL).build();
        jda.updateCommands().addCommands(
                Commands.slash("random", "ランダムにスロットを表示します。")
        ).queue();
        jda.addEventListener(new Main());
        if (new File("data.json").exists()) FILE = new File("data.json");
        else FILE = new File("build/resources/main/data.json");
        node = new ObjectMapper().readTree(FILE).deepCopy();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e)
    {
        if (node.get(e.getUser().getId()) != null && node.get(e.getUser().getId()).asInt() <= 2)
        {
            SecureRandom sc = new SecureRandom();
            String[] list = {"<:image0_21:1051895319224406097>", "❤️", "😗", "🐵", "💸", "🤪", "🙊"};
            e.reply(list[sc.nextInt(10)]+" "+list[sc.nextInt(10)]+" "+list[sc.nextInt(10)]).queue();
            if(node.get(e.getUser().getId()) != null)
                node.put(e.getUser().getId(), 1);
            else
                node.put(e.getUser().getId(), node.get(e.getUser().getId()).asInt()+1);
            reload();
        }
        else
        {
            e.reply(":warning: 既に2回スロットを回しています。").setEphemeral(true).queue();
        }
    }
    public static void reload()
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE));
            bw.write(new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(node));
            bw.flush();
            bw.close();
            node = new ObjectMapper().readTree(FILE).deepCopy();
        }
        catch (IOException ignored) {}
    }
}