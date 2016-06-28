package Report;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Report extends JavaPlugin {
	
	public YamlConfiguration cfg = (YamlConfiguration) getConfig();
	public static final File player_data = new File("plugins//Reports//player_reports");

	public void onEnable() {

		getCommand("report").setExecutor(new ReportCmd(this));
		getCommand("hilfe").setExecutor(new HilfeCmd(this));
		loadConfig();
		
	}


	public static File getPlayerFile(Player p) {
		return new File(player_data, p.getUniqueId().toString() + ".yml");
	}

	public void loadConfig() {
		@SuppressWarnings("unused")
		
		YamlConfiguration cfg = (YamlConfiguration) getConfig();
		saveConfig();
	}
}
