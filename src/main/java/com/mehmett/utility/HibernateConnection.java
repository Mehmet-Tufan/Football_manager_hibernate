package com.mehmett.utility;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateConnection {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Futbol_ManagerV2");
	public static  EntityManager em = emf.createEntityManager();
	
	
public static void beginConnection(){
	try{
		em.getTransaction().begin();
		
		
	}
	catch(RuntimeException e){
		System.err.println("Exception in connectionBegin: "+ e.getMessage());
		
	}
}
	
	public static void connectionClose(){
		try{
			em.close();
			emf.close();
			
			
		}
		catch(RuntimeException e){
			System.err.println("Exception in connectionClose: "+ e.getMessage());
			
		}
	}
	
	
	public static void em_commit(){
		try{
		
			em.getTransaction().commit();
			
			
		}
		catch(RuntimeException e){
			System.err.println("Exception in commit "+ e.getMessage());
			
		}
	}
}