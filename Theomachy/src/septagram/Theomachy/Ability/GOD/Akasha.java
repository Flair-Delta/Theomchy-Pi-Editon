package septagram.Theomachy.Ability.GOD;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.GetPlayerList;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Akasha extends Ability{

	private final static String[] des= {"고통과 향락의 여신입니다.",
			"일반 능력으로 주변 20칸 내에 있는 아군에게 향락을",
			"선사하여 걸음을 빨리하고 체력을 재생합니다.",
			"고급 능력으로 주변 10칸 내에 있는 적군에게 멀미를,",
			"주고, 체력을 닳게 합니다."};
	
	public Akasha(String playerName)
	{
		super(playerName,"아카샤", 17, true, false, false ,des);
		Theomachy.log.info(playerName+abilityName);
		
		
		this.cool1=60;
		this.sta1=10;
		this.cool2=120;
		this.sta2=20;
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

	private void leftAction(Player player) {
		
		if(CoolTimeChecker.Check(player, 1)&&PlayerInventory.ItemCheck(player, 4, sta1)){
			
			Skill.Use(player, 4, sta1, 1, cool1);
			
			List<Player> nearp=GetPlayerList.getNearByTeamMembers(player, 20, 20, 20);
			
			for(Player p:nearp){
				p.sendMessage(ChatColor.DARK_PURPLE+"향락"+ChatColor.WHITE+"이 당신을 즐겁게합니다!");
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*15, 0));
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,20*15, 0));
			}
		}
		
	}
	
	private void rightAction(Player player) {
		
		if(CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, 4, sta2)){
			
			List<Player> entityList = GetPlayerList.getNearByNotTeamMembers(player, 10, 10, 10);
			
			for(Player e:entityList){
				e.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,20*6, 0));
				e.setHealth(e.getHealth()-4);
			}
			
		}
	}
	
}
