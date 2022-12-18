package net.aariy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;


public class Main extends ListenerAdapter
{
    public static File FILE;
    public static ObjectNode node;
    public static void main(String[] args) throws IOException
    {
        JDA jda = JDABuilder.createDefault(null).enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_PRESENCES).setMemberCachePolicy(MemberCachePolicy.ALL).build();
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
            String list = "";
            e.reply(list.charAt(sc.nextInt(10))+"¦"+list.charAt(sc.nextInt(10))+"¦"+list.charAt(sc.nextInt(10))).queue();
            if(node.get(e.getUser().getId()) != null)
                node.put(e.getUser().getId(), 1);
            else
                node.put(e.getUser().getId(), node.get(e.getUser().getId()).asInt()+1);
        }
        else
        {
            e.reply("> :warning: 既に2回スロットを回しています。").queue();
        }
    }
}