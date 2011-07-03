package com.tips48.alwaysHave;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Event.Type;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;

public class AlwaysHave extends JavaPlugin {
	public static PermissionHandler permissionHandler;

	private String configString;
	protected ArrayList<String> itemsToAdd = new ArrayList<String>();
	String mainDirectory = "plugins/AlwaysHave";
	
	File ConfigCreate = new File(mainDirectory + File.separator
			+ "config.properties");
			
	Properties prop = new Properties();
	private AlwaysHavePlayerListener pListener = new AlwaysHavePlayerListener(this);
	private String prefix = "[AlwaysHave]";
	private double version = 0.1;
	private int subVersion = 0;
	Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable(){
	
		setupPermissions();
		
		log.info(prefix + " " + version + "_" + subVersion + " was enabled.");
		new File(mainDirectory).mkdir();
		if (!ConfigCreate.exists()) {
			try {
				ConfigCreate.createNewFile();
				FileOutputStream out = new FileOutputStream(ConfigCreate);
				prop.put("ItemToGive", "10:10,11:3");
				prop.store(out, "AlwaysHave Config");
				out.flush();
				out.close();
			} 
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		loadProcedure();
		parseConfig();
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Type.PLAYER_JOIN, pListener, Priority.Lowest, this);
		pm.registerEvent(Type.PLAYER_MOVE, pListener, Priority.Lowest, this);
	}
	
	public void onDisable(){
		log.info(prefix + " was disabled.");
	}
	
	private void setupPermissions() {
	    if (permissionHandler != null) {
	        return;
	    }
	    
	    Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
	    
	    if (permissionsPlugin == null) {
	    	this.log.info("Permission system not detected, defaulting to OP");
	        return;
	    }
	    
	    permissionHandler = ((Permissions) permissionsPlugin).getHandler();
	    this.log.info("Found and will use plugin "+((Permissions)permissionsPlugin).getDescription().getFullName());
	}
	
	public void loadProcedure() {
		FileInputStream InFile = null;
		try {
			InFile = new FileInputStream(ConfigCreate);
			prop.load(InFile);
			InFile.close();

		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
		configString = prop.getProperty("ItemToGive");
	}
	private void parseConfig(){
		String[] a = configString.split(",");
		for(String temp : a){
			itemsToAdd.add(temp);
		}
	}
}
