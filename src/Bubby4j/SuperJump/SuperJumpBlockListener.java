package Bubby4j.SuperJump;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import Bubby4j.SuperJump.SuperJump;

/**
 * Handle events for all Player related events
 * @author Bubby4j
 */

public class SuperJumpBlockListener implements Listener {
    private final SuperJump plugin;

    public SuperJumpBlockListener(SuperJump instance) {
        plugin = instance;
    }
    @EventHandler
    public void onSignChange(SignChangeEvent event) {
    	if (event.getLine(0).toUpperCase().contains("[JUMP]")){
    		if (plugin.onlyOpPlace && !event.getPlayer().isOp() && !event.getPlayer().hasPermission("superjump.create")) {
    			event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(323, 1));
    			event.getBlock().setTypeId(0);
    			event.getPlayer().sendMessage("Only Ops can create jump signs.");
    		}else{
        		int i = 1;
        		while (i <= 3) {
            		try {
            			Float.parseFloat(event.getLine(i));
            			if (Float.parseFloat(event.getLine(i)) > 50) {
            				event.setLine(i,"0");
            			}
            			}
            			catch (final NumberFormatException nfe) {
            				event.setLine(i,"0");
            			}
            			((Sign) event.getBlock().getState()).update();
            		i++;
        		}
    		}
    	}
    }
}

