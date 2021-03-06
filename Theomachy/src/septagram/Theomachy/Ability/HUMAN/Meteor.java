package septagram.Theomachy.Ability.HUMAN;

import java.util.Timer;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.Timer.Skill.MeteorTimer;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Meteor extends Ability
{
	private final static String[] des= {
			   "메테오는 유성을 소환하는 능력입니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"유성 소환",
			   " 자신의 위치를 기준으로 넓은 범위에 유성을 떨어뜨립니다." ,
			   "블럭은 메테오의 폭발에 파괴되지 않습니다."};
	
	public Meteor(String playerName)
	{
		super(playerName,"메테오", 117, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.cool1=110;
		this.sta1=20;
		
		this.rank=4;
	}
	
	public void T_Active(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, 369))
		{
			switch(EventFilter.PlayerInteract(event))
			{
			case 0:case 1:
				leftAction(player);
				break;
			}
		}
	}

	private void leftAction(Player player)
	{	
		if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, 4, sta1))
		{
			Skill.Use(player, 4, sta1, 0, cool1);
			Location location = player.getLocation();
			Timer t = new Timer();
			t.schedule(new MeteorTimer(player, location, 30), 0,200);
		}
	}
}
