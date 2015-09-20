package com.mrcrayfish.furniture.api;

public enum RecipeType {
	FREEZER("freezer"), 
	OVEN("oven"), 
	PRINTER("printer"), 
	MINEBAY("minebay"), 
	TOASTER("toaster"), 
	CHOPPING_BOARD("choppingboard"), 
	BLENDER("blender"), 
	DISHWASHER("dishwasher"),
	WASHING_MACHINE("washingmachine"),
	MICROWAVE("microwave");
	
	private String name;
	
	private RecipeType(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}