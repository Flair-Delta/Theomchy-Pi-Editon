package septagram.Theomachy.Ability.GOD;

import java.util.Timer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.Timer.Skill.ApollonPlayerScorching;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Apollon extends Ability
{
	private final static int coolTime1=90;
	private final static int coolTime2=180;
	private final static int material=4;
	private final static int stack1=1;
	private final static int stack2=10;
	private final static String[] des= {"태양의 신입니다.",
			   "일반능력은 밤을 낮으로 바꿀 수 있습니다.",
			   "고급능력은 밤을 낮으로 바꿈과 동시에 내리고 있는 비와 눈을 증발시키며",
			   "자신을 제외한 모든 플레이어들을 태웁니다.",
			   "단, 화염속성의 능력자, 그늘, 물속에 있는 플레이어는 피해를 입지 않습니다."};
	
	public Apollon(String playerName)
	{
		super(playerName, "아폴론", 6, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.cool1=coolTime1;
		this.cool2=coolTime2;
		this.sta1=stack1;
		this.sta2=stack2;
		this.rank=2;
		
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
			case 2:case 3:
				rightAction(player);
				break;
			}
		}
	}

	private void leftAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 1)&&PlayerInventory.ItemCheck(player, material, stack1))
		{
			Skill.Use(player, material, stack1, 1, coolTime1);
			World world = player.getWorld();
			world.setTime(6000);
			Bukkit.broadcastMessage(ChatColor.YELLOW+"태양의 신이 해를 띄웠습니다.");
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, material, stack2))
		{
			Skill.Use(player, material, stack2, 2, coolTime2);
			World world = player.getWorld();
			world.setTime(6000);
			world.setStorm(false);
			Timer t = new Timer();
			t.schedule(new ApollonPlayerScorching(player, 15), 5000,2000);
			}
	}
}
