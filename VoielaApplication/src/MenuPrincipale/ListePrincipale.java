package MenuPrincipale;

import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 

public class ListePrincipale extends JDialog implements ActionListener 
{
    public JList controle=new JList();

    public ListePrincipale(JFrame fenetre,String listData[])
    {
            JList list=new JList(listData);
            this.controle=list;
            fenetre.add(list);
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
          // TODO Auto-generated method stub

    }
}
