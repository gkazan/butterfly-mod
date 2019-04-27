package net.soggymustache.butterflies.util;

public enum ButterflyType {

	MONARCH(0, "Monarch"), 
	SWALLOWTAIL(1, "Swallowtail"), 
	CLOAK(2, "Cloak"), 
	ADMIRAL(3, "Admiral"), 
	CAPTAIN(4, "Captain"), 
	SKIPPER(5, "Skipper"), 
	MORPHO(6, "Morpho"), 
	BUTTERFLY(7, "Butterfly");

	public final int id;
	public final String name;
	
	ButterflyType(int id, String name) {
		this.id = id;
		this.name = name;
	}
}