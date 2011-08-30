package ca.agnate.Snowballer;

import org.bukkit.event.player.PlayerListener;

public class SBPlayerListener extends PlayerListener {
	private Snowballer plugin;

    public SBPlayerListener(Snowballer plugin) {
        this.plugin = plugin;
    }
}
