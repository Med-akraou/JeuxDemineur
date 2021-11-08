package pk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Dimeneur extends JFrame {
 DemineurMatrice m;
JPanel p,p1;
int larg;
int x0=20;
int y0=100;
JButton Jouer,Quitter;
int scors,nbRestant;
boolean bool=false;
public Dimeneur() {
	setTitle("Jeux demineur");
	setSize(600, 800);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	m=new DemineurMatrice(10, 10, 10);
	scors=0;nbRestant=m.nbBombes;
	larg=(getWidth()-40)/m.nbCasesX;
	p = new JPanel(){
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			dessinerGrille(g);
			g.setColor(Color.cyan);
			g.fillRect(200, 20, 200, 30);
			g.fillRect(200, 60, 200, 30);
			g.setColor(Color.black);
			g.drawString("Scorse: "+scors, 220, 40);
			g.drawString("Bombes restantes: "+nbRestant+"/"+m.nbBombes, 220, 80);
			
			for(int i=0;i<m.nbCasesY;i++)
				for(int j=0;j<m.nbCasesX;j++){
					if(bool && m.matrice[i][j]==-2)
						dessinerBombeRouge(g,x0+j*larg,y0+i*larg);
					else if(m.matrice[i][j]==-3)
						dessinerBombeVerte(g,x0+j*larg,y0+i*larg);
					}
			
			for(int i=0;i<m.nbCasesY;i++)
				for(int j=0;j<m.nbCasesX;j++)
					if(m.dejaDecouverte(i, j) && !m.isBombe(i, j) )
					g.drawString(String.valueOf(m.matrice[i][j]), x0+j*larg+larg/2, y0+i*larg+larg/2);
		}
	};
	
	getContentPane().add(p);
	Jouer=new JButton("Jouer");
	Quitter= new JButton("Quitter");
	p1= new JPanel();
	p1.add(Jouer);p1.add(Quitter);
	getContentPane().add(p1,BorderLayout.NORTH);
	Jouer.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new Dimeneur();
			
		}
	});
	Quitter.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
      dispose();
			
		}
	});
	p.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			if(isClicOnGrille(e) )
			{
				int i=toIndiceLingne(e.getY());
				int j=toIndiceColonne(e.getX());
				if(!m.dejaDecouverte(i, j) )
				{
					if( e.getButton()==MouseEvent.BUTTON1){
					if(m.isRedBombe(i, j)){
						
						bool=true;
						p.repaint();
						JOptionPane.showMessageDialog(null, "Erreur ! Vous avez perdu ");
						Dimeneur.this.dispose();
					}
					else{
						m.matrice[i][j]=m.CalculeNbBombes(i, j);
						//decouvrir(i,j);
						scors+=5;
						p.repaint();
					}
					}
					 if( e.getButton()==MouseEvent.BUTTON3 ){
						 if(m.isRedBombe(i, j)){
						m.matrice[i][j]=-3;
						nbRestant-=1; scors+=1;
						p.repaint();}
						 else {JOptionPane.showMessageDialog(null, "Erreur ! Vous avez perdu ");
						 Dimeneur.this.dispose();
						 }
					}
						
				}
			}
		}
	});
	
	setVisible(true);
}
void dessinerGrille(Graphics g){
	g.setColor(Color.white);
	g.fillRect(x0, y0, larg*m.nbCasesX, larg*m.nbCasesY);
	g.setColor(Color.black);
	
	for(int i=0;i<=m.nbCasesY;i++)
		g.drawLine(x0, y0+i*larg, x0+larg*m.nbCasesX, y0+i*larg);
	for(int i=0;i<=m.nbCasesX;i++)
		g.drawLine(x0+i*larg, y0,x0+i*larg , y0+larg*m.nbCasesY);
}
private boolean isClicOnGrille(MouseEvent e) {
	if(e.getX()>=x0 && e.getX()<=x0+m.nbCasesX*larg && e.getY()>=y0 && e.getY()<=y0+m.nbCasesY*larg)
		return true;
	return false;
}
private int toIndiceColonne(int x) {
	return (x-x0)/larg;
}
private int toIndiceLingne(int y) {
	return (y-y0)/larg;
}
private void dessinerBombeRouge(Graphics g,int x, int y){
	 dessinerBombe( g,Color.red, x,  y);
}
private void dessinerBombeVerte(Graphics g,int x, int y){
	 dessinerBombe( g,Color.green, x,  y);
}
private void dessinerBombe(Graphics g,Color c,int x, int y) {
g.setColor(c);
g.fillRect(x, y, larg, larg);
g.setColor(Color.gray);
g.fillOval(x+larg/4, y+larg/4, larg/2, larg/2);
g.setColor(Color.black);
g.drawOval(x+larg/4, y+larg/4, larg/2, larg/2);
g.fillOval(x+(3*larg)/5, y+larg/4, larg/4, larg/4);
g.drawLine(x+(4*larg)/5, y+larg/4, x+larg, y+larg/5);
}

/*void decouvrir(int i, int j){
	m.matrice[i][j]=m.CalculeNbBombes(i, j);
	
	if(m.isZeroBombes(i, j) )
	{
		if(m.isOutsideMatrice(i-1, j-1)&& m.isZeroBombes(i-1, j-1)) try{decouvrir(i-1,j-1);}
		catch (Exception e) {
			System.out.println("ça ne marche pas");
		}
		if(m.isOutsideMatrice(i-1, j)&& m.isZeroBombes(i-1, j))try{ decouvrir(i-1,j);}
		catch (Exception e) {
			System.out.println("ça ne marche pas");
		}
		if(m.isOutsideMatrice(i-1, j+1) && m.isZeroBombes(i-1, j+1)) try{decouvrir(i-1,j+1);}
		catch (Exception e) {
			System.out.println("ça ne marche pas");
		}
		if(m.isOutsideMatrice(i+1, j-1)&& m.isZeroBombes(i+1, j-1)) try{decouvrir(i+1,j-1);}
		catch (Exception e) {
			System.out.println("ça ne marche pas");
		}
		if(m.isOutsideMatrice(i+1, j)&& m.isZeroBombes(i+1, j)) try{decouvrir(i+1,j);}
        catch (Exception e) {
        	System.out.println("ça ne marche pas");
          }
		if(m.isOutsideMatrice(i+1, j+1)&& m.isZeroBombes(i+1, j+1)) try{decouvrir(i+1,j+1);}
		catch (Exception e) {
			System.out.println("ça ne marche pas");
		}
		if(m.isOutsideMatrice(i, j-1)&& m.isZeroBombes(i, j-1)) try{decouvrir(i,j-1);}
		catch (Exception e) {
			System.out.println("ça ne marche pas");
		}
		if(m.isOutsideMatrice(i, j+1)&& m.isZeroBombes(i, j+1)) try{decouvrir(i,j+1);}
		catch (Exception e) {
			System.out.println("ça ne marche pas");
		
	
}*/
public static void main(String[] args) {
	Dimeneur  j=	new Dimeneur();
	
}
}
