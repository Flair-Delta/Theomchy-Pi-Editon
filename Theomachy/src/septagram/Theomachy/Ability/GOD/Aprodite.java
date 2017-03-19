package septagram.Theomachy.Ability.GOD;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.GetPlayerList;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Aprodite extends Ability{
	
	private final static String[] des= {"미의 신입니다.",
			   "가까이 있는 사람들을 당신의 미로 끌어올 수 있습니다.",
			   "일반능력으로 주변 20칸 이내의 다른 팀의 ",
			   "사람들을 자신의 위치로 끌어옵니다. ",
			   "단, 자신이 블럭에 서 있어야 하고, 웅크리고 ",
			   "있으면 발동되지 않습니다."};
	private final int coolTime0=500;
	private final int stack0=64;
	
	public Aprodite(String playerName)
	{
		super(playerName, "아프로디테", 13, true, false, false, des);
		Theomachy.log.info(playerName+"아프로디테");
		
		this.cool1=coolTime0;
		this.sta1=stack0;
		
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

	private void leftAction(Player player) {
		if (CoolTimeChecker.Check(player, 1)&&PlayerInventory.ItemCheck(player, 4, stack0)) {
			if(!player.isSneaking() && !player.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.AIR)) {
				Skill.Use(player, 4, stack0, 0, coolTime0);
				try {
					List<Player> list=GetPlayerList.getNearByNotTeamMembers(player, 20, 20, 20);
				
					for(Player e:list) {
						e.teleport(player);
						e.sendMessage(ChatColor.YELLOW+"미의 여신에게 이끌려갑니다!");
					}
				}catch(Exception e) {}
				
				player.sendMessage("미로 다른 사람들을 홀렸습니다.");
			}else {
				player.sendMessage(ChatColor.RED+"웅크리고 있거나 발 밑의 블록이 없어 능력이 발동되지 않았습니다.");
			}
		}

	}
	
}
