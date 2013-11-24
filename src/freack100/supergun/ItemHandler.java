package freack100.supergun;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemHandler {

	public ItemStack setName(ItemStack is, String name, List lore){
		ItemMeta im = is.getItemMeta();
		if(!(name==null)){
			im.setDisplayName(name);
		}
		if(!(lore==null)){
			im.setLore(lore);
		}
		is.setItemMeta(im);
		return is;
		
	}
	
}
