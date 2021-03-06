package septagram.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Archer extends Ability
{
	private final static String[] des= {
			   "궁수입니다.",
			   ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"정확함",
			   "활 공격 데미지가 1.4배로 상승합니다.",
			   ChatColor.AQUA+"【일반/고급】 "+ChatColor.WHITE+"화살/활 생성",
			   "일반능력으로 화살을, 고급 능력으로 활을 만듭니다."};
	
	public Archer(String playerName)
	{
		super(playerName,"아처", 101, true, true, false, des);
		Theomachy.log.info(playerName+abilityName);
		this.cool1=20;
		this.cool2=60;
		this.sta1=7;
		this.sta2=15;
		
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
		if (CoolTimeChecker.Check(player, 1)&&PlayerInventory.ItemCheck(player, 4, sta1))
		{
			Skill.Use(player, 4, sta1, 1, cool1);
			World world = player.getWorld();
			Location location = player.getLocation();
			world.dropItem(location, new ItemStack(Material.ARROW.getId(), 1));
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, 4, sta2))
		{
			Skill.Use(player, 4, sta2, 2, cool2);
			World world = player.getWorld();
			Location location = player.getLocation();
			world.dropItem(location, new ItemStack(Material.BOW.getId(), 1));
		}
	}
	
	public void T_Passive(EntityDamageByEntityEvent event)
	{
		int damage = (int)(event.getDamage());
		event.setDamage((int) (damage*1.4));
	}
}
