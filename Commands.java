package com.parrotgaming.chatsync;

import org.bukkit.Bukkit;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Commands extends ListenerAdapter{
	public void onGuildMessageReceived(MessageReceivedEvent event) {
		String message = event.getMessage().getContentStripped();
		
		if(event.getMessage().getAuthor() != Main.jda.getSelfUser()) {
			String[] readerArgs = Main.cacheFileArgs.split(" ");
			if (event.getChannel().getName().equals(readerArgs[2])) {
				Bukkit.broadcastMessage("(Discord) <" + event.getMessage().getAuthor().getName() + ">" + " " + message);	
			}
		}
	}
}
