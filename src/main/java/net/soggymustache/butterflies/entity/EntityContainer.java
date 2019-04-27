package net.soggymustache.butterflies.entity;

import net.minecraft.entity.Entity;

public class EntityContainer {

	public Class<? extends Entity> entityClass;
	public String entityName;

	public EntityContainer(Class<? extends Entity> EntityClass, String entityNameIn) {
		this.entityClass = EntityClass;
		this.entityName = entityNameIn;
	}

}