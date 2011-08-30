package ca.agnate.Snowballer;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;

public class Snowballer extends JavaPlugin {

    private static List<String> permissionOPs;

    private int snowballDamage;
    private boolean throwFromDispensers;

    static {

    }

    public void onDisable() {
	System.out.println("[" + this + "] Snowballer is disabled.");
    }

    public void onEnable() {
	PluginManager pm = getServer().getPluginManager();
	final PlayerListener playerListener = new SBPlayerListener(this);
	final EntityListener entityListener = new SBEntityListener(this);

	//pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, playerListener, Priority.Monitor, this);

	pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Monitor, this);

	// Grab data from config file.
	snowballDamage = 10;
	throwFromDispensers = true;

	System.out.println("[" + this + "] Snowballer is enabled.");
    }

    public boolean has(Player p, String s)
    {
	//return (permissionHandler == null || permissionHandler.has(p, s));
	return hasSuperPerms(p, s) || hasOPPerm(p, s);
    }

    public boolean hasOPPerm (Player p, String node) {
	// If the node requires OP status, and the player has OP, then true.
	return( permissionOPs.contains(node) == false || p.isOp() );
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
}
