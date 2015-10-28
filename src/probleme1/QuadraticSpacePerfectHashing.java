package probleme1;

import java.util.ArrayList;
import java.util.Random;

public class QuadraticSpacePerfectHashing<AnyType> 
{
	static int p = 46337;

	int a, b;
	AnyType[] items;

	QuadraticSpacePerfectHashing()
	{
		a=b=0; items = null;
	}

	QuadraticSpacePerfectHashing(ArrayList<AnyType> array)
	{
		AllocateMemory(array);
	}

	public void SetArray(ArrayList<AnyType> array)
	{
		AllocateMemory(array);
	}

	public int Size()
	{
		if( items == null ) return 0;

		return items.length;
	}

	public boolean contains(AnyType x)
	{
		// A completer
		int m = items.length;
		int pos = ((a*(x.hashCode())+b)%p)%m;
		
		if(null != items[pos] && items[pos].equals(x))
		{
			return true;
		}
		return false;
			
	}

	public AnyType getItem (AnyType x) {
		// A completer
		int m = items.length;
		return items[((a*(x.hashCode())+b)%p)%m];
	}

	public void remove (AnyType x) {
		// A completer
		int m = items.length;
		items[((a*(x.hashCode())+b)%p)%m] = null;
	}


	@SuppressWarnings("unchecked")
	private void AllocateMemory(ArrayList<AnyType> array)
	{
		Random generator = new Random( System.nanoTime() );

		if(array == null || array.size() == 0)
		{
			// A completer
			items = (AnyType[]) new Object[0];
			return;
		}
		if(array.size() == 1)
		{
			// A completer	
			a = b = 0;
			items = (AnyType[]) new Object[1];
			items[0] = array.get(0);		
			return;
		}

		do
		{
			items = null;

			// A completer
			a = generator.nextInt(p-1)+1;
			b = generator.nextInt(p);
		}
		while( collisionExists( array ) );
	}

	@SuppressWarnings("unchecked")
	private boolean collisionExists(ArrayList<AnyType> array)
	{
		// A completer
		int m = array.size()*array.size();
		items = (AnyType[]) new Object[m];
		if(array.size()==0)
		{
			System.out.println("test");
		}
		for(AnyType i : array)
		{	
			int hashCode = i.hashCode();
			if(hashCode >= p) return true;
			int pos = ((a*hashCode+b)%p)%m;
			if (items[pos] == null)
			{
				items[pos]=i;
			}
			else return true;
		}

		return false;
	}
}
