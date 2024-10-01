package com.mehmett.service;

import com.mehmett.entity.Fixture;
import com.mehmett.repository.FixtureRepository;


public class FixtureService extends ServiceManager<Fixture,Long>{
	
	private static FixtureService instance;
	
	private FixtureService() {
		super(FixtureRepository.getInstance());
	}
	public static FixtureService getInstance() {
		if (instance == null) {
			instance = new FixtureService();
		}
		return instance;
	}
}