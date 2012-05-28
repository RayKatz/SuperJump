package Bubby4j.SuperJump;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

/**
 * SuperJump for Bukkit
 *
 * @author Bubby4j
 */
//TODO: Work for other entities

public class SuperJump extends JavaPlugin {
    private final SuperJumpMovementListener playerListener = new SuperJumpMovementListener(this);
    private final SuperJumpBlockListener blockListener = new SuperJumpBlockListener(this);
    public boolean onlyOpPlace = true;

    public void onEnable() {
    	PluginDescriptionFile pdfFile = this.getDescription();
    	System.out.println("["+ pdfFile.getName() + "] Enabling " + pdfFile.getName() + " " + pdfFile.getVersion());
    	
    	//Load config
    	FileConfiguration config = this.getConfig();
    	getConfig().options().copyDefaults(true);

    	//Load "onlyOpPlace" config property
    	if (config.getBoolean("onlyOpPlace", true) == true){
    		onlyOpPlace = true;
    		config.set("onlyOpPlace", true);
    	} else if (config.getBoolean("onlyOpPlace",true) == false){
    		onlyOpPlace = false;
    		config.set("onlyOpPlace", false);
    	} else {
    		System.out.println("[" + pdfFile.getName() + "] Invalid value for onlyOpPlace, defaulting to true");
    		config.set("onlyOpPlace", true);
    	}

    	//Load timeout property
        try{
        	playerListener.timeout = config.getInt("timeout", 500);
        	config.set("timeout", config.getInt("timeout", 500));
        }catch (Exception e){
        	config.set("timeout", 500);
        	System.out.println("[" + pdfFile.getName() + "] Invalid value for timeout, must be a number, defaulting to 500 milliseconds");
       	}

    	//Save config
    	this.saveConfig();

        // Register events
    	PluginManager pm = getServer().getPluginManager();
    	pm.registerEvents(playerListener, this);
    	pm.registerEvents(blockListener, this);
    }

    public void onDisable() {
    	PluginDescriptionFile pdfFile = this.getDescription();
    	System.out.println("["+ pdfFile.getName() + "] Disabling " + pdfFile.getName() + " " + pdfFile.getVersion());
    }
}

