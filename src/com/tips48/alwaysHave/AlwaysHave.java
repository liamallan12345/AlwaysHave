package com.tips48.alwaysHave;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Event.Type;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class AlwaysHave extends JavaPlugin {
	private String configString;
	protected boolean usePermissions;
	private String excludedPlayers;
	protected ArrayList playersExcluded = new ArrayList();
	public PermissionHandler permissionHandler;
	protected ArrayList<String> itemsToAdd = new ArrayList<String>();
	String mainDirectory = "plugins/AlwaysHave";
	File ConfigCreate = new File(mainDirectory + File.separator
			+ "config.properties");
	Properties prop = new Properties();
	private AlwaysHavePlayerListener pListener = new AlwaysHavePlayerListener(
			this);
	private String prefix = "[AlwaysHave]";
	private double version = 0.1;
	private int subVersion = 0;
	Logger log = Logger.getLogger("Minecraft");

	public void onEnable() {
		log.info(prefix + " " + version + "_" + subVersion + " was enabled.");
		new File(mainDirectory).mkdir();
		if (!ConfigCreate.exists()) {
			try {
				ConfigCreate.createNewFile();
				FileOutputStream out = new FileOutputStream(ConfigCreate);
				prop.put("ItemToGive", "10:10,11:3");
				prop.put("Use-permissions", "false");
				prop.put("Excluded-Players", "newboy123,griefer3");
				prop.store(out, "AlwaysHave Config");
				out.flush();
				out.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		loadProcedure();
		parseConfig();
		if (usePermissions == true) {
			setupPermissions();
		}
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Type.PLAYER_JOIN, pListener, Priority.Lowest, this);
		pm.registerEvent(Type.PLAYER_MOVE, pListener, Priority.Lowest, this);
	}

	public void onDisable() {
		log.info(prefix + " was disabled.");
	}

	public void loadProcedure() {
		FileInputStream InFile = null;
		try {
			InFile = new FileInputStream(ConfigCreate);
			prop.load(InFile);
			InFile.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		configString = prop.getProperty("ItemToGive");
		usePermissions = Boolean.parseBoolean("Use-permissions");
		excludedPlayers = prop.getProperty("Excluded-Players");
	}

	private void parseConfig() {
		String[] a = configString.split(",");
		for (String temp : a) {
			itemsToAdd.add(temp);
		}
	}

	private void parseNames() {
		String[] b = excludedPlayers.split(",");
		for (String temp : b) {
			playersExcluded.add(temp);
		}
	}

	private void setupPermissions() {
		Plugin permissionsPlugin = this.getServer().getPluginManager()
				.getPlugin("Permissions");
		if (this.permissionHandler == null) {
			if (permissionsPlugin != null) {
				this.permissionHandler = ((Permissions) permissionsPlugin)
						.getHandler();
				log.info(prefix + " Permissions detected.");
			}
		}
	}
}
