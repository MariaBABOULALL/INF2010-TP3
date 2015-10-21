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
			data = (QuadraticSpacePerfectHashing<AnyType>[]) new Object[0];
			return;
		}
		if(array.size() == 1)
		{
			a = b = 0;
			// A completer
			data = (QuadraticSpacePerfectHashing<AnyType>[]) new Object[1];
			data[0] = (QuadraticSpacePerfectHashing<AnyType>)array.get(0);	
			return;
		}

		// A completer
		data = (QuadraticSpacePerfectHashing<AnyType>[]) new Object[array.size()];
		for (AnyType i : array)
		{
			// A completer
			a = generator.nextInt(p-1)+1;
			b = generator.nextInt(p);
			int hashCode = i.hashCode();
			if(hashCode >= p) return;
			int pos = ((a*hashCode+b)%p)%array.size();
			if(null == data[pos])
			{
				ArrayList<AnyType> perfectArray = new ArrayList<AnyType>();
				perfectArray.add(i);
				data[pos].SetArray(perfectArray);
			}	
			else
			{
				ArrayList<AnyType> perfectArray = new ArrayList<AnyType>();
				for(AnyType item : data[pos].items)
				{
					perfectArray.add(item);
				}
				perfectArray.add(i);
				data[pos].SetArray(perfectArray);
			}
		}
		
		for (QuadraticSpacePerfectHashing<AnyType> k : data)
		{
			for(AnyType item : k.items)
			{
				if(null != item)
				{
					System.out.println(item);
				}
			}
		}
		

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
/*
	public boolean contains(AnyType x )
	{
		// A completer

	}
	
	public AnyType getItem (AnyType x) {
		// A completer
		
	}
	
	public void remove (AnyType x) {
		// A completer
		
	}*/
}
