package septagram.Theomachy.Ability.GOD;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.DB.GameData;
import septagram.Theomachy.Manager.EventManager;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Athena extends Ability
{
	private final int coolTime1=10;
	private final int coolTime2=3;
	private final int material=4;
	private final int stack1=5;
	private final int stack2=64;
	private int abilityLimitCounter=2;
	private final static String[] des= {"지혜의 신입니다.",
			   "플레이어들이 사망할 때 마다 경험치를 얻습니다.",
			   "자신이 사망하면 경험치는 초기화됩니다.",
			   "일반능력으로 책을 얻을 수 있으며",
			   "고급능력으로 게임 당 2번 인챈트 테이블을 얻을 수 있습니다."};
	
	public Athena(String playerName)
	{
		super(playerName,"아테나", 5, true, true, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.cool1=coolTime1;
		this.cool2=coolTime2;
		this.sta1=stack1;
		this.sta2=stack2;
		
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
			player.getInventory().addItem(new ItemStack(Material.BOOK.getId(),3));
		}
	}
	
	private void rightAction(Player player)
	{
		if (abilityLimitCounter>0)
		{
			if (CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, material, stack2))
			{
				if (abilityLimitCounter>1)
				{
					Skill.Use(player, material, stack2, 2, coolTime2);
					player.getInventory().addItem(new ItemStack(Material.ENCHANTMENT_TABLE.getId(),1));
					player.sendMessage("남은 교환 횟수 : "+--abilityLimitCounter);
				}
				else
				{
					Skill.Use(player, material, stack2, 2, 0);
					player.getInventory().addItem(new ItemStack(Material.ENCHANTMENT_TABLE.getId(),1));
					player.sendMessage("남은 교환 횟수 : "+--abilityLimitCounter);
				}
			}
		}
		else
			player.sendMessage("이 능력은 더이상 사용할 수 없습니다.");
	}
	
	public void T_Passive(PlayerDeathEvent event)
	{
		if (event.getEntity().getLastDamageCause().getCause() != DamageCause.SUICIDE)
		{
			Player player = GameData.OnlinePlayer.get(playerName);
			player.setLevel(player.getLevel()+1);
		}
	}
	
	public void conditionSet()
	{
		EventManager.PlayerDeathEventList.add(this);//나중에 콘디셧셋으로 바꾸기
	}
	public void conditionReSet()
	{
		EventManager.PlayerDeathEventList.remove(this);//나중에 콘디션 리셋으로 바꾸기
	}
}

