package com.parrotgaming.chatsync;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDA.Status;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.TextChannel;

public class Main extends JavaPlugin {
	public static JDA jda;
	public static String cacheFileArgs;
	
	public static void main(String[] args) throws LoginException {
		System.out.println(" ");
	}
	
    @Override
    public void onEnable(){
		
		this.getCommand("chatsync-start").setExecutor(new EnableBot());
		this.getCommand("chatsync-reset").setExecutor(new DisableBot());
		
		Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
		
		try {
        	try {
  		      File ReadObj = new File("chatsync-data.txt");
  		      Scanner myReader = new Scanner(ReadObj);
  		      EmbedBuilder eb = new EmbedBuilder();
  		      eb.setTitle("Server Online");
  			  eb.setColor(Color.green);
  		      while (myReader.hasNextLine()) {
  		        String data = myReader.nextLine();
  		        String[] readerArgs = data.split(" ");
	  		    Main.jda = JDABuilder.createDefault(readerArgs[0]).build();
	  		    Main.jda.getPresence().setStatus(OnlineStatus.ONLINE);
					
				Main.jda.addEventListener(new Commands());
				
	  		    try {
					Main.jda.awaitStatus(Status.CONNECTED);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
	  		    cacheFileArgs = readerArgs[0] + " " + readerArgs[1] + " " + readerArgs[2];
	  		    
				TextChannel textChannel = jda.getGuildById(readerArgs[1]).getTextChannelsByName(readerArgs[2],true).get(0);
				textChannel.sendMessage(eb.build()).queue();
  		      }
  		      myReader.close();
  		      Bukkit.broadcastMessage("[ChatSync] Connection to Discord Successful");
  		    } catch (FileNotFoundException e) {
  		      System.out.println("An error occurred.");
  		      e.printStackTrace();
  		    }
		} catch (LoginException e) {
			e.printStackTrace();
		}
		
    	System.out.println("Online");
    }

    @Override
    public void onDisable() {
    	String[] readerArgs = cacheFileArgs.split(" ");
    	
    	EmbedBuilder eb = new EmbedBuilder();
	    eb.setTitle("Server Offline");
		eb.setColor(Color.black);
		
    	TextChannel textChannel = jda.getGuildById(readerArgs[1]).getTextChannelsByName(readerArgs[2],true).get(0);
		textChannel.sendMessage(eb.build()).queue();
    	System.out.println("Shutting Down...");
    }
}