package probleme1;

import java.util.Random;
import java.util.ArrayList;

public class LinearSpacePerfectHashing<AnyType>
{
	static int p = 46337;

	QuadraticSpacePerfectHashing<AnyType>[] data;
	int a, b;

	LinearSpacePerfectHashing()
	{
		a=b=0; data = null;
	}

	LinearSpacePerfectHashing(ArrayList<AnyType> array)
	{
		AllocateMemory(array);
	}

	public void SetArray(ArrayList<AnyType> array)
	{
		AllocateMemory(array);
	}

	@SuppressWarnings("unchecked")
	private void AllocateMemory(ArrayList<AnyType> array)
	{
		Random generator = new Random( System.nanoTime() );

		if(array == null || array.size() == 0)
		{
			// A completer
			data = new QuadraticSpacePerfectHashing[0];
			return;
		}
		if(array.size() == 1)
		{
			a = b = 0;
			// A completer
			data = new QuadraticSpacePerfectHashing[1];
			data[0] = (QuadraticSpacePerfectHashing<AnyType>)array.get(0);	
			return;
		}

		// A completer
		data = new QuadraticSpacePerfectHashing[array.size()];
		a = generator.nextInt(p-1)+1;
		b = generator.nextInt(p);
		for (AnyType i : array)
		{
			// A completer
			int hashCode = i.hashCode();
			if(hashCode >= p) return;
			int pos = ((a*hashCode+b)%p)%array.size();
			if(null == data[pos])
			{
				ArrayList<AnyType> perfectArray = new ArrayList<AnyType>();
				perfectArray.add(i);
				data[pos] = new QuadraticSpacePerfectHashing<AnyType>();
				data[pos].SetArray(perfectArray);
			}	
			else
			{
				ArrayList<AnyType> perfectArray = new ArrayList<AnyType>();
				for(AnyType item : data[pos].items)
				{	
					if(null != item){
						perfectArray.add(item);
					}	
				}
				perfectArray.add(i);
				data[pos].SetArray(perfectArray);
			}
		}
		/*
		System.out.println("Affichage final");
		for (QuadraticSpacePerfectHashing<AnyType> k : data)
		{
			if(k != null)
			{
				for(AnyType item : k.items)
				{
					if(null != item)
					{
						System.out.print(item +", ");
					}
				}
				System.out.println();
			}
		}
		*/
	}

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

	public boolean contains(AnyType x)
	{
		// A completer
		int m = data.length;
		int pos = ((a*(x.hashCode())+b)%p)%m;
		
		if(null != data[pos] && data[pos].contains(x))
		{
			return true;	
		}
		return false;
	}
	
	public AnyType getItem (AnyType x) {
		// A completer
		int m = data.length;
		return data[((a*(x.hashCode())+b)%p)%m].getItem(x);	
	}
	
	public void remove (AnyType x) {
		// A completer
		int m = data.length;
		if(null != data[((a*(x.hashCode())+b)%p)%m])
		{
			data[((a*(x.hashCode())+b)%p)%m].remove(x);
		}	
	}
}
