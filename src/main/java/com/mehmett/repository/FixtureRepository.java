package com.mehmett.repository;

import com.mehmett.entity.Fixture;

public class FixtureRepository extends RepositoryManager<Fixture,Long>{
	private static FixtureRepository instance;
	
	private FixtureRepository() {
		super(Fixture.class);
	}
	public static FixtureRepository getInstance() {
		if (instance == null) {
			instance = new FixtureRepository();
			
		}
		return instance;
	}
}