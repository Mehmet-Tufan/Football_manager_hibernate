package com.mehmett;

import com.mehmett.gui.MainMenu;
import com.mehmett.utility.Data.DemoData;
import com.mehmett.utility.HibernateConnection;

import java.util.Scanner;

public class Runner {
	public static void main(String[] args) {
		HibernateConnection.beginConnection();

		DemoData.GenerateDemonData();
		welcomeMassege();
		MainMenu mainMenu = MainMenu.getInstance();
		mainMenu.mainMenu();
		
		

		HibernateConnection.em.getTransaction().commit();
		HibernateConnection.connectionClose();
		
		
	}
	
	private static void welcomeMassege() {
		String title = """
				███████╗ ██████╗  ██████╗ ████████╗██████╗  █████╗ ██╗     ██╗    \s
				██╔════╝██╔═══██╗██╔═══██╗╚══██╔══╝██╔══██╗██╔══██╗██║     ██║    \s
				█████╗  ██║   ██║██║   ██║   ██║   ██████╔╝███████║██║     ██║    \s
				██╔══╝  ██║   ██║██║   ██║   ██║   ██╔══██╗██╔══██║██║     ██║    \s
				██║     ╚██████╔╝╚██████╔╝   ██║   ██████╔╝██║  ██║███████╗███████╗
				╚═╝      ╚═════╝  ╚═════╝    ╚═╝   ╚═════╝ ╚═╝  ╚═╝╚══════╝╚══════╝
				                                                                  \s
				███╗   ███╗ █████╗ ███╗   ██╗ █████╗  ██████╗ ███████╗██████╗     \s
				████╗ ████║██╔══██╗████╗  ██║██╔══██╗██╔════╝ ██╔════╝██╔══██╗    \s
				██╔████╔██║███████║██╔██╗ ██║███████║██║  ███╗█████╗  ██████╔╝    \s
				██║╚██╔╝██║██╔══██║██║╚██╗██║██╔══██║██║   ██║██╔══╝  ██╔══██╗    \s
				██║ ╚═╝ ██║██║  ██║██║ ╚████║██║  ██║╚██████╔╝███████╗██║  ██║    \s
				╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝    \s
				                                                                  \s
				""";
		System.out.println(title);
		System.out.println("Press any key to continue...");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		
	}
}