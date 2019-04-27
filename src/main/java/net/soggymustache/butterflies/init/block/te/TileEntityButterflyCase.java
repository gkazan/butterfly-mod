package net.soggymustache.butterflies.init.block.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityButterflyCase extends TileEntity implements ITickable{
	
	private String name = "";
	private byte EColor;
	private float red, green, blue, rarity, extra;
	public int ticks = 0;
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("Name", name == null ? "" : name);
		compound.setByte("EColor", EColor);
		compound.setFloat("Red", red);
		compound.setFloat("Green", green);
		compound.setFloat("Blue", blue);
		compound.setFloat("Rarity", rarity);
		compound.setFloat("Extra", extra);
		return compound;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		name = compound.getString("Name");
		EColor = compound.getByte("EColor");
		red = compound.getFloat("Red");
		green = compound.getFloat("Green");
		blue = compound.getFloat("Blue");
		rarity = compound.getFloat("Rarity");
		extra = compound.getFloat("Extra");
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() 
	{
		return new SPacketUpdateTileEntity(pos, getBlockMetadata(), this.writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public NBTTagCompound getUpdateTag() 
	{
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void update() {
		ticks++;
		if(ticks == Integer.MAX_VALUE - 1) {
			ticks = 0;
		}
	}

}
