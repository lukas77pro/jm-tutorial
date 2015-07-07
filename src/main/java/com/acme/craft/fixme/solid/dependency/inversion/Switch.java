package com.acme.craft.fixme.solid.dependency.inversion;

public class Switch {

	private boolean pressed;
	private Lamp l;

	public void pressSwitch() {
		pressed = !pressed;
		l.pressSwitch();
	}
}
