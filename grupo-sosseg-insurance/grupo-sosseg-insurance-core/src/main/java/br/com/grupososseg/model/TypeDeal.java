package br.com.grupososseg.model;

import java.util.stream.Stream;

public enum TypeDeal {

    BIG_INFLUENCER("Big Influenciador"),
    MEGA_INFLUENCER("Mega Influenciador"),
    MICRO_INFLUENCER("Micro Influenciador"),
    NANO_INFLUENCER("Nano Influenciador");

	private String name;

	private TypeDeal(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	
	public static TypeDeal findByName(String name) {
		
		return Stream.of(TypeDeal.values())
		   .filter(type -> type.getName().equalsIgnoreCase(name))
		   .findAny()
		   .get();
	}
	
}
