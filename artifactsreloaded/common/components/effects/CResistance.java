package archived.artifactsreloaded.common.components.effects;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import archived.artifactsreloaded.common.components.BaseComponent;


public class CResistance extends BaseComponent {

}
@Override
public String getRandomTrigger(Random rand, boolean isArmor) {
	if(isArmor) {
		return "onArmorTickUpdate";
	}
	String str = "";
	switch(rand.nextInt(3)) {
		case 0:
			str = "onItemRightClick";
			break;
		case 1:
			str = "hitEntity";
			break;
		case 2:
			str = "onUpdate";
			break;
	}
	return str;
}

@Override
public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
	UtilsForComponents.sendPotionPacket(11, 600, 0, player);
	UtilsForComponents.sendItemDamagePacket(player, player.inventory.currentItem, 1);
	itemStack.stackTagCompound.setInteger("onItemRightClickDelay", 200);
	return itemStack;
}

@Override
public boolean hitEntity(ItemStack itemStack, EntityLivingBase entityLivingHit, EntityLivingBase entityLivingPlayer) {
	if(!entityLivingPlayer.worldObj.isRemote) {
		entityLivingPlayer.addPotionEffect(new PotionEffect(11, 100, 0));
	}
	return false;
}

//works great
@Override
public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean held) {
	//Check that the artifact is in a baubles slot if it should be
	if(DragonArtifacts.baublesLoaded && stack.stackTagCompound != null &&
						UtilsForComponents.equipableByBaubles(stack.stackTagCompound.getString("iconName")) &&
						DragonArtifacts.baublesMustBeEquipped && slot >= 0) {
		return;
	}

	if(!world.isRemote) {
		if(entity instanceof EntityLivingBase) {
			EntityLivingBase ent = (EntityLivingBase) entity;
			ent.addPotionEffect(new PotionEffect(11, 10, 0));
		}
	}
}

public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, String trigger, boolean advTooltip) {
	if(trigger == "passively.") {
		par3List.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal("effect.Damage Resistance"));
	}
	else {
		int time = 0;
		if(trigger == "when inflicting damage.") {
			time = 5;
		}
		else if(trigger == "when used.") {
			time = 30;
		}
		par3List.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal("effect.Damage Resistance"));
		par3List.add("  " + EnumChatFormatting.AQUA + StatCollector.translateToLocal("tool."+trigger) + " (" + time + " "  + StatCollector.translateToLocal("time.seconds") + ")");
	}
}

@Override
public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean advTooltip) {
	par3List.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal("effect.Damage Resistance"));
}

@Override
public String getPreAdj(Random rand) {
	String str = "";
	switch(rand.nextInt(3)) {
		case 0:
			str = "Armored";
			break;
		case 1:
			str = "Shielding";
			break;
		case 2:
			str = "Guarding";
			break;
	}
	return str;
}

@Override
public String getPostAdj(Random rand) {
	String str = "";
	switch(rand.nextInt(2)) {
		case 0:
			str = "of Protection";
			break;
		case 1:
			str = "of Armor";
			break;
	}
	return str;
}

@Override
public int getTextureBitflags() {
	return Flags.AMULET | Flags.FIGURINE | Flags.RING | Flags.STAFF | Flags.TRINKET | Flags.ARMOR | Flags.CHESTPLATE;
}

@Override
public int getNegTextureBitflags() {
	return Flags.DAGGER | Flags.SWORD;
}

@Override
public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack, boolean worn) {
	if(worn)
		onUpdate(itemStack, world, player, 0, true);
}
}

