package net.soggymustache.butterflies.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.soggymustache.butterflies.client.render.RenderButterfly;
import net.soggymustache.butterflies.entity.EntityButterfly;

public class NameUtilities {
	
	public static List<String> pre, middle;
	public static final Random RANDOM = new Random();
	private static Map<Integer, ButterflyInfo> butterflies;
	private static int nextID = 0;
	private static int[] IDS;
	
	static {
		butterflies = new HashMap<>();
		middle = Lists.newArrayList();
		pre = Lists.newArrayList();
		
		pre.add("Rare");
		pre.add("Common");
		pre.add("Undiscovered");

		middle.add("Eastern");
		middle.add("Western");
		middle.add("Northern");
		middle.add("Old");
		middle.add("Mourning");
		middle.add("Pipevine");
		middle.add("Strange");

		nextID = ButterflyType.values().length;
		
		addButterfly(new ButterflyInfo("Menelaus Blue Morpho Butterfly", 10.0F, 0.18F, 0.502F, 0.671F));
		addButterfly(new ButterflyInfo("Erose\'s Butterfly", 10.0F, 0.875F, 0.592F, 0.824F));
		
		ButterflyInfo glasswing = new ButterflyInfo("Glasswing Butterfly", 10.0F) {
			@Override
			@SideOnly(Side.CLIENT)
			public void render(ModelBase model, RenderButterfly render, EntityButterfly e, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
	            GlStateManager.pushMatrix();
	            GlStateManager.enableAlpha();
	            GlStateManager.enableBlend();
	            GlStateManager.color(e.getRed(), e.getGreen(), e.getBlue(), 0.5F);
	        	render.bindTexture(render.WINGS);
	        	model.setModelAttributes(render.getMainModel());
	        	model.render(e, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	            GlStateManager.color(e.getRed(), e.getGreen(), e.getBlue(), 1.0F);
	            GlStateManager.disableAlpha();
	            GlStateManager.disableBlend();
	            GlStateManager.popMatrix();
			}
		};
		addButterfly(glasswing);
		
		ButterflyInfo rainbow = new ButterflyInfo("Rainbow Butterfly", 10.0F) {
			@Override
			@SideOnly(Side.CLIENT)
			public void render(ModelBase model, RenderButterfly render, EntityButterfly e, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    	    	GlStateManager.pushMatrix();
    	        int i1 = 25;
    	        int i = e.ticksExisted / 25 + e.getEntityId();
    	        int j = EnumDyeColor.values().length;
    	        int k = i % j;
    	        int l = (i + 1) % j;
    	        float f = ((float)(e.ticksExisted % 25) + 2.0F) / 25.0F;
    	        float[] afloat1 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(k));
    	        float[] afloat2 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(l));
    	        GlStateManager.color(afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f);
	        	render.bindTexture(render.WINGS);
	        	model.setModelAttributes(render.getMainModel());
	        	model.render(e, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    	        GlStateManager.popMatrix();
			}
		};
		addButterfly(rainbow);

		IDS = new int[butterflies.size()];
		int count = 0;
		for(Integer info : butterflies.keySet()) {
			IDS[count] = info.intValue();
			count++;
		}
	}
	
	public static Map<Integer, ButterflyInfo> getInfo() {
		return butterflies;
	}
	
	public static void addButterfly(ButterflyInfo info) {
		butterflies.put(nextID++, info);
	}
	
	public static void generateStats(EntityButterfly butterfly, EnumDyeColor colour) {
		if(RANDOM.nextInt(40) == 0) {
			int selection = IDS[RANDOM.nextInt(IDS.length)];
			ButterflyInfo info = butterflies.get(selection);
			butterfly.setBName(info.NAME);
			butterfly.setRarity(info.BRARITY);
			butterfly.setExtra(selection);

			if(info.colors != null) {
				butterfly.setRed(info.colors[0]);
				butterfly.setGreen(info.colors[1]);
				butterfly.setBlue(info.colors[2]);
			}
			else {
				Float[] col = ColorUtilities.generateColor(butterfly.getEColor());
				butterfly.setRed(col[0]);
				butterfly.setGreen(col[1]);
				butterfly.setBlue(col[2]);
			}
		}
		else {
			String name = "";
			float rarity = 0.0F;
			
			if (RANDOM.nextInt(pre.size() * 2) == 0) {
				name += pre.get(RANDOM.nextInt(pre.size()));
				rarity += 0.2F;
			}
			
			if(colour != null) {
				if(colour == EnumDyeColor.BLACK)
					colour = EnumDyeColor.WHITE;
				
				name += " ";
				
				if(colour == EnumDyeColor.LIGHT_BLUE)
					name += "Light Blue";
				else
					name += fix((colour.getUnlocalizedName().replaceAll("_", " ")));
				rarity += colour.getMetadata() * 0.1F;
			}
			
			if (RANDOM.nextInt(middle.size() + 3) != 0) {
				name += " ";
				name += middle.get(RANDOM.nextInt(middle.size()));
				rarity += 0.1F;
			}
			else {
				rarity += 0.3F;
			}
			
			name += " ";
			ButterflyType type = ButterflyType.values()[RANDOM.nextInt(ButterflyType.values().length)];
			name += type.name;
			butterfly.setExtra(type.id);
			rarity += 0.1F;
			
			if(name.isEmpty() || name.length() <= 10) {
				name = "Undiscovered Majestic Something";
				rarity += 10.0F;
			}
			
			name = name.trim().replaceAll(" ", "_").replaceAll("_", " ");
			rarity = (float) Math.min(Math.round(rarity * 100.0) / 100.0, 10.0F);
			
			butterfly.setBName(name);
			butterfly.setRarity(rarity);
			
			Float[] col = ColorUtilities.generateColor(butterfly.getEColor());
			butterfly.setRed(col[0]);
			butterfly.setGreen(col[1]);
			butterfly.setBlue(col[2]);
		}
	}
	
	public static String fix(String input) {
	    StringBuilder titleCase = new StringBuilder();
	    boolean n = true;

	    for (char c : input.toCharArray()) {
	        if (Character.isSpaceChar(c)) {
	            n = true;
	        } else if (n) {
	            c = Character.toTitleCase(c);
	            n = false;
	        }
	        titleCase.append(c);
	    }

	    return titleCase.toString();
	}
}
