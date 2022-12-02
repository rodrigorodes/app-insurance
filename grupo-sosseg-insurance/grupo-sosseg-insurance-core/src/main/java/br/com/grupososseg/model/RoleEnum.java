package br.com.grupososseg.model;

import org.apache.commons.lang3.StringUtils;

public enum RoleEnum {

	ADMIN("ROLE_ADMIN", "Administrador"),
	USER("ROLE_USER", "Influencer");

	private String name;
	private String descricao;

	private RoleEnum(String name, String descricao) {
		this.name = name;
		this.descricao = descricao;
	}

	public String getName() {
		return name;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public static String findByName(String name) {
		if (StringUtils.isNoneBlank(name)) {
			for (RoleEnum role : RoleEnum.values()) {
				if (role.getName().equals(name)) {
					return role.getDescricao();
				}
			}
		}
		return StringUtils.EMPTY;
	}

}
