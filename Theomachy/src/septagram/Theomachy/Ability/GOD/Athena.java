package septagram.Theomachy.Ability.GOD;

import org.bukkit.ChatColor;
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
	private int abilityLimitCounter=2;
	private final static String[] des= {
			   "아테나는 지혜의 신입니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"지식",
			   "책을 얻습니다.",
			   ChatColor.RED+"【고급】 "+ChatColor.WHITE+"강화",
			   "게임 당 2번만 인챈트 테이블을 얻습니다.",
			   ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"지혜",
			   "다른 사람이 죽으면 경험치를 얻습니다. 자신이 죽으면 경험치가 사라집니다."};
	
	public Athena(String playerName)
	{
		super(playerName,"아테나", 5, true, true, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.cool1=10;
		this.cool2=3;
		this.sta1=5;
		this.sta2=64;
		
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
		if (CoolTimeChecker.Check(player, 1)&&PlayerInventory.ItemCheck(player, 4, sta1))
		{
			Skill.Use(player, 4, sta1, 1, cool1);
			player.getInventory().addItem(new ItemStack(Material.BOOK.getId(),3));
		}
	}
	
	private void rightAction(Player player)
	{
		if (abilityLimitCounter>0)
		{
			if (CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, 4, sta2))
			{
				if (abilityLimitCounter>1)
				{
					Skill.Use(player, 4, sta2, 2, cool2);
					player.getInventory().addItem(new ItemStack(Material.ENCHANTMENT_TABLE.getId(),1));
					player.sendMessage("남은 교환 횟수 : "+--abilityLimitCounter);
				}
				else
				{
					Skill.Use(player, 4, sta2, 2, 0);
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

