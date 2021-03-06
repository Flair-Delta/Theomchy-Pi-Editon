package septagram.Theomachy.Ability.GOD;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;

public class Dionysus extends Ability
{	
	private final static String[] des= {
			   "디오니소스는 술의 신입니다.",
			   ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"알코올 중독",
			   "10% 확률로 자신을 공격한 10초간 상대의 시야를 어지럽히는 동시에",
			   "상대의 이동 속도, 공격력을 낮춥니다."};
	public Dionysus(String playerName)
	{
		super(playerName,"디오니소스", 12, false, true, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.rank=3;
	}
	
	public void T_Passive(EntityDamageByEntityEvent event)
	{
		Player player = (Player)event.getEntity();
		if (player.getName().equals(playerName))
		{
			Random random = new Random();
			int rn = random.nextInt(10);
			if (rn == 0)
			{
				Player target = (Player) event.getDamager();
				target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,200,0), true);
				target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,200,0), true);
				target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,240,0), true);
				target.sendMessage("술에 취해서 정신이 없습니다!");
				player.sendMessage("상대방에게 술을 먹였습니다.");
			}
		}
	}
}
