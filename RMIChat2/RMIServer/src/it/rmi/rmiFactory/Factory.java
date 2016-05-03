package it.rmi.rmiFactory;

import java.rmi.AccessException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.rmi.rmiFactory.model.impl.ServerImpl;

/*
 * - instanzia il registro
 * - esporta l'oggetto "ServerInterface"
 */

public class Factory {
	
	private Registry registry;
	
	private int registryPort = 1099;
	
	
	/*
	 * Pattern singleton, per garantire l'esistenza di una sola factory
	 */
	private static Factory instance = null;

	protected Factory() { /* per distruggerla */ };

	/*
	 * Torna la factory per fare myFactory.exportObjects()
	 */
	public static Factory getInstance() {
		if(instance == null) {
			instance = new Factory();
		}
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * @see it.rmi.rmiFactory.RmiFactory#exportObjects()
	 * Permette di esportare tutti gli oggetti definiti (per ora solo "ServerInterface")
	 */
	public void exportObjects() {

		try {

			
			
			Remote stub = UnicastRemoteObject.exportObject(new ServerImpl(), 0);
			
			
			
			registry = LocateRegistry.createRegistry(registryPort);
						
			registry.rebind("ServerInterface", stub);
			
			
			
			
			
			
			System.out.println("Server correctly configured ...");
			
			System.out.println("RMI handler is going to sleep for 15 minutes ...");
			
			Thread.sleep(15*60*1000);
			
			//Thread.interrupted();
			

			
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	

	
}
