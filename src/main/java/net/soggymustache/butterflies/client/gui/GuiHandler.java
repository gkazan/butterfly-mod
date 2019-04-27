package net.soggymustache.butterflies.client.gui;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.soggymustache.butterflies.ButterfliesMain;
import net.soggymustache.butterflies.entity.EntityButterfly;

public class GuiHandler implements IGuiHandler {
	
	public static void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(ButterfliesMain.instance, new GuiHandler());
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile_entity = world.getTileEntity(new BlockPos(x, y, z));
		if(ID == 0) {
			EntityButterfly entity = getEntityAt(player, x, y, z);
			if (entity != null)
				return new GuiLepidopterologyManual(entity);
		}
		if(ID == 1) {
			return new GuiLepidopterologyManual(tile_entity.getTileData());
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile_entity = world.getTileEntity(new BlockPos(x, y, z));
		if(ID == 0) {
			EntityButterfly entity = getEntityAt(player, x, y, z);
			if (entity != null)
				return new GuiLepidopterologyManual(entity);
		}
		if(ID == 1) {
			return new GuiLepidopterologyManual(tile_entity.getTileData());
		}
		return null;
	}
	
	private EntityButterfly getEntityAt(EntityPlayer player, int x, int y, int z)
	{
		AxisAlignedBB targetBox = new AxisAlignedBB(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1);
		List entities = player.world.getEntitiesWithinAABBExcludingEntity(player, targetBox);
		Iterator iterator = entities.iterator();
		EntityButterfly best = null;
		while (iterator.hasNext()) {
			Entity entity = (Entity) iterator.next();
			if (entity instanceof EntityButterfly) {
				best = (EntityButterfly) entity;
			}
		}
		return best;
	}
}