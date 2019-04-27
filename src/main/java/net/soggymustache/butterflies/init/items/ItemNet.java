package net.soggymustache.butterflies.init.items;

import java.util.List;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.soggymustache.butterflies.entity.EntityButterfly;

public class ItemNet extends ItemB{

	public ItemNet(String unloc) {
		super(unloc);
		this.setMaxStackSize(1);
		this.setMaxDamage(2);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(stack.getTagCompound() != null)
			tooltip.add(TextFormatting.GREEN + "Contains " + stack.getTagCompound().getString("DName"));
		else
			tooltip.add(TextFormatting.RED + "Empty");
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if(entity instanceof EntityButterfly) {
	        if (stack.getTagCompound() == null && EntityList.getEntityString(entity) != null)
	        {
	        	stack.setTagCompound(new NBTTagCompound());
	        	NBTTagCompound tags = new NBTTagCompound();
	        	entity.writeToNBT(tags);
	        	stack.getTagCompound().setTag("Data", tags);

	        	for(Entry<ResourceLocation, EntityEntry> f : ForgeRegistries.ENTITIES.getEntries()) {
	        		if(f.getValue().getEntityClass() == entity.getClass()) {
	        			stack.getTagCompound().setString("Entity", String.valueOf(f.getKey()));
	        		}
	        	}
	        	
	        	stack.getTagCompound().setString("Name", entity.getName());
	        	stack.getTagCompound().setString("DName", ((EntityButterfly)entity).getBName());
	        	
	        	if(!player.world.isRemote) {
	        		try {
	        			player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Caught " + ((EntityButterfly)entity).getBName() + "!"));
	        		}catch(Exception e) {
	        			player.sendMessage(new TextComponentString("Caught " + entity.getName() + "!"));
	        		}
	        	}
	        	
	        	EnumParticleTypes enumparticletypes = EnumParticleTypes.CLOUD;
	    		
        		for (int i = 0; i < 7; ++i) {
        			double d0 = player.getRNG().nextGaussian() * 0.02D;
        			double d1 = player.getRNG().nextGaussian() * 0.02D;
        			double d2 =player.getRNG().nextGaussian() * 0.02D;
        			entity.world.spawnParticle(enumparticletypes,
        					entity.posX + (double) (player.getRNG().nextFloat() * entity.width * 2.0F) - (double) entity.width,
        					entity.posY + 0.5D + (double) (player.getRNG().nextFloat() * entity.height),
        					entity.posZ + (double) (player.getRNG().nextFloat() * entity.width * 2.0F) - (double) entity.width, d0, d1, d2,
        					new int[0]);
        		}
	        	
        		entity.setDead();
	        	return true;
	        }
		}
		return super.onLeftClickEntity(stack, player, entity);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.inventory.getCurrentItem();
		Block block = worldIn.getBlockState(pos).getBlock();

		if(stack.getTagCompound() != null) {
			try {
				Entity entity2 = EntityList.createEntityByIDFromName(new ResourceLocation(stack.getTagCompound().getString("Entity")), worldIn);
				
				entity2.readFromNBT((NBTTagCompound) stack.getTagCompound().getTag("Data"));

				entity2.setPosition(pos.getX(), pos.getY()+1, pos.getZ());
				
				if(!worldIn.isRemote)
					worldIn.spawnEntity(entity2);
			}catch(Exception es) {
				System.err.println("Entity cannot be created!");
			}
			stack.setTagCompound(null);
			stack.damageItem(1, player);
		}else {
			if(!worldIn.isRemote)
				player.sendMessage(new TextComponentString(TextFormatting.RED + "The net is empty"));
		}
		
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
}
