package probleme2;

import probleme2.MyHashMap.Entry;

public class DoubleHashingTable<AnyType> {
	private static final int DEFAULT_TABLE_SIZE = 3;
    private Entry<AnyType, AnyType>[ ] array; // The array of elements
    private int currentSize;              // The number of occupied cells
    
	public DoubleHashingTable()
	{
		this(DEFAULT_TABLE_SIZE);
	}
	
	public DoubleHashingTable( int size )
    {
		array = new Entry[size];
		currentSize = 0;
        makeEmpty();
    }
	
    private int getPrime(int arraySize)
    {
        for (int i = arraySize - 1; i >= 1; i--)
        {
            int fact = 0;
            for (int j = 2; j <= (int) Math.sqrt(i); j++)
            {	
                if (i % j == 0)
                {
                    fact++;
                }
            }
            if (fact == 0)
            {
                return i;
            }
        }
        return 3;
    }
    
    private void makeEmpty()
    {
        currentSize = 0;
        for( int i = 0; i < array.length; i++ )
        {
            array[ i ] = null;
        }
    }
    
    public void insert(Entry x)
    {         
        int hash1 = myhash1(x.key);
        int hash2 = myhash2(x.key);        
       
        while (array[hash1] != null)
        {
            hash1 += hash2;
            hash1 %= array.length;
        }
        array[hash1] = new Entry(x.key, x.value);     
        if( ++currentSize > array.length / 2 )
            rehash( );
    }
    
    private int myhash1( Object key )
    {
        int hashVal = key.hashCode( );

        hashVal %= array.length;
        if( hashVal < 0 )
            hashVal += array.length;

        return hashVal;
    }
    
    private int myhash2( Object key )
    {
        int hashVal = key.hashCode( );

        hashVal %= array.length;
        if( hashVal < 0 )
            hashVal += array.length;
        
        int r = getPrime(array.length);

        return r - hashVal % r;
    }
    
    public Entry get(Object key) 
    {
        int hash1 = myhash1( key );
        int hash2 = myhash2( key );
 
        while (array[hash1] != null && !array[hash1].key.equals(key))
        {
            hash1 += hash2;
            hash1 %= currentSize;
        }
        return array[hash1];
    }
    
    /**
     * Expand the hash table.
     */
    private void rehash( )
    {
        Entry[ ] oldArray = array;

            // Create a new double-sized, empty table
        array = new Entry[ 2 * oldArray.length ];
        currentSize = 0;

            // Copy table over
        for( int i = 0; i < oldArray.length; i++ )
            if( oldArray[ i ] != null)
                insert( oldArray[ i ]);
    }
}
