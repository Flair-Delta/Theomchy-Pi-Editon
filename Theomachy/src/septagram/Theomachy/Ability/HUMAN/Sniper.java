package septagram.Theomachy.Ability.HUMAN;

import java.util.Timer;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.DB.GameData;
import septagram.Theomachy.Timer.Skill.SnipingDuration;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Sniper extends Ability
{
	private final int coolTime0=70;
	private final int material=4;
	private final int stack0=1;
	public boolean ready = false;
	public boolean sniping = false;
	private final static String[] des= {"빠른 화살을 이용해 상대방을 공격하는 능력입니다.",
			   "게임 시작시 활 1개 화살 20개를 지급합니다. " ,
			   "활을 들고 앉은 채(shift) 좌클릭하면 4초뒤 스나이핑 모드가 활성화됩니다." ,
			   "스나이핑 모드일시 쏜 화살이 타겟방향으로 보이지 않는 속도로",
			   "날아가며 맞은 적은 약 100~200의 데미지를 입습니다."};
	
	public Sniper(String playerName)
	{
		super(playerName, "저격수", 118, true, false, false, des);
		this.cool1=coolTime0;
		this.sta1=stack0;
		
		this.rank=3;
	}
	
	public void T_Active(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BOW.getId()))
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
		if (player.isSneaking() && !ready)
		{
			ready=true;
			Timer t = new Timer();
			t.schedule(new SnipingDuration(player, this),0,1000);
		}
	}
	
	@Override
	public void T_Passive(ProjectileLaunchEvent event, Player player)
	{
		if (this.sniping && (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, material, stack0)))
		{
			Entity entity = event.getEntity();
			if (entity instanceof Arrow)
			{
				Skill.Use(player, material, stack0, 0, coolTime0);
				entity.remove();
				@SuppressWarnings("deprecation")
				Arrow arrow = player.shootArrow();
				arrow.setVelocity(player.getEyeLocation().getDirection().multiply(100));
			}
		}
	}
	
	@Override
	public void conditionSet()
	{
		Player player = GameData.OnlinePlayer.get(this.playerName);
		player.getInventory().addItem(new ItemStack(Material.BOW.getId(), 1));
		player.getInventory().addItem(new ItemStack(Material.ARROW.getId(), 10));
	}
}
