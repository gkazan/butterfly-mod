package net.soggymustache.butterflies.entity;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.soggymustache.butterflies.ButterfliesMain;
import net.soggymustache.butterflies.init.items.ButterflyItems;
import net.soggymustache.butterflies.util.NameUtilities;

public class EntityButterfly extends EntityAnimal {
	
	private static final DataParameter<String> NAME = EntityDataManager.<String>createKey(EntityButterfly.class, DataSerializers.STRING);
	private static final DataParameter<Byte> LANDED = EntityDataManager.<Byte>createKey(EntityButterfly.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> ECOLOR = EntityDataManager.<Byte>createKey(EntityButterfly.class, DataSerializers.BYTE);
	private static final DataParameter<Float> RED = EntityDataManager.<Float>createKey(EntityButterfly.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> GREEN = EntityDataManager.<Float>createKey(EntityButterfly.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> BLUE = EntityDataManager.<Float>createKey(EntityButterfly.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> RARITY = EntityDataManager.<Float>createKey(EntityButterfly.class, DataSerializers.FLOAT);
	private static final DataParameter<Integer> EXTRA = EntityDataManager.<Integer>createKey(EntityButterfly.class, DataSerializers.VARINT);
	private BlockPos spawnPosition;
	private int stopped;

	public EntityButterfly(World worldIn) {
		super(worldIn);
		this.setSize(0.3F, 0.3F);
		this.setIsButterflyLanded(false);
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.setEColor((byte) this.getRNG().nextInt(EnumDyeColor.values().length));
		EnumDyeColor color = EnumDyeColor.values()[this.getEColorB()];

		if (color == EnumDyeColor.BLACK) {
			this.setEColor((byte) this.getRNG().nextInt(EnumDyeColor.values().length));
			color = EnumDyeColor.values()[this.getEColorB()];
		}

		NameUtilities.generateStats(this, color);
		
//		System.out.println(this.getExtra());
		
		Entity ent = this.world.getClosestPlayerToEntity(this, 5.0D);
		this.getEntityData().setTag("Other", ent.writeToNBT(new NBTTagCompound()));
		
		return super.onInitialSpawn(difficulty, livingdata);
	}

	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(ECOLOR, Byte.valueOf((byte) 0));
		this.dataManager.register(LANDED, Byte.valueOf((byte) 0));
		this.dataManager.register(NAME, "");
		this.dataManager.register(RED, 0.0F);
		this.dataManager.register(GREEN, 0.0F);
		this.dataManager.register(BLUE, 0.0F);
		this.dataManager.register(RARITY, 0.0F);
		this.dataManager.register(EXTRA, 0);
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);

		if (stack == null || stack.isEmpty()) {
			return false;
		}

		if (stack.getItem() == ButterflyItems.LEPIDOPTEROLOGY_BOOK) {
			if (player.world.isRemote)
				player.openGui(ButterfliesMain.instance, 0, player.world, (int) Math.floor(this.posX), (int) this.posY,
						(int) Math.floor(this.posZ));
			return true;
		}
		return super.processInteract(player, hand);
	}

	public boolean canBePushed() {
		return false;
	}

	protected void collideWithEntity(Entity entityIn) {
	}

	protected void collideWithNearbyEntities() {
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
	}

	public boolean getIsButterflyLanded() {
		return (((Byte) this.dataManager.get(LANDED)).byteValue() & 1) != 0;
	}

	public void setIsButterflyLanded(boolean isLanded) {
		byte b0 = ((Byte) this.dataManager.get(LANDED)).byteValue();

		this.dataManager.set(LANDED, Byte.valueOf(isLanded ? (byte) (b0 | 1) : (byte) (b0 & -2)));
	}

	public EnumDyeColor getEColor() {
		return EnumDyeColor.values()[this.dataManager.get(ECOLOR)];
	}

	public byte getEColorB() {
		return this.dataManager.get(ECOLOR);
	}

	public void setEColor(byte b) {
		this.dataManager.set(ECOLOR, Byte.valueOf(b));
	}

	public int getExtra() {
		return this.dataManager.get(EXTRA);
	}

	public void setExtra(int nm) {
		this.dataManager.set(EXTRA, nm);
	}

	public String getBName() {
		return this.dataManager.get(NAME);
	}

	public void setBName(String nm) {
		this.dataManager.set(NAME, nm);
	}

	public float getRarity() {
		return this.dataManager.get(RARITY);
	}

	public void setRarity(float nm) {
		this.dataManager.set(RARITY, nm);
	}

	public float getRed() {
		return this.dataManager.get(RED);
	}

	public void setRed(float nm) {
		this.dataManager.set(RED, nm);
	}

	public float getGreen() {
		return this.dataManager.get(GREEN);
	}

	public void setGreen(float nm) {
		this.dataManager.set(GREEN, nm);
	}

	public float getBlue() {
		return this.dataManager.get(BLUE);
	}

	public void setBlue(float nm) {
		this.dataManager.set(BLUE, nm);
	}

	public void onUpdate() {
		super.onUpdate();

		if (!this.isInWater()) {
			if (this.getIsButterflyLanded()) {
				this.motionX = 0.0D;
				this.motionY = 0.0D;
				this.motionZ = 0.0D;
				this.posY = (double) MathHelper.floor(this.posY) + 0.4D - (double) this.height;
			} else {
				this.motionY *= 0.6000000238418579D;
			}
		}

	}

	@Override
	protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
		super.dropFewItems(wasRecentlyHit, lootingModifier);

		ItemStack stack = new ItemStack(ButterflyItems.BUTTERFLY);
		stack.setTagCompound(new NBTTagCompound());

		stack.getTagCompound().setString("Name", this.getBName());
		stack.getTagCompound().setByte("EColor", this.getEColorB());
		stack.getTagCompound().setFloat("Red", this.getRed());
		stack.getTagCompound().setFloat("Green", this.getGreen());
		stack.getTagCompound().setFloat("Blue", this.getBlue());
		stack.getTagCompound().setFloat("Rarity", this.getRarity());
		stack.getTagCompound().setFloat("Extra", this.getExtra());

		this.entityDropItem(stack, 0);
	}

	protected void updateAITasks() {
		super.updateAITasks();
		BlockPos blockpos = new BlockPos(this);
		BlockPos blockpos1 = blockpos.down();

		if (!this.isInWater()) {
			if (this.getIsButterflyLanded()) {
				stopped++;
				if (this.world.getBlockState(blockpos1).isNormalCube()) {
					if (this.rand.nextInt(200) == 0) {
						this.rotationYawHead = (float) this.rand.nextInt(360);
					}

					if (this.world.getNearestPlayerNotCreative(this, 4.0D) != null) {
						this.setIsButterflyLanded(false);
						this.world.playEvent((EntityPlayer) null, 1025, blockpos, 0);
					}
				} else {
					this.setIsButterflyLanded(false);
					this.world.playEvent((EntityPlayer) null, 1025, blockpos, 0);
				}
			} else {
				if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
					this.spawnPosition = null;
				}

				if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((double) ((int) this.posX), (double) ((int) this.posY), (double) ((int) this.posZ)) < 4.0D) {
					this.spawnPosition = new BlockPos((int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7),
							(int) this.posY + this.rand.nextInt(6) - 2,
							(int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
				}

				double d0 = (double) this.spawnPosition.getX() + 0.5D - this.posX;
				double d1 = (double) this.spawnPosition.getY() + 0.1D - this.posY;
				double d2 = (double) this.spawnPosition.getZ() + 0.5D - this.posZ;
				this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
				this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
				this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
				float f = (float) (MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
				float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
				this.moveForward = 0.5F;
				this.rotationYaw += f1;

				if (this.world.handleMaterialAcceleration(
						this.getEntityBoundingBox().grow(0.0D, -4.0D, 0.0D).shrink(0.001D), Material.WATER, this)) {
					this.motionY = 0.1F;
				}

				if (this.rand.nextInt(100) == 0 && this.world.getBlockState(blockpos1).isNormalCube()) {
					this.setIsButterflyLanded(true);
				}
			}
			if (stopped >= 100 && !this.isOverWater()) {
				this.setIsButterflyLanded(false);
				stopped = 0;
			}
		}
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	public void fall(float distance, float damageMultiplier) {
	}

	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
	}

	public boolean doesEntityNotTriggerPressurePlate() {
		return true;
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.dataManager.set(LANDED, Byte.valueOf(compound.getByte("ButterflyFlags")));
		this.dataManager.set(NAME, compound.getString("Name"));
		this.dataManager.set(ECOLOR, Byte.valueOf(compound.getByte("BColor")));
		this.dataManager.set(RARITY, compound.getFloat("Rarity"));
		this.dataManager.set(RED, compound.getFloat("Red"));
		this.dataManager.set(GREEN, compound.getFloat("Green"));
		this.dataManager.set(BLUE, compound.getFloat("Blue"));
		this.dataManager.set(EXTRA, compound.getInteger("Extra"));
	}

	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setByte("ButterflyFlags", ((Byte) this.dataManager.get(LANDED)).byteValue());
		compound.setString("Name", this.dataManager.get(NAME));
		compound.setByte("BColor", ((Byte) this.dataManager.get(ECOLOR)).byteValue());
		compound.setFloat("Rarity", this.dataManager.get(RARITY));
		compound.setFloat("Red", this.dataManager.get(RED));
		compound.setFloat("Green", this.dataManager.get(GREEN));
		compound.setFloat("Blue", this.dataManager.get(BLUE));
		compound.setInteger("Extra", this.dataManager.get(EXTRA));
	}

	public float getEyeHeight() {
		return this.height / 2.0F;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}
}