package freack100.supergun;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Guns {

	ItemHandler ih = new ItemHandler();
	Main main;
	public Guns(Main main){
		this.main = main;
	}
	
	public ItemStack getPriceList(Integer price){
		ItemStack pricelist = new ItemStack(Material.ARROW,1);
		pricelist.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 0);
		ItemMeta im = pricelist.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add("Preis: " + price);
		im.setLore(lore);
		pricelist.setItemMeta(im);
		return pricelist;
		
	}
	
	public ItemStack getNormal(){
		ItemStack is = null;
		is = new ItemStack(Material.STONE_HOE,1);
		is = ih.setName(is, "Normal Gun", null);
		return is;
	}
	
	
	public ItemStack getAdvanced(){
		ItemStack is = null;
		is = new ItemStack(Material.IRON_HOE,1);
		is = ih.setName(is, "Advanced Gun", null);
		return is;
	}
	
	
	public ItemStack getMaster(){
		ItemStack is = null;
		is = new ItemStack(Material.DIAMOND_HOE,1);
		is = ih.setName(is,"Master Gun",null);
		return is;
	}
	/*
	public ItemStack getNormalShop(){
		ItemStack is = null;
		List<String> lore = new ArrayList<String>();
		lore.add("Preis: " + main.config.getInt("weapons.normal.price"));
		is = new ItemStack(Material.STONE_HOE,1);
		is = ih.setName(is, "Normal Gun", lore);
		return is;
	}
	
	public ItemStack getAdvancedShop(){
		ItemStack is = null;
		List<String> lore = new ArrayList<String>();
		lore.add("Preis: " + main.config.getInt("weapons.advanced.price"));
		is = new ItemStack(Material.IRON_HOE,1);
		is = ih.setName(is, "Advanced Gun", lore);
		return is;
	}
	
	public ItemStack getMasterShop(){
		ItemStack is = null;
		List<String> lore = new ArrayList<String>();
		lore.add("Preis: " + main.config.getInt("weapons.master.price"));
		is = new ItemStack(Material.DIAMOND_HOE,1);
		is = ih.setName(is,"Master Gun",lore);
		return is;
	}*/
	
}
