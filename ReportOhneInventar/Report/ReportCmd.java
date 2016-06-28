package Report;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R1.EntityPlayer;

public class ReportCmd implements CommandExecutor {
	private Report plugin;

	public ReportCmd(Report plugin) {
		this.plugin = plugin;
	}

	ArrayList<Player> cooldown = new ArrayList<Player>();

	public static int getPing(Player p) {
		CraftPlayer pingp = (CraftPlayer) p;
		EntityPlayer pinge = pingp.getHandle();
		return pinge.ping;
	}

	Date date = new Date();

	SimpleDateFormat sdf = new SimpleDateFormat("'Datum:' dd MMMM yyyy  'Uhrzeit:'HH:mm:ss", Locale.GERMAN);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player p = (Player) sender;

		if (sender instanceof Player) {

			if (args.length >= 2) {

				if (cooldown.contains(p)) {
					p.sendMessage(
							"[§4REPORT§f] §cBitte warte einen moment bevor du einen neuen Spieler reporten kannst!");
				} else {

					Player t = Bukkit.getPlayer(args[0]);
					if (Bukkit.getOnlinePlayers().contains(t)) {
						String message = "";
						for (int i = 1; i != args.length; i++) {
							message += args[i] + " ";

						}

						if (args.length >= 2) {
							cooldown.add(p);
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
								public void run() {
									cooldown.remove(p);
								}
							}, 20 * 15);

							File playerFile = Report.getPlayerFile(p);
							FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
							if (!playerFile.exists()) {
								try {
									playerFile.createNewFile();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							String grund = "GRUND: " + message;
							String ziel = "ZIEL: " + t.getName().toString();
							String daten = sdf.format(date);
							String name = "Name des Reporters: " + p.getName();

							String all = name + "  " + grund + "  " + " " + ziel + "  " + daten;

							config.createSection(all);

							try {
								config.save(playerFile);
							} catch (IOException e1) {

								e1.printStackTrace();
							}

							p.sendMessage("[§4REPORT§f] §aDu hast den Spieler §c" + t.getPlayer().getName()
									+ " §aerfolgreich wegen §b " + message + "§a reportet!");
							for (Player team : Bukkit.getOnlinePlayers()) {
								if (team.hasPermission("SettlaCore.Report")) {

									int x = (int) p.getLocation().getX();
									int y = (int) p.getLocation().getY();
									int z = (int) p.getLocation().getZ();

									team.sendMessage("[§4REPORT§f] §aNeuer Report!");
									team.sendMessage("§b_-_-_-_-_-_-_");
									team.sendMessage("[§4REPORT§f] §aName: §b" + t.getName());
									team.sendMessage("[§4REPORT§f] §aReporter: §b" + p.getName());

									team.sendMessage("[§4REPORT§f] §aGrund: §4" + message);
									team.sendMessage(
											"[§4REPORT§f] §aLocation: §bX:§c " + x + " §bY: §c" + y + " §b Z:§c " + z);
									team.sendMessage("[§4REPORT§f] §aWelt:§b  " + t.getWorld().getName());
									team.sendMessage("[§4REPORT§f] §aPing: §b" + getPing(t));
								}
							}

						}

					} else {
						p.sendMessage("[§4REPORT§f] §cDer angegebene Spieler ist nicht Online!");

					}
				}

			} else {
				sender.sendMessage("[§4REPORT§f] §c/report <NAME> <GRUND>");
			}
			return false;
		}
		return false;

	}

}
