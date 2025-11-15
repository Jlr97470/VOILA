package AccessTable;

import MenuPrincipale.FenetrePrincipale;
import MenuPrincipale.ListePrincipale;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JFrame;

public class AccessTable{
	private Vector<Vector<Object>> vvs_table = new Vector<Vector<Object>>(); // Tableau contenant la Table apr�s un getTableInVector()
	private String path; // Chemin vers ma table Access (ne pas oublier les double back-slash "\\" ex: C:\\repertoire\\matable.accdb)
	private String table; // Nom de la Table
	private Connection conn;
	private Statement st;
	private boolean etap1 = false;  
	private boolean etap2 = false;  
	private boolean etap3 = false;
	
	/* Ces booleans me servent � v�rifier que l'objet AccessTable est correctement utilis�
	 * pour ne pas lancer une connexion sans avoir donner un chemin d'acc�s et une table
	 * ou pour ne pas d�connect� si l'on ne sait pas connect� d'abord...
	 */

  public AccessTable() throws SQLException
  {
  }
  public AccessTable(String pathtodb)
  {
	  this.path = pathtodb;
	  this.etap1 = true;
  }
  public AccessTable(String pathtodb, String tablename)
  {
	  this.path = pathtodb;
	  this.table = tablename;
	  this.etap1 = true;
	  this.etap2 = true;
  }
  public void setPathToDataBase(String s) throws Throwable
  {
	  if(!this.etap3)
	  {
		  this.path = s;
		  this.etap1 = true;
	  }
	  else
	  {
		  throw new Exception("AccessTable en cours d'utilisation");
	  }
  }
  public void setTableName(String s) throws Throwable
  {
	  if(!this.etap3)
	  {
		  this.table = s;
		  this.etap2 = true;
	  }
	  else
	  {
		  throw new Exception("AccessTable en cours d'utilisation");
	  }
  }
  public void connection() throws Throwable //Initialise la connexion
  {
	  if( this.etap1 && this.etap2)
	  {
		  this.conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ="+this.path);
		  this.st = this.conn.createStatement();
		  this.etap3 = true;
	  }
	  else
	  {
		  throw new Exception("AccessTable non configur� !");
	  }
  }
  public Vector<Vector<Object>> getTableInVector() throws Throwable // R�cup�ration de la Table dans un Vector de Vector d'objet
  {
	  if (this.etap3)
	  {
		  ResultSet rs = this.st.executeQuery("SELECT * FROM "+this.table);
		  for(int i = 0; rs.next();i++)
		  {
			  Vector<Object> vs = new Vector<Object>();
			  for (int j = 1 ; j<= rs.getMetaData().getColumnCount();j++)
			  {
				  vs.add(rs.getObject(j));
			  }
			  this.vvs_table.add(vs);
		  }
	  }
	  else
	  {
		  throw new Exception("AccessTable non connect�");
	  }
	  return this.vvs_table;
  }
  public void disconnection() throws Throwable // d�connexion
  {
	  if (this.etap3)
	  {
		  this.st.close();
		  this.conn.close();
		  this.etap3 = false;
	  }
	  else
	  {
		  throw new Exception("AccessTable non connect�");
	  }
	  
  }
  public static void main(String[] arg0) throws Throwable
  {
        String StrBase = "D:\\\\MES_SITES_INTERNET\\\\ASP\\\\DELTAINFORMATIQUE\\\\WEBSITE\\\\BASE\\\\Delta-InfoFich_2010.accdb";
        
        String StrTable = "TBLMACCONNEXIONS";
        
        String StrList[] = new String[1000000];
        
        Integer IntNumElement=0;
        
        AccessTable ASTable = new AccessTable();
        
        ASTable.setPathToDataBase(StrBase);
        
        ASTable.setTableName(StrTable);
        
        ASTable.connection();
        
        Vector<Vector<Object>> vvo_test = ASTable.getTableInVector();
        
        ASTable.disconnection();
        
        for(Vector<Object> vo : vvo_test)
        {            
                StrList[IntNumElement]=new String(vo.toString());
                
                IntNumElement=IntNumElement+1;
        }      
        // on cr�e une fen�tre dont le titre est "Hello World!" 
        FenetrePrincipale fenetre = new FenetrePrincipale("VOIELA 1.0");

        ListePrincipale list = new ListePrincipale(fenetre.window,StrList); 
        // Defini la taille de la fenetre
        fenetre.window.setSize(640, 480);
        // la fen�tre doit se fermer quand on clique sur la croix rouge
        fenetre.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // on centre la fen�tre
        fenetre.window.setLocationRelativeTo(null);
        // on rend la fen�tre visible
        fenetre.window.setVisible(true);          
  }

}