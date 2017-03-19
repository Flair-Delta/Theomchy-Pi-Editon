package septagram.Theomachy.Ability.GOD;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;
import septagram.Theomachy.Utility.GetPlayerList;

public class Asclepius extends Ability
{
	private final int coolTime1=60;
	private final int coolTime2=120;
	private final int material=4;
	private final int stack1=1;
	private final int stack2=5;
	private final static String[] des= {"의술의 신입니다.",
			   "일반능력으로 자신을 모두 회복 시킬 수 있습니다." ,
			   "고급능력으로 주변 5칸에 있는 자신을 제외한 팀원의 체력을",
			   "모두 회복시킬 수 있습니다."};
	
	public Asclepius(String playerName)
	{
		super(playerName,"아스클리피어스", 10, true, false, false, des);
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
			player.setHealth(20);
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, material, stack2))
		{
			List<Player> targetList = GetPlayerList.getNearByTeamMembers(player, 5, 5, 5);
			if (!targetList.isEmpty())
			{
				Skill.Use(player, material, stack2, 2, coolTime2);
				player.sendMessage("자신을 제외한 모든 팀원의 체력을 회복합니다.");
				player.sendMessage(ChatColor.GREEN+"체력을 회복한 플레이어 목록");
				for (Player e : targetList)
				{
					e.setHealth(20);
					e.sendMessage(ChatColor.YELLOW+"의술의 신의 능력으로 모든 체력을 회복합니다.");
					player.sendMessage(ChatColor.GOLD+e.getName());
				}
			}
			else
				player.sendMessage("사용 가능한 대상이 없습니다.");
		}
	}
}
