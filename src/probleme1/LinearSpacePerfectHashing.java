package probleme1;

import java.util.Random;
import java.util.ArrayList;

public class LinearSpacePerfectHashing<AnyType>
{
	static int p = 46337;

	QuadraticSpacePerfectHashing<AnyType>[] data;
	int a, b;

	/**
	 * Constructeur par défaut
	 */
	LinearSpacePerfectHashing()
	{
		a=b=0; data = null;
	}

	/**
	 * Constructeur par paramètre
	 */
	LinearSpacePerfectHashing(ArrayList<AnyType> array)
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
	 * Remplissage de la table à partir d'un tableau d'élément
	 */
	@SuppressWarnings("unchecked")
	private void AllocateMemory(ArrayList<AnyType> array)
	{
		Random generator = new Random( System.nanoTime() );

		// Si le tableau est vide, on crée une table vide
		if(array == null || array.size() == 0)
		{
			data = new QuadraticSpacePerfectHashing[0];
			return;
		}
		// Si le tableau contient un élément, on crée une table à un élément
		// Et positionne cet élément à la première case
		if(array.size() == 1)
		{
			a = b = 0;
			data = new QuadraticSpacePerfectHashing[1];
			data[0] = (QuadraticSpacePerfectHashing<AnyType>)array.get(0);	
			return;
		}

		// Si la taille du tableau > 1
		data = new QuadraticSpacePerfectHashing[array.size()];
		// On génère a et b
		a = generator.nextInt(p-1)+1;
		b = generator.nextInt(p);
		for (AnyType i : array)
		{
			// Pour chaque élément du tableau on calcul sa position
			int hashCode = i.hashCode();
			if(hashCode >= p) return;
			int pos = ((a*hashCode+b)%p)%array.size();
			// Si la case est vide
			if(null == data[pos])
			{
				// On crée un nouveau QuadraticSpacePerfectHashing array auquel on ajoute l'élément
				ArrayList<AnyType> perfectArray = new ArrayList<AnyType>();
				perfectArray.add(i);
				data[pos] = new QuadraticSpacePerfectHashing<AnyType>();
				data[pos].SetArray(perfectArray);
			}	
			else
			{
				// Sinon on récupère les élèments du QuadraticSpacePerfectHashing
				ArrayList<AnyType> perfectArray = new ArrayList<AnyType>();
				for(AnyType item : data[pos].items)
				{	
					if(null != item){
						perfectArray.add(item);
					}	
				}
				// Auxquels on ajoute l'élément à ajouter et on recalcule leurs positions
				perfectArray.add(i);
				data[pos].SetArray(perfectArray);
			}
		}
	}
	
	/**
	 * Retourne la taille de la table
	 */
	public int Size()
	{
		if( data == null ) return 0;

		int size = 0;
		for(int i=0; i<data.length; ++i)
		{
			size += (data[i] == null ? 1 : data[i].Size());
		}
		return size;
	}

	/**
	 * Vérifie si l'élément est bien dans la table
	 */
	public boolean contains(AnyType x)
	{
		// Recalcul de la position
		int m = data.length;
		int pos = ((a*(x.hashCode())+b)%p)%m;
		
		// Si l'élément est bien dans le QuadraticSpacePerfectHashing indiqué à cette place
		if(null != data[pos] && data[pos].contains(x))
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
		int m = data.length;
		// Puis on va chercher dans le QuadraticSpacePerfectHashing
		return data[((a*(x.hashCode())+b)%p)%m].getItem(x);	
	}
	
	/**
	 * Supprime l'item s'il est dans la table
	 */
	public void remove (AnyType x) {
		int m = data.length;
		// Recalcule de la position et on le supprime s'il y est
		if(null != data[((a*(x.hashCode())+b)%p)%m])
		{
			data[((a*(x.hashCode())+b)%p)%m].remove(x);
		}	
	}
}
