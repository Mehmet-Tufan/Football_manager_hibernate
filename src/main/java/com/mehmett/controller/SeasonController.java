package com.mehmett.controller;

public class SeasonController {
	private static SeasonController instance;
	public SeasonController() {
	
	}
	public static SeasonController getInstance() {
		if (instance == null) {
			instance = new SeasonController();
		}
		return instance;
	}
}