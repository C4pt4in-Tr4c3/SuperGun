package freack100.supergun;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class LobbyHandler implements CommandExecutor {

	
	Main main;
	ChatHandler ch = new ChatHandler();
	
	public LobbyHandler(Main main){
		this.main = main;
	}
	
	

	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if(cmd.getName().equalsIgnoreCase("gunjoin")){
			if(!(sender instanceof Player)){
				sender.sendMessage("Du kannst nur als Spieler joinen!");
			return true;
			}
			Player p = (Player) sender;
			if(main.players.contains(p.getName())){
				ch.sendMessage(p, "Du bist bereits in der Lobby!");
				return true;
			}
			p.teleport(new Location(Bukkit.getWorld(main.config.getString("lobby.world")), main.config.getDouble("lobby.x"), main.config.getDouble("lobby.y"), main.config.getDouble("lobby.z")));
			ch.sendMessage(p, "Du bist in die SuperGun lobby gejoint!");
			if(main.redteam.size() < main.blueteam.size()){
				main.redteam.add(p.getName());
				ch.sendMessage(p, "Wilkommen im §4Roten §aTeam!");
			}
			else if(main.blueteam.size() < main.redteam.size()) {
				main.blueteam.add(p.getName());
				ch.sendMessage(p, "Wilkommen im §9Blauen §a Team!");
			}
			else if(main.blueteam.size() == main.redteam.size()){
				ch.sendMessage(p, "Wilkommen im §9Blauen §a Team!");
				main.blueteam.add(p.getName());
			}

			main.players.add(p.getName());
			ch.sendMessage(p, "§4"+main.redteam.size()+" §9"+main.blueteam.size());
			return true;
		}

	else if(cmd.getName().equalsIgnoreCase("gunleave")){
		if(!(sender instanceof Player)){
			sender.sendMessage("Du kannst nur als Spieler joinen!");
		return true;
		}

	Player p = (Player) sender;
	p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
	ch.sendMessage(p, "Du hast die SuperGun lobby verlassen!");
	if(main.redteam.contains(p.getName())){
		main.redteam.remove(p.getName());
	}
	else{
		main.blueteam.remove(p.getName());
	}
	main.players.remove(p.getName());
	return true;
	}else if(cmd.getName().equalsIgnoreCase("gunlobby")){
		if(!(sender instanceof Player)){
			sender.sendMessage("Nur Spieler können die Lobby setzen!");
		return true;
		}
		Player p = (Player) sender;
		main.config.set("lobby.x", p.getLocation().getBlockX());
		main.config.set("lobby.y", p.getLocation().getBlockY());
		main.config.set("lobby.z", p.getLocation().getBlockZ());
		main.config.set("lobby.world", p.getWorld().getName());
		main.saveConfig();
		ch.sendMessage(p,"Lobby erfolgreich gesetzt!");
		return true;
	}
	else if(cmd.getName().equalsIgnoreCase("gunspectate")){
		Player p = (Player) sender;
		p.teleport(new Location(Bukkit.getWorld(main.config.getString("spectate.world")),main.config.getDouble("spectate.x"),main.config.getDouble("spectate.y"),main.config.getDouble("spectate.z")));
		ch.sendMessage(p, "Du bist nun in der Spectator Lobby!");
		return true;
	}
		
	return false;
	
	
	
	}



	public void start() {
		
		for(String name : main.blueteam){
			Player p = Bukkit.getPlayerExact(name);
			p.teleport(new Location(Bukkit.getWorld(main.config.getString("blue.spawn")),main.config.getDouble("blue.x"),main.config.getDouble("blue.y"),main.config.getDouble("blue.z")));
			ch.sendMessage(p, "Du wurdest zum §9Blauen $6 Spawn teleportiert!");
		}
		
		for(String name : main.redteam){
			Player p = Bukkit.getPlayerExact(name);
			p.teleport(new Location(Bukkit.getWorld(main.config.getString("red.spawn")),main.config.getDouble("red.x"),main.config.getDouble("red.y"),main.config.getDouble("red.z")));
			ch.sendMessage(p, "Du wurdest zum §4Roten $6 Spawn teleportiert!");
		}
		
	}

}
