package com.parrotgaming.chatsync;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.dv8tion.jda.api.entities.TextChannel;

public class DisableBot implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.isOp() == true) {
			Path fileToDeletePath = Paths.get("chatsync-data.txt");
			try {
				String[] readerArgs = Main.cacheFileArgs.split(" ");
		        TextChannel textChannel = Main.jda.getGuildById(readerArgs[1]).getTextChannelsByName(readerArgs[2],true).get(0);
				textChannel.sendMessage("Disconnection Successful").queue();
				Files.delete(fileToDeletePath);
				Main.cacheFileArgs = "";
				Main.jda = null;
				sender.sendMessage("[ChatSync] Successfully disconnected from Discord");
			} catch (IOException e) {
				e.printStackTrace();
				sender.sendMessage("Cannot reset bot, there is no bot configured for this server");
				return false;
			}
			return true;	
		} else {
			sender.sendMessage("You do not have permission to execute this command");
			return false;
		}
	}
}
