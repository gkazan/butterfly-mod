package net.soggymustache.butterflies.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.EnumDyeColor;

public class ColorUtilities {

	public static final Map<EnumDyeColor, Float[]> COLOR = new HashMap<>();
	public static final Random RAND = new Random();
	
	static {
		Float[] blue = {0.145F, 0.196F, 0.796F};
		COLOR.put(EnumDyeColor.BLUE, blue);
		
		Float[] brown = {0.373F, 0.227F, 0.059F};
		COLOR.put(EnumDyeColor.BROWN, brown);
		
		Float[] cyan = {0.173F, 0.651F, 0.8F};
		COLOR.put(EnumDyeColor.CYAN, cyan);
		
		Float[] gray = {0.435F, 0.478F, 0.49F};
		COLOR.put(EnumDyeColor.GRAY, gray);

		Float[] green = {0.094F, 0.451F, 0.145F};
		COLOR.put(EnumDyeColor.GREEN, green);
		
		Float[] lblue = {0.145F, 0.702F, 0.961F};
		COLOR.put(EnumDyeColor.LIGHT_BLUE, lblue);
		
		Float[] lime = {0.118F, 0.816F, 0.22F};
		COLOR.put(EnumDyeColor.LIME, lime);
		
		Float[] magenta = {0.624F, 0.231F, 0.745F};
		COLOR.put(EnumDyeColor.MAGENTA, magenta);
		
		Float[] orange = {0.831F, 0.412F, 0.098F};
		COLOR.put(EnumDyeColor.ORANGE, orange);
		
		Float[] pink = {0.937F, 0.078F, 0.733F};
		COLOR.put(EnumDyeColor.PINK, pink);
		
		Float[] purple = {0.58F, 0.078F, 0.937F};
		COLOR.put(EnumDyeColor.PURPLE, purple);
		
		Float[] red = {0.843F, 0.098F, 0.098F};
		COLOR.put(EnumDyeColor.RED, red);
		
		Float[] silver = {0.776F, 0.776F, 0.776F};
		COLOR.put(EnumDyeColor.SILVER, silver);
		
		Float[] white = {1.0F, 1.0F, 1.0F};
		COLOR.put(EnumDyeColor.WHITE, white);
		
		Float[] yellow = {0.89F, 0.878F, 0.086F};
		COLOR.put(EnumDyeColor.YELLOW, yellow);
		
		//Black looks bad so we replace it with white
		COLOR.put(EnumDyeColor.BLACK, white);
	}
	
	public static Float[] generateColor(EnumDyeColor color) {
		Float[] reference = COLOR.get(color);
		Float[] n = new Float[3];
		
		n[0] = reference[0] + (RAND.nextFloat() * 0.1F * (RAND.nextBoolean() ? -1 : 1));
		n[1] = reference[1] + (RAND.nextFloat() * 0.1F * (RAND.nextBoolean() ? -1 : 1));
		n[2] = reference[2] + (RAND.nextFloat() * 0.1F * (RAND.nextBoolean() ? -1 : 1));
		return n;
	}
	
}
