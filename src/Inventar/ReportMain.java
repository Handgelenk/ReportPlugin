package Inventar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_9_R1.EntityPlayer;

public class ReportMain extends JavaPlugin {

	public static ArrayList<Player> cooldown = new ArrayList<Player>();
	public static List<Integer> x = new ArrayList<Integer>();
	public static List<Integer> y = new ArrayList<Integer>();
	public static List<Integer> z = new ArrayList<Integer>();
	public static ArrayList<Integer> ping = new ArrayList<Integer>();
	public static List<String> welt = new ArrayList<String>();
	public static List<String> name = new ArrayList<String>();

	public YamlConfiguration cfg = (YamlConfiguration) getConfig();
	public static final File player_data = new File("plugins//Reports2//playerInv_reports");

	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new ClickManager(this), this);
	}

	public static int getPing(Player t) {
		CraftPlayer pingp = (CraftPlayer) t;
		EntityPlayer pinge = pingp.getHandle();
		return pinge.ping;
	}

	public static File getPlayerFile(Player p) {
		return new File(player_data, p.getUniqueId().toString() + ".yml");
	}

	public void loadConfig() {
		@SuppressWarnings("unused")

		YamlConfiguration cfg = (YamlConfiguration) getConfig();
		saveConfig();
	}
	
	
	
	
	
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("report2")) {
			if (sender instanceof Player) {

				if (args.length == 1) {

					if (cooldown.contains(p)) {
						p.sendMessage(
								"[§4REPORT§f] §cBitte warte einen moment bevor du einen neuen Spieler reporten kannst!");
					} else {

						@SuppressWarnings("deprecation")
						Player t = Bukkit.getPlayer(args[0]);
						if (Bukkit.getOnlinePlayers().contains(t)) {
							x.add((int) t.getLocation().getX());
							y.add((int) t.getLocation().getY());
							z.add((int) t.getLocation().getZ());
							ping.add(getPing(p));
							welt.add(t.getWorld().getName());
							name.add(t.getName());

							Inventar1.createInventory(p);

						} else {
							p.sendMessage("[§4REPORT§f] §cDer angegebene Spieler ist nicht Online!");

						}
					}

				} else {
					sender.sendMessage("[§4REPORT§f] §c/report <NAME>");
				}
				return false;
			}
		}

		return false;

	}

}
