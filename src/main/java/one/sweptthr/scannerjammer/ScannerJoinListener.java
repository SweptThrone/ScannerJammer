package one.sweptthr.scannerjammer;

import java.util.Date;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ScannerJoinListener implements Listener {
	private final ScannerJammer plugin;
	
	public ScannerJoinListener( ScannerJammer plugin ) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoinServer( PlayerJoinEvent event ) {
		if ( plugin.exemptUsers.getBoolean( event.getPlayer().getName().toLowerCase() )
		|| event.getPlayer().getName().toLowerCase().contains( "scan" ) ) {
			event.getPlayer().ban( "You've been ScannerJammed!", new Date( Long.MAX_VALUE ), null, true );
			this.plugin.getLogger().info( "Successfully ScannerJammed " + event.getPlayer().getName() + "!" );
		}
	}
}
