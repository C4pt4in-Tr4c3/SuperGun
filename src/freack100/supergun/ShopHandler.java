package freack100.supergun;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopHandler implements Listener {

	public ShopHandler(Main main){
		this.main = main;
	}
	
	Main main;
	
	public List<String> opened = new ArrayList<String>();
	ItemHandler ih = new ItemHandler();
	Guns g = new Guns(main);
	ChatHandler ch = new ChatHandler();
	
	public void open(Player p){
		p.openInventory(this.getInv());
		opened.add(p.getName());
	}
	
	public void close(Player p){
		opened.remove(p.getName());
	}
	
	public Inventory getInv(){
		Inventory inv = Bukkit.createInventory(null,2*9,"Shop");
		ItemStack ph = new ItemStack(Material.PISTON_EXTENSION,1);
		ph = ih.setName(ph,"Platzhalter",null);
		for(int i = 0; i < 2*9; i++){
			inv.setItem(i, ph);
		}
		inv.setItem(0, g.getNormal());
		inv.setItem(1, g.getAdvanced());
		inv.setItem(2, g.getMaster());
		inv.setItem(9,g.getPriceList(main.config.getInt("weapons.normal.price")));
		inv.setItem(10,g.getPriceList(main.config.getInt("weapons.advanced.price")));
		inv.setItem(11,g.getPriceList(main.config.getInt("weapons.master.price")));
		return inv;
	}
	
	@EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent evt){
		Player p = (Player) evt.getPlayer();
		close(p);
	}
	
	@EventHandler
	public void onPlayerKickEvent(PlayerKickEvent evt){
		Player p = (Player) evt.getPlayer();
		close(p);
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent evt){
		Player p = (Player) evt.getPlayer();
		close(p);
	}
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent evt){
		Player p = (Player) evt.getWhoClicked();
		if(opened.contains(p.getName())){
			if(evt.getRawSlot() == evt.getSlot()){
				evt.setCancelled(true);
				
				
				//Checking for specific Items
				if(evt.getCursor().equals(g.getNormal())){
					if(main.config.getInt("players." + p.getName() + ".money") >= main.config.getInt("weapons.normal.price")){
						main.config.set("players." + p.getName() + ".money", main.config.getInt("players." + p.getName() + ".money") - main.config.getInt("weapons.normal.price"));
						main.saveConfig();
						main.reloadConfig();
						p.getInventory().addItem(g.getNormal());
					}
				}
				else if(evt.getCursor().equals(g.getAdvanced())){
					if(main.config.getInt("players." + p.getName() + ".money") >= main.config.getInt("weapons.advanced.price")){
						main.config.set("players." + p.getName() + ".money", main.config.getInt("players." + p.getName() + ".money") - main.config.getInt("weapons.advanced.price"));
						main.saveConfig();
						main.reloadConfig();
						p.getInventory().addItem(g.getAdvanced());
					}
				}
				else if(evt.getCursor().equals(g.getMaster())){
					if(main.config.getInt("players." + p.getName() + ".money") >= main.config.getInt("weapons.master.price")){
						main.config.set("players." + p.getName() + ".money", main.config.getInt("players." + p.getName() + ".money") - main.config.getInt("weapons.master.price"));
						main.saveConfig();
						main.reloadConfig();
						p.getInventory().addItem(g.getMaster());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent evt){
		Block block = evt.getClickedBlock();
		if(evt.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(block.getType().equals(Material.WALL_SIGN)|| block.getType().equals(Material.SIGN_POST)){
				Sign s = (Sign) block.getState();
				if(s.getLine(1).equals("[SHOP]")){
					this.open(evt.getPlayer().getPlayer());
				}
			}
		}
	}
	
	@EventHandler
	public void onSignChangeEvent(SignChangeEvent evt){
			Sign s = (Sign) evt.getBlock().getState();
			if((evt.getLine(1).equalsIgnoreCase("[SHOP]"))){
				if(evt.getLine(2).equals(main.config.getString("shop.password"))){
					evt.setLine(2,"");
					evt.setLine(1,"[SHOP]");
				}
				else{
					ch.sendMessage(evt.getPlayer().getPlayer(), "Du musst das richtige Passwort eingeben!");
					evt.getBlock().breakNaturally();
				}
			}
		}
	}
	

