package net.soggymustache.butterflies.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;
import net.soggymustache.butterflies.ButterfliesReference;

@Mod.EventBusSubscriber(modid = ButterfliesReference.MOD_ID)
public class ButterfliesEntities {

	private static int id = 0;
	public static final List<EntityContainer> containers = new ArrayList<>();

	static {
		add(EntityButterfly.class, "butterfly");
	}

	private static void add(Class<? extends Entity> entityClass, String entityNameIn){
		containers.add(new EntityContainer(entityClass, entityNameIn));
	}

	@SubscribeEvent
	public static void registerEntities(final RegistryEvent.Register<EntityEntry> event) {
		final IForgeRegistry<EntityEntry> registry = event.getRegistry();

		for(EntityContainer container : containers){
			EntityEntry entry = EntityEntryBuilder.create().entity(container.entityClass).id(new ResourceLocation(ButterfliesReference.MOD_ID, container.entityName), id++).name(ButterfliesReference.MOD_ID + "." + container.entityName).tracker(90, 2, true).build();
			entry.setEgg(new EntityEggInfo(new ResourceLocation(ButterfliesReference.MOD_ID, container.entityName), 0xbdde1f, 0x1F85DE));
			registry.register(entry);
		}
	}
}
