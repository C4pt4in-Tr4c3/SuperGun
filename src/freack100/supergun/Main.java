package freack100.supergun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	LobbyHandler lobbyhandler = new LobbyHandler(this);
	ChatHandler ch = new ChatHandler();
	
	List<String> players = new ArrayList<String>();
	List<String> redteam = new ArrayList<String>();
	List<String> blueteam = new ArrayList<String>();
	HashMap<String,Integer> normalcooldown = new HashMap<String,Integer>();
	HashMap<String,Integer> advacedcooldown = new HashMap<String,Integer>();
	int bluelive;
	int redlive;
	int needPlayers = 1;
	int maxPlayers = 20;
	int wait = 10;
	Boolean ingame = false;
	Boolean started = false;

	public FileConfiguration config;
	Logger logger = Bukkit.getLogger();
	


	@SuppressWarnings("deprecation")
	@Override
	public void onEnable(){
		//Lobby Runnable
		this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable(){
			public void run(){
				if(!(ingame)){
					for(String name : players){
						Player p = Bukkit.getPlayerExact(name);
						if(players.size() < needPlayers){
							p.setLevel(100);
							wait = 10;
						}
						else
						{
							p.setLevel(wait);
							wait--;
						}
					}
					if(wait < 1){
						ingame = true;
					}
				}else{
					if(!(started)){
						started = true;
						wait = 10;
						if(!blueteam.isEmpty()){
							for(String name : blueteam){
								Player p = Bukkit.getPlayerExact(name);
								p.teleport(new Location(Bukkit.getWorld(config.getString("blue.spawn.world")),config.getDouble("blue.spawn.x"),config.getDouble("blue.spawn.y"),config.getDouble("blue.spawn.z")));
								ch.sendMessage(p, "Du wurdest zum §9Blauen §6 Spawn teleportiert!");
							}
						}
						
						if(!redteam.isEmpty()){
							for(String name : redteam){
								Player p = Bukkit.getPlayerExact(name);
								p.teleport(new Location(Bukkit.getWorld(config.getString("red.spawn.world")),config.getDouble("red.spawn.x"),config.getDouble("red.spawn.y"),config.getDouble("red.spawn.z")));
								ch.sendMessage(p, "Du wurdest zum §4Roten §6 Spawn teleportiert!");
							}
						}
					}
					
					
					//Adding the cooldown
					Iterator it = normalcooldown.entrySet().iterator();
					while(it.hasNext()){
						Map.Entry map = (Map.Entry)it.next();
						normalcooldown.put((String)map.getKey(), (Integer)map.getValue()-1);
						if((Integer)map.getValue() <= 0){
							normalcooldown.remove((String)map.getKey());
						}
					}
				}
			}
		}, 0L, 20L);
		
	//Ingame Runnable

		logger.info("SuperGun"  + " Enabled!");
		
		//Registering the commands
		this.getCommand("gunjoin").setExecutor(lobbyhandler);
		this.getCommand("gunlobby").setExecutor(lobbyhandler);
		this.getCommand("gunleave").setExecutor(lobbyhandler);
		this.getCommand("gunred").setExecutor(lobbyhandler);
		this.getCommand("gunblue").setExecutor(lobbyhandler);
		this.getCommand("gunspectate").setExecutor(lobbyhandler);
		this.getCommand("inventory").setExecutor(lobbyhandler);
		
		//Registering the Events
		Bukkit.getPluginManager().registerEvents(new ShopHandler(this), this);
		
		this.config = getConfig();

		//Lobby Spawn
			config.addDefault("lobby.x", Bukkit.getWorlds().get(0).getSpawnLocation().getBlockX());
			config.addDefault("lobby.y",Bukkit.getWorlds().get(0).getSpawnLocation().getBlockY());
			config.addDefault("lobby.z", Bukkit.getWorlds().get(0).getSpawnLocation().getBlockZ());
			config.addDefault("lobby.world",Bukkit.getWorlds().get(0).getName());
			
		//Blue Spawn
			config.addDefault("blue.spawn.x", Bukkit.getWorlds().get(0).getSpawnLocation().getBlockX());
			config.addDefault("blue.spawn.y", Bukkit.getWorlds().get(0).getSpawnLocation().getBlockY());
			config.addDefault("blue.spawn.z", Bukkit.getWorlds().get(0).getSpawnLocation().getBlockZ());
			config.addDefault("blue.spawn.world", Bukkit.getWorlds().get(0).getName());
			
		//Red Spawn
			config.addDefault("red.spawn.x", Bukkit.getWorlds().get(0).getSpawnLocation().getBlockX());
			config.addDefault("red.spawn.y", Bukkit.getWorlds().get(0).getSpawnLocation().getBlockY());
			config.addDefault("red.spawn.z", Bukkit.getWorlds().get(0).getSpawnLocation().getBlockZ());
			config.addDefault("red.spawn.world", Bukkit.getWorlds().get(0).getName());
			
		//Spectator Lobby
			config.addDefault("spectate.world",Bukkit.getWorlds().get(0).getName());
			config.addDefault("spectate.x", Bukkit.getWorlds().get(0).getSpawnLocation().getBlockX());
			config.addDefault("spectate.y", Bukkit.getWorlds().get(0).getSpawnLocation().getBlockY());
			config.addDefault("spectate.z", Bukkit.getWorlds().get(0).getSpawnLocation().getBlockZ());
		
		//Prices
			config.addDefault("weapons.normal.price", 100);
			config.addDefault("weapons.advanced.price", 250);
			config.addDefault("weapons.master.price", 500);
			
			
		//The shop password
			config.addDefault("shop.password","password");
			
		//Configuration
			config.addDefault("config.teamLive", 50);
			config.addDefault("config.minPlayers", 10);
			config.addDefault("config.maxPlayers",20);
		
		//Points
			config.addDefault("players.laklaklak.points",20000);
			config.addDefault("players.laklaklak.money",10000);
		
		//Saving the Configuration
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		redlive = config.getInt("config.teamLive");
		bluelive = config.getInt("config.teamlive");
	}
	
	@Override
	public void onDisable(){
		logger.info("SuperGun" + " Disabled!");
	}
	
	
}
