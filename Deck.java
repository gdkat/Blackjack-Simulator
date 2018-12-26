/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GKatz
 */
public class Deck {
    // define fields here
	private Card[] d;
	
	// This constructor builds a deck of 52 cards.
	public Deck()
	{
		d = new Card[52];
		int i = 0;
		while ( i < d.length )
			for ( int j = 0 ; j < 4 ; j++)
				for ( int k = 1 ; k < 14 ; k++ )
				{
					d[i] = new Card(j,k);
					i++;
				}	
	}

	
	// This method takes the top card off the deck and returns it.
	public Card deal()
	{
            Card topCard = null;
            for( int i = 0 ; i < d.length ; i++ )
            {
		if(d[i] != null)
                {
                    topCard = d[i];
                    d[i] = null;
                    break;
		}
            }
            
            //Card topCard = new Card(0, 1);
            return topCard;
	}
	
	
	// this method returns true if there are no more cards to deal, false otherwise
	public boolean isEmpty()
	{
		boolean isEmpty = false;
		for ( int i = 0 ; i < d.length ; i++ )
		{
			if ( d[i] != null )
			{
				isEmpty = false;
			}
			else
			{
				isEmpty = true;
			}
		}
		return isEmpty;
	}
	
	//this method puts the deck int some random order
	public void shuffle()
	{
		for ( int i = 0 ; i < d.length ; i++ )
		{
			Card holder = d[i];
			int randomNum = (int)(Math.random()*51);
			d[i] = d[randomNum];
			d[randomNum] = holder;
		}
	}
}
