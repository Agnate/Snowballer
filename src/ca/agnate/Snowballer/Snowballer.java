package ca.agnate.Snowballer;

import java.io.File;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;

public class Snowballer extends JavaPlugin {

    private static List<String> permissionOPs;

    private int snowballDamage;
    private boolean throwFromDispensers;
    
    public static File dir;

    public void onDisable() {
	System.out.println("[" + this + "] Snowballer is disabled.");
    }

    public void onEnable() {
	// Setup directory and desc info.
	dir = getDataFolder();
        
        // Set defaults.
	snowballDamage = 3;
	throwFromDispensers = false;
        
        // Retrieve the config details.
        getConfig();
	
        // Set up listeners
	PluginManager pm = getServer().getPluginManager();
	final EntityListener entityListener = new SBEntityListener(this);

	pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Lowest, this);
	
	
	System.out.println("[" + this + "] Snowballer is enabled.");
    }

    public boolean has(Player p, String s)
    {
	//return (permissionHandler == null || permissionHandler.has(p, s));
	//return hasSuperPerms(p, s) || hasOPPerm(p, s);
	return hasSuperPerms(p, s);
    }

    public boolean hasOPPerm (Player p, String node) {
	// If the node requires OP status, and the player has OP, then true.
	return( permissionOPs == null  || permissionOPs.contains(node) == false || p.isOp() );
    }

    public boolean hasSuperPerms(Player p, String s)
    {
	String[] nodes = s.split("\\.");

	String perm = "";
	for (int i = 0; i < nodes.length; i++)
	{
	    perm += nodes[i] + ".";
	    if (p.hasPermission(perm + "*"))
		return true;
	}

	return p.hasPermission(s);
    }

    public int getSnowballDamage() {
	return snowballDamage;
    }

    public boolean allowDispensers() {
	return throwFromDispensers;
    }
    
    public void getConfig () {
	if (!dir.exists()) dir.mkdirs();
	
	File file = new File(dir, "config.yml");
        
	// Make Configuration object
        Configuration config = new Configuration( file );
        config.load();
        
        if ( config.getProperty("snowball-damage") == null ) {
            config.setProperty("snowball-damage", 0);
        }
        else {
            snowballDamage = (Integer) config.getProperty("snowball-damage");
        }
        
        if ( config.getProperty("allow-dispensers") == null ) {
            config.setProperty("allow-dispensers", false);
        }
        else {
            throwFromDispensers = (Boolean) config.getProperty("allow-dispensers");
        }
        
        // Save the file
        config.save();
    }
}
