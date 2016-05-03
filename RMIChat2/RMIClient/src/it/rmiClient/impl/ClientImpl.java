package it.rmiClient.impl;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.app.Applicazione;
import it.rmi.interfacce.ClientInterface;
import it.rmi.interfacce.ServerInterface;


public class ClientImpl extends UnicastRemoteObject implements ClientInterface{
	
	private static final long serialVersionUID = 1L;


	private Applicazione applicazione;
	
	
	private String address="127.0.0.1";
	private byte[] key;					// chiave pubblica dell'applicazione
	private ServerInterface server;
	final static int registry_port = 1099;
	final static String host = "localhost";

	
	public void setServer(){
    	try{
    		
    		System.out.println("checkpoint 1");
    		
    		final Registry registry = LocateRegistry.getRegistry(address, registry_port);

			this.server = (ServerInterface) registry.lookup("ServerInterface");
			
			System.out.println("rmi client connesso correttamente.");
    		 
    		
    	}catch (RemoteException re){
			System.out.println();
			System.out.println("RemoteException");
			System.out.println(re);
			re.printStackTrace();
		}
		catch (NotBoundException nbe){
			System.out.println();
			System.out.println("NotBoundException");
			System.out.println(nbe);
		}
    	
    	return;
    	
    }
	
	
	
	
	public ClientImpl(Applicazione applicazione, String address, byte[] Pkey) throws RemoteException{
		
		this.applicazione = applicazione;
		
		this.address = address;
		
		this.key = Pkey;
		
		this.setServer();
		
		System.out.println("Costruttore ClientImpl");
	}
	
	
	@Override
	public void tell(int type, String user, byte[] msg) throws RemoteException {
		this.applicazione.deliverMessage(type, user, msg);
	}
	
	public ServerInterface getServer(){
		return this.server;
	}

	@Override
	public byte[] getPublicKey() throws RemoteException{
		return this.key;
	}
	
}