package Inventar;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface Inventar2 {

	public static void createInventory(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9, "§4Report§f-§5Confirm");

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

		inv.setItem(2, ja);
		inv.setItem(6, nein);

		p.openInventory(inv);
		p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 10, 100);

	}
}
