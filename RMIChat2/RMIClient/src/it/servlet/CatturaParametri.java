package it.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.app.Applicazione;
import it.chat.impl.Login;
import it.master.ApplicazioneImpl;

/**
 * Servlet implementation class Servlet
 */

@WebServlet("/catturaParametri")
public class CatturaParametri extends HttpServlet {

	
	private static final long serialVersionUID = 102831973249L;
	
	private Applicazione applicazione = new ApplicazioneImpl();
			
    /**
     * Default constructor. 
     */
    public CatturaParametri() {
        // TODO Auto-generated constructor stub
    	super();
    }
  
    @Override public void init() throws ServletException {System.out.println("Sono nell init");};
    
@Override protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException 
{
	System.out.println("Sono entrato nel service");
	
	String pwd=request.getParameter("id");
	String nick = request.getParameter("name");
	

	
	if( CatturaParametri.this.applicazione.validate(nick,pwd) ) {
		CatturaParametri.this.applicazione.setUserName(nick);
		CatturaParametri.this.applicazione.openListUsers();}
	else{
		CatturaParametri.this.applicazione.registraFacebook(nick,pwd);
		CatturaParametri.this.applicazione.setUserName(nick);
		CatturaParametri.this.applicazione.openListUsers();    
        }
}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
@Override	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("JSono entrato nel doget");
		
		System.out.println(request.getParameter("id"));
		System.out.println(request.getParameter("name"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
@Override	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
