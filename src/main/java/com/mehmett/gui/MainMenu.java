package com.mehmett.gui;

import com.mehmett.entity.Manager;
import com.mehmett.gui.iddaa_gui.IddaaMenu;
import com.mehmett.gui.league_gui.LeagueMenu;
import com.mehmett.gui.manager_gui.ManagerDashboard;
import com.mehmett.gui.manager_gui.ManagerLoginRegister;
import com.mehmett.gui.match_gui.MatchMenu;

import java.util.Optional;

import static com.mehmett.utility.ConsoleTextUtils.*;


public class MainMenu {
    private Optional<Manager> manager = Optional.empty();
    private static MainMenu instance;

    private MainMenu() {}
    public static MainMenu getInstance() {
        if (instance == null) {
            instance = new MainMenu();
        }
        return instance;
    }




    public void mainMenu(){
        boolean opt;
        do{
            if (manager.isEmpty()) opt = anonymousMainMenu();
            //-->             <--//
            else opt = managerMainMenu();
        } while(opt);
    }

    private boolean managerMainMenu() {
        printTitle("Main Menu");
        printMenuOptions(
                "Manager Dashboard",
                "Play Match",
                "Show Leagues",
                "Logout",
                "Exit");
        return managerMainMenuOptions(getIntUserInput("Select: "));
    }

    private boolean managerMainMenuOptions(int intUserInput) {
        switch (intUserInput) {
            case 1:{ // Manager Dashboard
                ManagerDashboard managerDashboard = new ManagerDashboard();
               manager =  managerDashboard.managerDashboard(manager);
                
                break;
            }
            case 2:{// Play Match
                MatchMenu.getInstance().matchMenu(manager);
                break;
            }
            case 3:{ //League Menu
                LeagueMenu.getInstance().leagueMenu(manager);
                break;
            }
            case 4:{ // Logout
                manager = Optional.empty();
                System.out.println("Logging out...");

                break;
            }
            case 5:{// Exit..
                printSuccessMessage("Gülü Gülü....");
                return false;
            }
        }
        return true;
    }

    private boolean anonymousMainMenu() {
        printTitle("Main Menu");
        printMenuOptions(
                "Manager Login",
                "Play Match",
                "League Menu",
                "Fedaration Login (In development...)",
                "Go oynakazan.com",
                "Exit");
        return anonymousMainMenuOptions(getIntUserInput("Select: "));
    }

    private boolean anonymousMainMenuOptions(int intUserInput) {
        switch (intUserInput) {
            case 1:{ // Manager Login
                ManagerLoginRegister managerLoginRegister = ManagerLoginRegister.getInstance();
                manager = managerLoginRegister.loginRegisterMenu();
                break;
            }
            case 2:{// Play Match
                MatchMenu.getInstance().matchMenu(manager);
                break;
            }
            case 3:{ //League Menu
                LeagueMenu.getInstance().leagueMenu(manager);
                break;
            }
            case 4:{ // Federation login (In development...)
                System.out.println("In development...");
                break;
            }
            case 5:{
                IddaaMenu iddaaMenu = new IddaaMenu();
                iddaaMenu.iddaaMenu();
                break;
            }
            case 6:{// Exit..
                printSuccessMessage("Gülü Gülü....");
                return false;
            }
        }
        return true;
    }

}