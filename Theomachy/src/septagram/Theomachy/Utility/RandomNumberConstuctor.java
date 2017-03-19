package septagram.Theomachy.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.DB.AbilityData;
import septagram.Theomachy.Handler.CommandModule.Blacklist;

public class RandomNumberConstuctor
{
	public static int[] nonDuplicate()
	{
		
		List<Integer> Canlist=new ArrayList<Integer>();
		Random random=new Random();
		
		for(int e=0;e<AbilityData.GOD_ABILITY_NUMBER;e++){
			if(!Blacklist.Blacklist.contains(e+1)) Canlist.add(e+1);
		} int a=101;
		for(int e=0;e<AbilityData.HUMAN_ABILITY_NUMBER;e++) { 
			if(!Blacklist.Blacklist.contains(a)) Canlist.add(a); a++;
		}
		
		Blacklist.Canlist=Canlist.size();
		
		int[] rn;
		if(!Theomachy.RANDOMUP)
			rn=new int[Canlist.size()];
		else
			rn=new int[Canlist.size()*2];
		
		Object[] rn1= Canlist.toArray();
		
		for(int i=0;i<rn1.length;i++) {
			rn[i]=(Integer) rn1[i];
		}
		
		if(Theomachy.RANDOMUP){
			for(int i=rn1.length;i<rn.length;i++){
				rn[i]=(Integer) rn1[i-rn1.length];
			}
		}
		
		for(int i=0; i<rn.length; i++)//섞
		{
			int r=random.nextInt(rn.length-1);
			int temp=rn[i];
			rn[i]=rn[r];
			rn[r]=temp;
		}
		StringBuilder sb = new StringBuilder();
		for (int num : rn)
			sb.append(num).append(" ");
		Theomachy.log.info(String.valueOf(sb));
		return rn;
	}
}
