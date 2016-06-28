package Report;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HilfeCmd implements CommandExecutor {

	private Report plugin;
	
	ArrayList<Player> cooldown = new ArrayList<Player>();

	public HilfeCmd(Report plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player p = (Player) sender;

		if (cooldown.contains(p)) {
			p.sendMessage("[§bHilfe§f] §cBitte warte einen moment bis du erneut um Hilfe beten darfst!");

			
		} else {
			if (args.length >= 1) {

				String message = "";
				for (int i = 0; i != args.length; i++) {
					message += args[i] + " ";
				}

				p.sendMessage("[§bHilfe§f] §aDu hast ein Team-Mitglied um Hilfe gebeten!");
				cooldown.add(p);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin = plugin, new Runnable() {
					public void run() {
						cooldown.remove(p);
					}
				}, 20*15);
				
				for (Player team : Bukkit.getOnlinePlayers()) {
					if (team.hasPermission("SettlaCore.Helfer")) {
						team.sendMessage("[§bHilfe§f]");
						team.sendMessage("§b[§b§k_-_-_-_-_-_-_-_-§b]");
						team.sendMessage("[§bHilfe§f] §aDer Spieler §c" + p.getName() + " §abrauch Hilfe!");
						team.sendMessage("[§bHilfe§f] §4Grund: §a" + message);
						team.sendMessage("§b[§b§k_-_-_-_-_-_-_-_-§b]");
						
						
				
					}
				}

			} else {
				p.sendMessage("[§bHilfe§f] §a/hilfe <Grund>");

			}
		}
		return false;
	}

}
