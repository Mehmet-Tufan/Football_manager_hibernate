package com.mehmett.controller;

import com.mehmett.dto.request.ManagerSaveRequestDTO;
import com.mehmett.dto.response.ManagerResponseDTO;
import com.mehmett.entity.Manager;
import com.mehmett.entity.Team;
import com.mehmett.service.ManagerService;
import com.mehmett.utility.ConsoleTextUtils;
import com.mehmett.dto.request.ManagerSaveRequestDTO;

import java.util.List;
import java.util.Optional;

public class ManagerController {
	private static ManagerController instance;
	private static ManagerService managerService = ManagerService.getInstance();
	
	
	private ManagerController() {
	}
	public static ManagerController getInstance() {
		if (instance == null) {
			instance = new ManagerController();
		}
		return instance;
	}

	public List<Manager> findByFieldAndValue(String fieldname, Object value) {
		return managerService.findByFieldNameAndValue(fieldname, value);
	}

    public Optional<Manager> findByUsernameAndPassword(String username, String password) {
		try {
			return managerService.findByUsernameAndPassword(username,password);

		}catch (Exception e) {
			System.out.println("Controller Error: "+ e.getMessage());
			return Optional.empty();
		}
    }

	public boolean checkUsername(String username) {
		try {
			return managerService.checkUsername(username);

		}catch (Exception e) {
			System.out.println("Controller Error: "+ e.getMessage());
			return false;
		}
	}

	public boolean checkPassword(String password, String repeatPassword) {
		try {
			return managerService.checkPassword(password, repeatPassword);

		}catch (Exception e) {
			System.out.println("Controller Error: "+ e.getMessage());
			return false;
		}
	}

	public Optional<ManagerResponseDTO> saveDTO(ManagerSaveRequestDTO saveRequestDTO) {
		try {
			return managerService.saveDTO(saveRequestDTO);
		}catch (Exception e) {
			System.out.println("Controller Error: "+ e.getMessage());
			return Optional.empty();
		}
	}


	public Optional<Manager> findByTeam(Team proposer) {
		try {
			List<Manager> managers = managerService.findByFieldNameAndValue("team", proposer);
			if (!managers.isEmpty()) {
				return Optional.of(managers.getFirst());
			}
		} catch (Exception e) {
			ConsoleTextUtils.printErrorMessage("Controller Error: "+ e.getMessage());
		}
		return Optional.empty();
	}
}