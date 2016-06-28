package Inventar;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface Inventar1 {

	public static void createInventory(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9, "§4Report");
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

		inv.setItem(2, hack);
		inv.setItem(6, grief);

		p.openInventory(inv);
		p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 10, 100);
	}

}
