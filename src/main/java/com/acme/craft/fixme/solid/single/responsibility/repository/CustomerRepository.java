package com.acme.craft.fixme.solid.single.responsibility.repository;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import lombok.extern.java.Log;


public class CustomerRepository {

	public void add() {
		try {
			/*
			 * do some database stuff here
			 */
		} catch (Exception e) {
			HandleError.handleError("b�ad");
		}
	}

}
