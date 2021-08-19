package com.parrotgaming.chatsync;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.security.auth.login.LoginException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.JDA.Status;
import net.dv8tion.jda.api.entities.TextChannel;

public class EnableBot implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.isOp() == true) {
			try {
			      File myObj = new File("chatsync-data.txt");
			      if (myObj.createNewFile()) {
			        FileWriter myWriter = new FileWriter("chatsync-data.txt");
			        myWriter.write(args[0] + " " + args[1] + " " + args[2]);
			        myWriter.close();
			        Main.cacheFileArgs = args[0] + " " + args[1] + " " + args[2];
			        try {
						Main.jda = JDABuilder.createDefault(args[0]).build();
						Main.jda.getPresence().setStatus(OnlineStatus.ONLINE);
						
						Main.jda.addEventListener(new Commands());
						
			  		    try {
							Main.jda.awaitStatus(Status.CONNECTED);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
			  		    
						TextChannel textChannel = Main.jda.getGuildById(args[1]).getTextChannelsByName(args[2],true).get(0);
						textChannel.sendMessage("Connection Successful").queue();
						
						sender.sendMessage("[ChatSync] Connection to Discord Successful");
						
					} catch (LoginException e) {
						e.printStackTrace();
					}	
			      } else {
			        sender.sendMessage("A bot is already configured for this server");
			      }
			    } catch (IOException e) {
			      e.printStackTrace();
			    }
		    return true;	
		} else {
			sender.sendMessage("You do not have permission to execute this command");
			return false;
		}
	}
}
