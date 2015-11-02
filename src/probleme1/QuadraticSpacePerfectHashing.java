package probleme1;

import java.util.ArrayList;
import java.util.Random;

public class QuadraticSpacePerfectHashing<AnyType> 
{
	static int p = 46337;

	int a, b;
	AnyType[] items;
	
	/**
	 * Constructeur par défaut
	 */
	QuadraticSpacePerfectHashing()
	{
		a=b=0; items = null;
	}

	/**
	 * Constructeur par paramètre
	 */
	QuadraticSpacePerfectHashing(ArrayList<AnyType> array)
	{
		AllocateMemory(array);
	}

	/**
	 * Permet de faire appel à AllocateMemory pour remplir la table
	 */
	public void SetArray(ArrayList<AnyType> array)
	{
		AllocateMemory(array);
	}

	/**
	 * Retourne la taille de la table
	 */
	public int Size()
	{
		if( items == null ) return 0;

		return items.length;
	}

	/**
	 * Vérifie si l'élément est bien dans la table
	 */
	public boolean contains(AnyType x)
	{
		// Recalcul de la position
		int m = items.length;
		int pos = ((a*(x.hashCode())+b)%p)%m;
		
		// Si l'élément est bien à cette place
		if(null != items[pos] && items[pos].equals(x))
		{
			return true;
		}
		return false;
			
	}

	/**
	 * Retourne l'item s'il est dans la table
	 */
	public AnyType getItem (AnyType x) {
		// Consiste à recalculer sa position dans la table
		int m = items.length;
		return items[((a*(x.hashCode())+b)%p)%m];
	}

	/**
	 * Supprime l'item s'il est dans la table
	 */
	public void remove (AnyType x) {
		// Recalcule de la position et on le supprime s'il y est
		int m = items.length;
		items[((a*(x.hashCode())+b)%p)%m] = null;
	}


	@SuppressWarnings("unchecked")
	private void AllocateMemory(ArrayList<AnyType> array)
	{
		Random generator = new Random( System.nanoTime() );

		if(array == null || array.size() == 0)
		{
			// Si le tableau est vide, on crée une table vide
			items = (AnyType[]) new Object[0];
			return;
		}
		if(array.size() == 1)
		{
			// Si le tableau contient un élément, on crée une table à un élément
			// Et positionne cet élément à la première case	
			a = b = 0;
			items = (AnyType[]) new Object[1];
			items[0] = array.get(0);		
			return;
		}

		// Tant qu'on a des collisions
		do
		{
			// On (ré)initialise la table à null
			items = null;
			// On génère a et b
			a = generator.nextInt(p-1)+1;
			b = generator.nextInt(p);
		}
		// On essaye de placer les éléments du tableau
		while( collisionExists( array ) );
	}

	/**
	 * Remplit la table s'il n'y a pas de collision
	 */
	@SuppressWarnings("unchecked")
	private boolean collisionExists(ArrayList<AnyType> array)
	{
		int m = array.size()*array.size();
		items = (AnyType[]) new Object[m];
		for(AnyType i : array)
		{	
			// Pour chaque élément du tableau on calcule sa position
			int hashCode = i.hashCode();
			if(hashCode >= p) return true;
			int pos = ((a*hashCode+b)%p)%m;
			// Si la case est vide on place l'élément
			if (items[pos] == null)
			{
				items[pos]=i;
			}
			// Sinon c'est une collision
			else return true;
		}
		return false;
	}
}
