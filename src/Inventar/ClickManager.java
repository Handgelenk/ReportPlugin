package Inventar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ClickManager implements Listener {

	private ReportMain plugin;

	public ClickManager(ReportMain plugin) {
		this.plugin = plugin;
	}

	ArrayList<String> list = new ArrayList<String>();

	Date date = new Date();

	SimpleDateFormat sdf = new SimpleDateFormat("'Datum:' dd MMMM yyyy  'Uhrzeit:'HH:mm:ss", Locale.GERMAN);

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		// ________________________________
		ItemStack hack = new ItemStack(Material.IRON_SWORD);
		ItemMeta hackmeta = hack.getItemMeta();
		hackmeta.setDisplayName("§4§LHACKING");

		hack.setItemMeta(hackmeta);
		// ________________________________

		// ________________________________
		ItemStack grief = new ItemStack(Material.IRON_PICKAXE);
		ItemMeta griefmeta = grief.getItemMeta();
		griefmeta.setDisplayName("§4§LGRIEFING");

		grief.setItemMeta(griefmeta);
		// ________________________________

		// INVENTAR 1
		if (e.getInventory().getName().equalsIgnoreCase("§4Report")) {
			try {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4§lHACKING")) {
					e.setCancelled(true);
					p.closeInventory();
					Inventar2.createInventory(p);
					list.add("§4Hacking");

				}

				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4§lGRIEFING")) {
					e.setCancelled(true);
					p.closeInventory();
					list.add("§4Griefing");
					Inventar2.createInventory(p);

				}
			} catch (Exception ex) {

			}

		}

		// --------------------------------------------------------------------------------------
		// //

		// ________________________________
		ItemStack ja = new ItemStack(Material.STAINED_GLASS);
		ItemMeta jameta = ja.getItemMeta();
		jameta.setDisplayName("§2§lJA");

		ja.setItemMeta(jameta);
		// ________________________________

		// ________________________________
		ItemStack nein = new ItemStack(Material.BARRIER);
		ItemMeta neinmeta = ja.getItemMeta();
		neinmeta.setDisplayName("§4§lNEIN");

		nein.setItemMeta(neinmeta);
		// ________________________________

		// INVENTAR2
		if (e.getInventory().getName().equalsIgnoreCase("§4Report§f-§5Confirm")) {
			try {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2§lJA")) {

					ReportMain.cooldown.add(p);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
						public void run() {
							ReportMain.cooldown.remove(p);
						}
					}, 20 * 15);

					File playerFile = ReportMain.getPlayerFile(p);
					FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
					if (!playerFile.exists()) {
						try {
							playerFile.createNewFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					String grund = "GRUND: " + list;
					String ziel = "ZIEL: " + ReportMain.name;
					String daten = sdf.format(date);
					String name = "Name des Reporters: " + p.getName();

					String all = name + "  " + grund + "  " + " " + ziel + "  " + daten;

					config.createSection(all);

					try {
						config.save(playerFile);
					} catch (IOException e1) {

						e1.printStackTrace();
					}

					for (Player team : Bukkit.getOnlinePlayers()) {
						if (team.hasPermission("SettlaCore.Report")) {

							team.sendMessage("[§4REPORT§f] §aNeuer Report!");
							team.sendMessage("§b_-_-_-_-_-_-_");
							team.sendMessage("[§4REPORT§f] §aName: §b" + ReportMain.name);
							team.sendMessage("[§4REPORT§f] §aReporter: §b" + p.getName());

							team.sendMessage("[§4REPORT§f] §aGrund: §4" + list);
							team.sendMessage("[§4REPORT§f] §aLocation: §bX:§c " + ReportMain.x + " §bY: §c"
									+ ReportMain.y + " §b Z:§c " + ReportMain.z);
							team.sendMessage("[§4REPORT§f] §aWelt:§b  " + ReportMain.welt);
							team.sendMessage("[§4REPORT§f] §aPing: §b" + ReportMain.ping);
						}
					}

					list.removeAll(list);
					ReportMain.name.removeAll(ReportMain.name);
					ReportMain.x.removeAll(ReportMain.x);
					ReportMain.y.removeAll(ReportMain.y);
					ReportMain.z.removeAll(ReportMain.z);
					ReportMain.welt.removeAll(ReportMain.welt);
					ReportMain.ping.removeAll(ReportMain.ping);

					p.sendMessage("[§4REPORT§f] §aDanke für deinen Report!");

					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
					e.setCancelled(true);
					p.closeInventory();

				}

				// ______________________-s

				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4§lNEIN")) {
					e.setCancelled(true);
					p.closeInventory();

				}

			} catch (Exception ex) {

			}

		}

	}
}
