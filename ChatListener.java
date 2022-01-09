package com.parrotgaming.chatsync;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class ChatListener implements Listener {
	
	@EventHandler
	public void onChatMessage(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String messageContent = event.getMessage();
		
		String[] readerArgs = Main.cacheFileArgs.split(" ");
        TextChannel textChannel = Main.jda.getGuildById(readerArgs[1]).getTextChannelsByName(readerArgs[2],true).get(0);
		textChannel.sendMessage("<" + player.getName() + "> " + messageContent).queue();
	}
	@EventHandler
	public void PlayerQuitEvent(PlayerQuitEvent event) {
		String name = event.getPlayer().getName();
		int playerCount = Bukkit.getOnlinePlayers().size() -1;
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Player Left " + "(" + playerCount + "/" + Bukkit.getMaxPlayers() + ")");
		eb.setColor(Color.red);
		eb.setDescription(name + " Left");
		eb.setThumbnail("https://crafatar.com/avatars/" + event.getPlayer().getUniqueId());
		
		String[] readerArgs = Main.cacheFileArgs.split(" ");
		TextChannel textChannel = Main.jda.getGuildById(readerArgs[1]).getTextChannelsByName(readerArgs[2],true).get(0);
        textChannel.sendMessageEmbeds(eb.build()).queue();
	}
	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent event) {
		String name = event.getPlayer().getName();
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Player Joined " + "(" + Bukkit.getServer().getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ")");
		eb.setColor(Color.green);
		eb.setDescription(name + " Joined");
		eb.setThumbnail("https://crafatar.com/avatars/" + event.getPlayer().getUniqueId());
		
		String[] readerArgs = Main.cacheFileArgs.split(" ");
		TextChannel textChannel = Main.jda.getGuildById(readerArgs[1]).getTextChannelsByName(readerArgs[2],true).get(0);
        textChannel.sendMessageEmbeds(eb.build()).queue();
	}
	@EventHandler
	public void PlayerDeathEvent(PlayerDeathEvent event) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setThumbnail("https://crafatar.com/avatars/" + event.getEntity().getUniqueId() + "?size=25");
		eb.setTitle(event.getDeathMessage());
		eb.setColor(Color.black);
		
		String[] readerArgs = Main.cacheFileArgs.split(" ");
		TextChannel textChannel = Main.jda.getGuildById(readerArgs[1]).getTextChannelsByName(readerArgs[2],true).get(0);
        textChannel.sendMessageEmbeds(eb.build()).queue();
	}
}
