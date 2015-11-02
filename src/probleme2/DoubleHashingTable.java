package probleme2;

import probleme2.MyHashMap.Entry;

public class DoubleHashingTable<AnyType> {
	// On choisit DEFAULT_TABLE_SIZE = 3 par défaut
	private static final int DEFAULT_TABLE_SIZE = 3;
	// The array of elements
    private Entry<AnyType, AnyType>[ ] array;
    // The number of occupied cells
    private int currentSize;             
    
    /**
     *  Constructeur par défaut
     */
	public DoubleHashingTable()
	{
		// Appel du constructeur par paramètres
		this(DEFAULT_TABLE_SIZE);
	}
	
	/**
	 *  Constructeur par paramètres
	 */
	public DoubleHashingTable( int size )
    {
		array = new Entry[size];
		currentSize = 0;
		// On initialise toutes les cases à null
        makeEmpty();
    }
	
	/**
	 * Méthode qui retourne le plus grand nombre premier inférieur à la taille du tableau
	 */
    private int getPrime(int arraySize)
    {
    	// On teste pour arraySize - 1 (car on veut un nb premier inférieur à la taille)
    	// Et si on trouve pas on itère jusqu'à 1
        for (int i = arraySize - 1; i >= 1; i--)
        {
        	// On suppose que i est premier
        	boolean isPrime = true;
            // On teste la divisibilité de i par tous les nombre de 2 à racine(i)
            for (int j = 2; j <= (int) Math.sqrt(i) && isPrime; j++)
            {	
            	// S'il est divisible, il n'est pas premier
                if (i % j == 0)
                {
                	isPrime = false;
                }
            }
            if (isPrime)
            {
                return i;
            }
        }
        return 3;
    }
    
    /**
    * Initialisation de toutes les cases à null
    */
    private void makeEmpty()
    {
        currentSize = 0;
        for( int i = 0; i < array.length; i++ )
        {
            array[ i ] = null;
        }
    }
    
    /**
     * Méthode qui permet l'insertion d'une entrée
     */ 
    public void insert(Entry x)
    {        
    	// Calcul de H1 et H2
        int hash1 = myhash1(x.key);
        int hash2 = myhash2(x.key);        
       
        // Tant que la case donnée par H1 n'est pas vide (collision)
        while (array[hash1] != null)
        {
        	// On y rajoute H2 et fais son modulo
            hash1 += hash2;
            hash1 %= array.length;
        }
        // Une fois une case vide trouvée on insère là valeur et vérifie si on doit rehash
        array[hash1] = new Entry(x.key, x.value);     
        if( ++currentSize > array.length / 2 )
            rehash( );
    }
    
    /**
     * Calcul de H1
     */ 
    private int myhash1( Object key )
    {
        int hashVal = key.hashCode( );

        hashVal %= array.length;
        if( hashVal < 0 )
            hashVal += array.length;

        return hashVal;
    }
    
    /**
     * Calcul de H2
     */ 
    private int myhash2( Object key )
    {
        int hashVal = key.hashCode( );

        hashVal %= array.length;
        if( hashVal < 0 )
            hashVal += array.length;
        
        int r = getPrime(array.length);

        return r - hashVal % r;
    }
    
    /**
    * Permet de récupérer un objet dans la table
    */
    public Entry get(Entry x) 
    {
    	// Calcul de H1 et H2
        int hash1 = myhash1( x.key );
        int hash2 = myhash2( x.key );
        
        // Si on tombe sur une case occupée par un élément différent que la clé donnée
        while (array[hash1] != null && !array[hash1].key.equals(x.key))
        {
        	// On recalcule en ajoutant H2 et en faisant son modulo par rapport à la taille du tableau
            hash1 += hash2;
            hash1 %= array.length;
        }
        return array[hash1];
    }
    
    /**
     * Agrandissement de la table de hachage
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
    
    /**
     * Retoune le nombre d'éléments
     */
    public int nbElement()
    {
    	return currentSize;
    }
}
