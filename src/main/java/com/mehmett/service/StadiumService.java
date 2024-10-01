package com.mehmett.service;

import com.mehmett.entity.Stadium;
import com.mehmett.repository.StadiumRepository;

public class StadiumService extends ServiceManager<Stadium,Long>{
	private static StadiumService instance;
	private StadiumService() {
		super(StadiumRepository.getInstance());
	}
	public static StadiumService getInstance() {
		if (instance == null) {
			instance = new StadiumService();
		}
		return instance;
	}
}