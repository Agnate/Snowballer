package ca.agnate.Snowballer;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.event.entity.EntityListener;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;

public class Snowballer extends JavaPlugin {

    private int snowballDamage;
    private boolean throwFromDispensers;
    private boolean throwFromSnowmen;
    
    protected List<Node> permissionOPs;
    
    public void onDisable() {
	System.out.println("[" + this + "] Snowballer is disabled.");
    }

    public void onEnable() {
	// Add in permission node checks for OPs.
    	permissionOPs = new LinkedList<Node>();
    	
    	if ( this.getConfig().getBoolean("use_permissions", true) ) {
            permissionOPs.add( Node.DAMAGE );
            permissionOPs.add( Node.EXTINGUISH );
            permissionOPs.add( Node.KNOCKBACK );
    	}
	
	// Set defaults.
	snowballDamage = this.getConfig().getInt("snowball_damage", 3);
	throwFromDispensers = this.getConfig().getBoolean("allow_dispenser", false);
	throwFromSnowmen = this.getConfig().getBoolean("allow_snowman", false);
	
        // Set up listeners
	PluginManager pm = getServer().getPluginManager();
	EntityListener entityListener = new SBEntityListener(this);
	
	pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Lowest, this);
	
	// Save a default config file.
        this.getConfig().options().copyDefaults(true);
        saveConfig();
	
	System.out.println("[" + this + "] Snowballer is enabled.");
    }

    public boolean has(Permissible p, Node n) {
        // return (permissionHandler == null || permissionHandler.has(p, s));
        // return hasSuperPerms(p, s);
        return hasSuperPerms(p, n.toString()) || hasOPPerm(p, n);
    }
    protected boolean hasOPPerm(Permissible p, Node node) {
        // If the node requires OP status, and the player has OP, then true.
        return (permissionOPs == null || permissionOPs.contains(node) == false || p.isOp());
    }
    protected boolean hasSuperPerms(Permissible p, String s) {
        String[] nodes = s.split("\\.");
        
        // If they have the permission,
        if ( p.hasPermission(s) ) {
        	return true;
        }
        
        // Otherwise, check for any parent * nodes.
        String perm = "";
        for (int i = 0; i < nodes.length; i++) {
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
    public boolean allowSnowmen() {
	return throwFromSnowmen;
    }
}
