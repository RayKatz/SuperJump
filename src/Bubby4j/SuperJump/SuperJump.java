package Bubby4j.SuperJump;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.config.Configuration;

/**
 * SuperJump for Bukkit
 *
 * @author Bubby4j
 */

public class SuperJump extends JavaPlugin {
    private final SuperJumpPlayerListener playerListener = new SuperJumpPlayerListener(this);
    private final SuperJumpBlockListener blockListener = new SuperJumpBlockListener(this);
    public Configuration config = null;
    public boolean onlyOpPlace = true;

    public void onEnable() {
    	PluginDescriptionFile pdfFile = this.getDescription();

    	boolean changed = false;
    	//Load config
    	config = this.getConfiguration();
    	config.load();

    	//Load "onlyOpPlace" config property and check if it's valid
    	if (config.getProperty("onlyOpPlace") == null) {
    		config.setProperty("onlyOpPlace", "true");
    		onlyOpPlace = true;
    		changed = true;
    	} else {
    		if (config.getProperty("onlyOpPlace").equals("true") || config.getProperty("onlyOpPlace").equals("yes")){
    			onlyOpPlace = true;
    		} else if (config.getProperty("onlyOpPlace").equals("false") || config.getProperty("onlyOpPlace").equals("no")){
    			onlyOpPlace = false;
    		} else {
    			System.out.println("[" + pdfFile.getName() + "] Invalid value for onlyOpPlace, defaulting to true");
    			config.setProperty("onlyOpPlace", "true");
    			changed = true;
    		}
    		
        	if (config.getProperty("timeout") == null) {
        		config.setProperty("timeout", "0.5");
        		changed = true;
        	} else {
        		try{
        			playerListener.timeout = (Integer) config.getProperty("timeout");
        		}catch (Exception e){
        			config.setProperty("timeout", "0.5");
        			changed = true;
        			System.out.println("[" + pdfFile.getName() + "] Invalid value for timeout, must be a number, defaulting to 0.5 seconds");
        		}
        	}
    		
    	}
    	//Save config if it was changed
    	if (changed) {
    		config.save();
    		changed = false;
    	}

        // Register events
    	PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.SIGN_CHANGE, blockListener, Priority.Normal, this);

        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " has been enabled!");
    }

    public void onDisable() {
    	PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " has been disabled.");
    }
}

