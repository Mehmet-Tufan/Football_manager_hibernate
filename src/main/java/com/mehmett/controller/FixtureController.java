package com.mehmett.controller;

public class FixtureController {
	private static FixtureController instance;
	
	private FixtureController() {
	
	}
	public static FixtureController getInstance() {
		if (instance == null) {
			instance =new FixtureController();
		}
		return instance;
	}
}