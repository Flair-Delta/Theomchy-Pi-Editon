package septagram.Theomachy.Ability.GOD;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Hades extends Ability
{	
	private final int coolTime1=100;
	private final int coolTime2=300;
	private final int material=4;
	private final int stack1=5;
	private final int stack2=10;
	private final static String[] des= {
				"죽음의 신입니다.",
			   "패시브 능력으로 사망시 70% 확률로 아이템을 잃지 않습니다." ,
			   "일반능력은 주변 2칸에 있는 자신을 포함한 ",
			   "모든 플레이어와 함께 나락으로 떨어집니다.",
			   "고급능력은 주변 4칸에 있는 자신을 제외한 ",
			   "모든 플레이어를 나락으로 떨어뜨립니다."};
	
	public Hades(String playerName)
	{
		super(playerName,"하데스", 3, true, false, false, des);
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
			Skill.Use(player, material, stack1, 1,coolTime1);
			Entity entity=player;
			Location location = player.getLocation();
			location.setY(-2.0d);
			List<Entity> entitylist = entity.getNearbyEntities(2, 2, 2);
			for (Entity e : entitylist)
			{
				if (e instanceof LivingEntity)
				{
					e.teleport(location);
					if (e.getType() == EntityType.PLAYER)
						((Player)e).sendMessage("죽음의 신의 능력에 의해 나락으로 떨어집니다.");
				}
			}
			player.teleport(location);
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, material, stack2))
		{
			Skill.Use(player, material, stack2, 2,coolTime2);
			Entity entity=player;
			Location location = player.getLocation();
			location.setY(-2.0d);
			List<Entity> entitylist = entity.getNearbyEntities(4, 4, 4);
			for (Entity e : entitylist)
			{
				if (e instanceof LivingEntity)
				{
					e.teleport(location);
					if (e.getType() == EntityType.PLAYER)
						((Player)e).sendMessage(ChatColor.RED+"죽음의 신의 능력에 의해 나락으로 떨어집니다.");
				}
			}
		}
	}
	
	private ItemStack[] inventory;
	private ItemStack[] armor;
	public void T_Passive(PlayerDeathEvent event)
	{
		Random r=new Random();
		if (r.nextInt(10) <=5)
		{
			this.inventory=event.getEntity().getInventory().getContents();
			this.armor = event.getEntity().getInventory().getArmorContents();
			event.getDrops().clear();
		}
		else
			event.getEntity().sendMessage("아이템을 모두 잃었습니다.");
	}
	public void T_Passive(PlayerRespawnEvent event)
	{
		Player player = event.getPlayer();
		if (inventory !=null)
			player.getInventory().setContents(inventory);
		if (armor !=null)
		player.getInventory().setArmorContents(armor);
		inventory = null;
		armor = null;
	}
}

