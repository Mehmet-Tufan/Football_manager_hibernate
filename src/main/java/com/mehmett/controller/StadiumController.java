package com.mehmett.controller;

public class StadiumController {
	private static StadiumController instance;
	private StadiumController() {
	
	}
	public static StadiumController getInstance() {
		if (instance == null) {
			instance = new StadiumController();
		}
		return instance;
	}
}