package freack100.supergun;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatHandler {

	public void sendMessage(Player p, String msg){
		p.sendMessage(ChatColor.translateAlternateColorCodes('§', "§7[§2"+ "SuperGun" +"§7] §a "+msg));
	}
	
	public void broadcast(String msg){
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('§', "§7[§2§l"+ Bukkit.getName() +"§7] §a "+msg));
	}
	
}
