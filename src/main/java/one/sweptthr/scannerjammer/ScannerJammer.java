package one.sweptthr.scannerjammer;

import java.io.File;
import java.io.IOException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ScannerJammer extends JavaPlugin {
	public File dataFile;
	public YamlConfiguration exemptUsers;
	
	public YamlConfiguration loadExemptUsers() {
		return YamlConfiguration.loadConfiguration( this.dataFile );
	}
	
	public void saveExemptUsers() {
		try {
			this.exemptUsers.save( this.dataFile );
		} catch ( IOException err ) {
			this.getLogger().severe( "A fatal error has occurred while saving the exempt users file.  This might cause issues!" );
		}
	}

	@Override
	public void onEnable() {
		// [the server prints a message by default]
		
		// add event handler
		this.getServer().getPluginManager().registerEvents( new ScannerJoinListener( this ), this );
		
		// create this plugin's data folder and empty data file
		if ( this.getDataFolder().mkdir() ) {
			this.getLogger().info( "Created data folder" );
		}
		this.dataFile = new File( this.getDataFolder(), "exempt.yml" );
		this.exemptUsers = new YamlConfiguration();
		if ( this.dataFile.exists() ) {
			this.exemptUsers = this.loadExemptUsers();
			this.getLogger().info( "Loading exempt users list." );
		} else {
			this.saveExemptUsers();
		}
	}

	@Override
	public void onDisable() {
		// [the server prints a message by default]
	}

	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
		if ( command.getName().equalsIgnoreCase( "exempt" ) ) {
			if ( sender.hasPermission( "scannerjammer.admin" ) || sender.isOp() ) {
				if ( args.length != 1 ) {
					return false;
				}
				
				this.exemptUsers.set( args[ 0 ].toLowerCase(), true );
				this.saveExemptUsers();
				sender.sendMessage( "§a" + args[ 0 ] + " has been added to the exempt users list." );
				return true;
			} else {
				sender.sendMessage( "§cYou do not have permission to use this command." );
				return true;
			}
		}
		
		return false;
	}
}
