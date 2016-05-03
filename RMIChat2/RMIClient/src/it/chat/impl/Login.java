package it.chat.impl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import it.app.Applicazione;
import it.utility.InvioKeyListener;

public class Login extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7761456041218602590L;

	private Applicazione applicazione;
	
	private static int tentativi = 0;
	
	private JPanel display;
	private JTextField nomeField;
	private JPasswordField pwdField;
	private JLabel errorLable;
	private JButton okButton; 
	
	
	private void doLogin(){
		
		System.out.println("tentativo:"+ tentativi );
		++tentativi;
		
		System.out.println("Login:"+nomeField.getText().trim()+":"+ String.valueOf(pwdField.getPassword()).trim()+".");
		
		if( Login.this.applicazione.validate( nomeField.getText(), String.valueOf(pwdField.getPassword()).trim() ) ) {
			Login.this.applicazione.setUserName(nomeField.getText().trim());
			Login.this.setVisible(false);
			Login.this.applicazione.openListUsers();
		}else{
			errorLable.setForeground(Color.RED);
			errorLable.setText("user o password errati");
		}
		
		if(tentativi>=3){
			Login.this.applicazione.exitOnErr();
		}
	}
	
	
	private void facebookLogin(){
		System.out.println("Pulsante facebook");
		try {
			
			if(Desktop.isDesktopSupported())
			{
			  try {
				Desktop.getDesktop().browse(new URI("http://localhost:8080/RMIClient/index"));
			  	  } catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  	  								  }
			}
			
		}
			catch (MalformedURLException e) {
				e.printStackTrace();
				} catch (IOException e) {
				e.printStackTrace();
				}

	}
	/*** pannelli ***/
	private JPanel northPanel(){
		JPanel northPanel=new JPanel();
		
		ImageIcon icon = new ImageIcon("img/logo.jpg");
		Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		icon.setImage(scaledImage);
		
		JLabel image=new JLabel(icon);
		northPanel.add(image);
		return northPanel;
		
	}
	
	
	
	private JPanel centerPanel(){
		
		JPanel centerPanel = new JPanel();	
		nomeField = new JTextField();
		pwdField = new JPasswordField();
		errorLable = new JLabel();
		
		centerPanel.setLayout(new GridLayout(7, 1));
		
		NotNullDocumentListener listener = new NotNullDocumentListener();
		nomeField.getDocument().addDocumentListener(listener);
		pwdField.getDocument().addDocumentListener(listener);
		
		InvioKeyListener l = new InvioListener();
		nomeField.addKeyListener(l);
		pwdField.addKeyListener(l);
		
		centerPanel.add(new JLabel());					// riga vuota
		centerPanel.add(new JLabel("User",SwingConstants.CENTER));
		centerPanel.add(nomeField);
		centerPanel.add(new JLabel("Password",SwingConstants.CENTER));
		centerPanel.add(pwdField);
		centerPanel.add(new JLabel());					// riga vuota
		centerPanel.add(errorLable);					
		
		return centerPanel;
	}
	
	private JPanel bottomPanel(){
		
		//Creo pulsanti
		JPanel bottomPanel = new JPanel();
		
		//Pulsante entra
		okButton = new JButton("ENTRA");
		okButton.setForeground(Color.black);
		
		//Pulsante annulla
		JButton cancelButton = new JButton("ANNULLA");
		cancelButton.setForeground(Color.red);
		
		//Pulsante facebook
		ImageIcon icon = new ImageIcon("img/facebook.png");
		Image scaledImage = icon.getImage().getScaledInstance(70, 18, Image.SCALE_SMOOTH);
		icon.setImage(scaledImage);
		JButton facebookButton = new JButton(icon);
		facebookButton.setBorder(null);
		
		//Disabilito il tasto ok per evitare venga premuto senza nome
		okButton.setEnabled(false);
		
		
		//Aggiungo gli Action Listener
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Login.this.doLogin();
				return;
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Login.this.applicazione.exitOnErr();
			}
		});
		
		facebookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Login.this.facebookLogin();
				Login.this.applicazione.exitOnErr();
				}
		});
		
		
		
		
		//Aggiungo i pulsanti al pannello
		bottomPanel.add(okButton);
		bottomPanel.add(cancelButton);
		bottomPanel.add(facebookButton);		
		
		return bottomPanel;
	}
	
	private JPanel display(){
		display.setLayout(new BorderLayout());
			
		display.add(northPanel(),BorderLayout.NORTH);
		display.add(centerPanel(),BorderLayout.CENTER);
		display.add(bottomPanel(),BorderLayout.SOUTH);
				
		return display;
	}
	
	
	private void initUI(){

		//Imposto le proprietà della finestre di login
		setTitle("LOGIN");
		setSize(450, 333);
		setLocation(450, 200);
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(display(),BorderLayout.CENTER);

		setVisible(true);
	}
	
	
	public Login(Applicazione applicazione) {
		super();
		this.applicazione = applicazione;
		this.display = new JPanel();
		initUI();
	}
	
	
	
	
	
	
	private class InvioListener extends InvioKeyListener{
		
		public InvioListener() { }
		
		private boolean campiVuoti(){
		
			return ( 
					(nomeField.getText() == null) || 
					(nomeField.getText().trim().equals("")) ||
					(pwdField.getPassword().toString() == null) ||
					(pwdField.getPassword().toString().trim().equals("")) 
				);
		}
		
		
		
		@Override
		protected boolean conditition() {
			return !campiVuoti();
		}

		@Override
		protected void doAction() {
			Login.this.doLogin();
		}
	}
	
	
	
	private class NotNullDocumentListener implements DocumentListener{
		
		private boolean campiVuoti(){
			return ( 
					(nomeField.getText() == null) || 
					(nomeField.getText().trim().equals("")) ||
					(pwdField.getPassword().toString()== null) ||
					(pwdField.getPassword().toString().trim().equals("")) 
				);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			errorLable.setText("");
			if(!campiVuoti() )
				okButton.setEnabled(true);
			else 
				okButton.setEnabled(false);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			errorLable.setText("");
			if(!campiVuoti() )
				okButton.setEnabled(true);
			else 
				okButton.setEnabled(false);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			errorLable.setText("");
			if(!campiVuoti() )
				okButton.setEnabled(true);
			else 
				okButton.setEnabled(false);
		}
	}
}