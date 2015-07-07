package com.acme.craft.fixme.commons.string;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NameValidator {

	public boolean valid(String name) {
		if (name != null && name.length() > 0) {
			return true;
		}
		return false;
	}

	public boolean isJohn(String name) {
		String johnName = "John";
		return name == johnName;
	}

	public String validationMessage(String firstName, String lastName, String nick) {
		StringBuilder sb = new StringBuilder();
		sb.append("Provided name is not valid. First name: ").append(firstName).append(" lastName: ");
		sb.append(lastName);
		sb.append(" nick: ");
		sb.append(nick);
		log.info("CZESC");
		return sb.toString();
		//return String.format("Provided name is not valid. First name: %s lastName: %s nick: %s", firstName, lastName, nick);
	}

}
