package ca.agnate.Snowballer;

import org.bukkit.block.Dispenser;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class SBEntityListener extends EntityListener {
    private Snowballer plugin;

    public SBEntityListener(Snowballer plugin) {
	this.plugin = plugin;
    }

    public void onEntityDamageByEntityEvent(EntityDamageEvent event) {
	// Return if it's not a Projectile (aka Snowball).
	if ( event.getCause() != DamageCause.PROJECTILE ) { return; }

	EntityDamageByEntityEvent e = (event instanceof EntityDamageByEntityEvent) ? (EntityDamageByEntityEvent) event : null;

	// Return if it's not an EntityDamageByEntityEvent.
	if ( e == null ) { return; }

	Entity damager = e.getDamager();
	Entity damagee = event.getEntity();

	// Only concerned with snowballs.
	if ( !(damager instanceof Snowball) ) { return; }

	Snowball sb = (Snowball) damager;

	// Check if shooter has enhanced Snowballer permission.
	if ( sb.getShooter() instanceof Player ) {
	    Player p = (Player) sb.getShooter();

	    // If they are able to damage, apply it.
	    if ( plugin.has(p, Node.DAMAGE.toString() ) ) {
		event.setDamage( plugin.getSnowballDamage() );
	    }
	}
	else if ( plugin.allowDispensers()  &&  sb.getShooter() instanceof Dispenser ) {
	    Dispenser d = (Dispenser) sb.getShooter();

	    // Apply damage.
	    event.setDamage( plugin.getSnowballDamage() );
	}
    }
}
