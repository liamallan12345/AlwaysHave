package com.tips48.alwaysHave;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class AlwaysHavePlayerListener extends PlayerListener {
	private AlwaysHave plugin;

	public AlwaysHavePlayerListener(AlwaysHave core) {
		plugin = core;
	}

	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if (plugin.usePermissions == true) {
			if (!(plugin).permissionHandler.has(p, "AlwaysHave.allowed")) {
				return;
			} else {
				PlayerInventory items = p.getInventory();
				ArrayList<String> itemAmmount = plugin.itemsToAdd;
				for (String s : itemAmmount) {
					String[] a = s.split(":");
					ItemStack item = new ItemStack(Integer.parseInt(a[0]),
							Integer.parseInt(a[1]));
					if (!(items.contains(Integer.parseInt(a[0])))) {
						items.addItem(item);
					}
				}
			}
		} else {
			if (!(plugin.playersExcluded.contains(p.getName()))) {
				PlayerInventory items = p.getInventory();
				ArrayList<String> itemAmmount = plugin.itemsToAdd;
				for (String s : itemAmmount) {
					String[] a = s.split(":");
					ItemStack item = new ItemStack(Integer.parseInt(a[0]),
							Integer.parseInt(a[1]));
					if (!(items.contains(Integer.parseInt(a[0])))) {
						items.addItem(item);
					}
				}
			}
		}
	}

	public void onPlayerMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (plugin.usePermissions == true) {
			if (!(plugin).permissionHandler.has(p, "AlwaysHave.allowed")) {
				return;
			} else {
				PlayerInventory items = p.getInventory();
				ArrayList<String> itemAmmount = plugin.itemsToAdd;
				for (String s : itemAmmount) {
					String[] a = s.split(":");
					ItemStack item = new ItemStack(Integer.parseInt(a[0]),
							Integer.parseInt(a[1]));
					if (!(items.contains(Integer.parseInt(a[0])))) {
						items.addItem(item);
					}
				}
			}
		} else {
			PlayerInventory items = p.getInventory();
			ArrayList<String> itemAmmount = plugin.itemsToAdd;
			for (String s : itemAmmount) {
				String[] a = s.split(":");
				ItemStack item = new ItemStack(Integer.parseInt(a[0]),
						Integer.parseInt(a[1]));
				if (!(items.contains(Integer.parseInt(a[0])))) {
					items.addItem(item);
				}
			}
		}
	}
}
