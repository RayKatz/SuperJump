package Bubby4j.SuperJump;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import Bubby4j.SuperJump.SuperJump;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

/**
 * Handle events for all Player related events
 * @author Bubby4j
 */
@SuppressWarnings("unused")
public class SuperJumpPlayerListener extends PlayerListener {
    private final SuperJump plugin;

    public SuperJumpPlayerListener(SuperJump instance) {
        plugin = instance;
    }

    //Insert Player related code here
    
    public void onPlayerMove(PlayerMoveEvent event) {
    	World world = event.getTo().getWorld();
    	Player player = event.getPlayer();
    	Location loc = new Location(world, event.getTo().getX(), event.getTo().getY() - 1, event.getTo().getZ());
    	Block blockon = world.getBlockAt(loc);
    	Location loc2 = new Location(world, event.getTo().getX(), event.getTo().getY() - 2, event.getTo().getZ());
    	Block block2 = world.getBlockAt(loc2);
    	
    	if (block2.getType().name().toUpperCase() == "SIGN_POST" || block2.getType().name().toUpperCase() == "SIGN") {
    		Sign sign = (Sign)block2.getState();
    		if (sign.getLine(0).toUpperCase().contains("[JUMP]")) {
    			int i = 1;
    			while (i <= 3) {
        			try {
        				Float.parseFloat(sign.getLine(i));
        				if (sign.getLine(i).length() > 7) {
        					sign.setLine(i,"0");
        					sign.update();
        				}
        				}
        				catch (NumberFormatException nfe) {
        					sign.setLine(i,"0");
        					sign.update();
        				}
    			
        			i++;
    			}
    			player.setVelocity(new Vector(Float.parseFloat(sign.getLine(1)),Float.parseFloat(sign.getLine(2)),Float.parseFloat(sign.getLine(3))));
    		}
    	}

    }
}

