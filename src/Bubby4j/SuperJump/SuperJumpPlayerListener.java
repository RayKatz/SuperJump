package Bubby4j.SuperJump;

import java.util.Date;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import Bubby4j.SuperJump.SuperJump;

/**
 * Handle events for all Player related events
 * @author Bubby4j
 */

public class SuperJumpPlayerListener extends PlayerListener {
    //private final SuperJump plugin;
	private final HashMap<Player, Integer> lastruns = new HashMap<Player, Integer>();
	public double timeout = 0.5;

    public SuperJumpPlayerListener(SuperJump instance) {
        //plugin = instance;
    }
    
    public Integer getLastRun(Player player){
        if (lastruns.containsKey(player)) {
            return lastruns.get(player);
        } else {
            return -1;
        }
    }
    public void setLastRun(Player player){
    	lastruns.put(player, (int)new Date().getTime());
    }
    
    public void onPlayerMove(PlayerMoveEvent event) {
    	final Location target = event.getTo();
    	final World world = target.getWorld();
    	final Block block2 = world.getBlockAt(new Location(world, target.getX(), target.getY() - 2, target.getZ()));
    	final Material type = block2.getType();
    	if (type != Material.SIGN_POST && type != Material.SIGN_POST){return;}
    	final Sign sign = (Sign)block2.getState();

    	if (sign.getLine(0).toUpperCase().contains("[JUMP]")) {
    		final Player player = event.getPlayer();
        	if ((int)new Date().getTime()-getLastRun(player) < timeout*1000 && (int)new Date().getTime()-getLastRun(player) > -1){return;}

			final Location location = player.getLocation();
			final float yaw = location.getYaw();
			final float pitch = location.getPitch();
    		setLastRun(event.getPlayer());
    		player.setVelocity(new Vector(0,0,0));
    		player.teleport(new Location(world, target.getX(), target.getY(), target.getZ(), yaw, pitch));
    		player.setVelocity(new Vector(Float.parseFloat(sign.getLine(1)),Float.parseFloat(sign.getLine(2)),Float.parseFloat(sign.getLine(3))));
    	}
   	}
}

