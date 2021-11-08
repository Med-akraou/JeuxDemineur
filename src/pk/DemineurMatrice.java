package pk;

import java.util.Random;

public class DemineurMatrice {
	int[][] matrice;
	int nbCasesX; // nombre de colonnes de la matrice
	int nbCasesY; // nombre de lignes de la matrice
	int nbBombes; // nombre de bombes à cacher dans la matrice
	
	public DemineurMatrice( int nbCasesX, int nbCasesY,
			int nbBombes) {
		matrice = new int[nbCasesY][nbCasesX];
		this.nbCasesX = nbCasesX;
		this.nbCasesY = nbCasesY;
		this.nbBombes = nbBombes;
		initMatrice();
		cacherBombes(nbBombes);
	}
	private void initMatrice(){
		for(int i=0;i<nbCasesY;i++)
			for(int j=0;j<nbCasesX;j++)
				matrice[i][j]=-1;
	}
	private void cacherBombes(int nbBombes){
		Random r = new  Random();
		int cpt=0;
		while(cpt<nbBombes)
		{
			int i=r.nextInt(nbCasesY);
			int j=r.nextInt(nbCasesX);
			if(matrice[i][j]!=-2)
			{
				matrice[i][j]=-2;
				cpt++;
			}
		}
	}
	 void afficherMatriceConsole(){
		for(int i=0;i<nbCasesY;i++){
			for(int j=0;j<nbCasesX;j++)
				System.out.print(matrice[i][j]+" ");
			System.out.println();
			}
	}
	public boolean dejaDecouverte(int i, int j){
		if(matrice[i][j]!=-1 && matrice[i][j]!=-2 )
			return true;
		else return false;
	}
	//boolean isOutsideMatrice(int x, int y) // teste si les coord (x,y) font partie de la matrice ou non.
	public boolean isRedBombe(int i, int j) {
		if (matrice[i][j]==-2) return true;
		else return false;
	}
	public boolean isGreenBombe(int i, int j){
		if (matrice[i][j]==-3) return true;
		else return false;
	}
	public boolean isBombe(int i,int j) {
		return (isGreenBombe(i,j)||isRedBombe(i, j));
	}
	
	public int CalculeNbBombes(int i,int j) {
		int cpt=0;
		
		
			if(isOutsideMatrice(i-1, j-1)&& isBombe(i-1,j-1)) cpt++;
			if(isOutsideMatrice(i-1, j)&&isBombe(i-1,j)) cpt++;
			if(isOutsideMatrice(i-1, j+1)&& isBombe(i-1,j+1)) cpt++;
			if(isOutsideMatrice(i+1, j-1)&&isBombe(i+1,j-1)) cpt++;
			if(isOutsideMatrice(i+1, j)&&isBombe(i+1,j)) cpt++;
			if(isOutsideMatrice(i+1, j+1)&&isBombe(i+1,j+1)) cpt++;
			if(isOutsideMatrice(i, j-1)&&isBombe(i,j-1)) cpt++;
			if(isOutsideMatrice(i, j+1)&&isBombe(i,j+1)) cpt++;
		
		return cpt;
	}
	public boolean isZeroBombes(int i,int j) {
		if(CalculeNbBombes(i, j)==0)
			return true;
		return false;
	}
	public boolean isOutsideMatrice(int x, int y) {
		return (x>=0 && x<nbCasesY && y>=0 && y<nbCasesX);
	}
	void decouvrirLesVides(int i,int j){
		
		  matrice[i][j]=CalculeNbBombes(i, j);
		  
		  if(matrice[i][j]==0){
			 if(i-1>=0 && j-1>=0)  decouvrirLesVides(i-1, j-1); 
			if(i-1>=0) decouvrirLesVides(i-1, j);  
			if(i-1>=0 && j+1<nbCasesX)   decouvrirLesVides(i-1, j+1);  
			  if(i+1<nbCasesY && j-1>=0) decouvrirLesVides(i+1, j-1); 
			 if(i+1<nbCasesY)  decouvrirLesVides(i+1, j); 
			   if(i+1<nbCasesY && j+1<nbCasesX)decouvrirLesVides(i+1, j+1); 
			 if(j-1>=0)  decouvrirLesVides(i, j-1); 
			   if(j+1<nbCasesX)decouvrirLesVides(i, j+1); 
		  }
		  
		}

public static void main(String[] args) {
	DemineurMatrice m= new DemineurMatrice(10, 10, 10);
	m.afficherMatriceConsole();
	m.decouvrirLesVides(5, 5);
	
 System.out.println();
 m.afficherMatriceConsole();
	
	
}
}