package net.soggymustache.butterflies.init.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.soggymustache.butterflies.ButterfliesMain;
import net.soggymustache.butterflies.init.block.te.TileEntityButterflyCase;
import net.soggymustache.butterflies.init.items.ButterflyItems;

public class BlockButterflyCase extends Block implements ITileEntityProvider {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
		
    protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 1.0D, 1.0D, 1.0D, 0.8D);
    protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.8D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.2D);
    protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.2D, 1.0D, 1.0D);
	
    public BlockButterflyCase(String name, Material materialIn) {
		super(materialIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabs.MISC);
		
		ButterflyBlocks.BLOCKS.add(this);
		ButterflyItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
    
    @Override
    public boolean hasTileEntity() {
    	return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
    	return new TileEntityButterflyCase(); 
    }
    
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
    	return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState state)
    {
        return true;
    }
    
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch ((EnumFacing)state.getValue(FACING))
        {
            case EAST:
                return EAST_AABB;
            case WEST:
                return WEST_AABB;
            case SOUTH:
                return SOUTH_AABB;
            default:
            case NORTH:
                return NORTH_AABB;
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    	return NULL_AABB;
    }
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity te = worldIn.getTileEntity(pos);
		ItemStack stack = playerIn.inventory.getCurrentItem();
		
		if(stack != null && stack.getItem() == ButterflyItems.BUTTERFLY) {
			if(stack.getTagCompound() != null) {
				NBTTagCompound nbt = (NBTTagCompound) stack.getTagCompound();
				NBTTagCompound data = worldIn.getTileEntity(pos).getTileData();
				
				if(data.hasKey("Name"))
					return false;
				
				data.setString("Name", nbt.getString("Name"));
				data.setByte("EColor", nbt.getByte("EColor"));
				data.setFloat("Red", nbt.getFloat("Red"));
				data.setFloat("Green", nbt.getFloat("Green"));
				data.setFloat("Blue", nbt.getFloat("Blue"));
				data.setFloat("Rarity", nbt.getFloat("Rarity"));
				data.setFloat("Extra", nbt.getFloat("Extra"));
				stack.shrink(1);
				te.markDirty();
			}
		}
		else if(stack != null && stack.getItem() == ButterflyItems.LEPIDOPTEROLOGY_BOOK) {
			if(te.getTileData().hasKey("Name")) {
				if (playerIn.world.isRemote)
					playerIn.openGui(ButterfliesMain.instance, 1, playerIn.world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    	TileEntity te = worldIn.getTileEntity(pos);
    	super.onBlockAdded(worldIn, pos, state);
    }
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity te = world.getTileEntity(pos);
		
		if(te.getTileData().hasKey("Name")) {
	    	ItemStack stack = new ItemStack(ButterflyItems.BUTTERFLY);
	    	NBTTagCompound comp = new NBTTagCompound();
	    	
	    	comp.setString("Name", te.getTileData().getString("Name"));
	    	comp.setByte("EColor", te.getTileData().getByte("EColor"));
	    	comp.setFloat("Red", te.getTileData().getFloat("Red"));
	    	comp.setFloat("Green", te.getTileData().getFloat("Green"));
	    	comp.setFloat("Blue", te.getTileData().getFloat("Blue"));
	    	comp.setFloat("Rarity", te.getTileData().getFloat("Rarity"));
	    	comp.setFloat("Extra", te.getTileData().getFloat("Extra"));
	    	
	    	stack.setTagCompound(comp);
	    	EntityItem item = new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), stack);
	    	if(!world.isRemote) {
	    		world.spawnEntity(item);
	    	}
		}
		
		world.removeTileEntity(pos);
		super.breakBlock(world, pos, state);
	}
	
    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, net.minecraft.item.ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}
    
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
	{
		super.eventReceived(state, worldIn, pos, id, param);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	}
    
    static final class SwitchEnumFacing
    {
        static final int[] FACING_LOOKUP = new int[EnumFacing.values().length];

        static
        {
            try
            {
                FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError var4)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError var3)
            {
                ;
            }
            
            try
            {
                FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 3;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 4;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }
        }
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityButterflyCase();
	}
}
