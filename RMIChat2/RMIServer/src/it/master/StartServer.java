package it.master;
import it.rmi.rmiFactory.Factory;


public class StartServer {
	
	
	
	public static void main(String[] args){
		
		
		System.setProperty("java.security.policy","file:./security.policy");
		
        System.setSecurityManager(new SecurityManager());
		
		System.out.println("Launching the server..");

		
		//esporto gli oggetti da remotizzare
		Factory.getInstance().exportObjects();
		
			
	}
}