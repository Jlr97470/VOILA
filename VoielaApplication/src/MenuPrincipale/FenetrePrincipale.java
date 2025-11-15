package MenuPrincipale;

import  javax.swing.*;
import  java.awt.*; 
import  java.awt.event.*;

public class FenetrePrincipale extends JFrame implements ActionListener 
{
    public JFrame window=new JFrame();

    public FenetrePrincipale(String Title)
    {
            JFrame fenetre=new JFrame(Title);
            this.window=fenetre;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}