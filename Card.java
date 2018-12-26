/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GKatz
 */
public class Card {
    // Card suits (provided for your convenience - use is optional)
	public static final int SPADES   = 0;
	public static final int HEARTS   = 1;
	public static final int CLUBS    = 2;
	public static final int DIAMONDS = 3;

	// Card faces (provided for your convenience - use is optional)
	public static final int ACE      = 1;
	public static final int TWO      = 2;
	public static final int THREE    = 3;
	public static final int FOUR     = 4;
	public static final int FIVE     = 5;
	public static final int SIX      = 6;
	public static final int SEVEN    = 7;
	public static final int EIGHT    = 8;
	public static final int NINE     = 9;
	public static final int TEN      = 10;
	public static final int JACK     = 11;
	public static final int QUEEN    = 12;
	public static final int KING     = 13;


	// define fields here
	private int suit;
	private int face;
	private boolean faceUp;
        private int _value;
	
	// This constructor builds a card with the given suit and face, turned face down.
	public Card(int cardSuit, int cardFace)
	{
		suit = cardSuit;
                face = cardFace;
		faceUp = true;
                _value = determineValue();
	}

	// This method retrieves the suit (spades, hearts, etc.) of this card.
	public int getSuit()
	{
		return suit;
	}
	
	// This method retrieves the face (ace through king) of this card.
	public int getFace()
	{
		return face;
	}
	
	// This method retrieves the numerical value of this card
	// (usually same as card face, except 1 for ace and 10 for jack/queen/king)
	public int determineValue()
	{
            int cardValue;
            
		if ( face == 11 || face == 12 || face == 13 )
                    cardValue = 10;
                else if ( face == 1 )
                    cardValue = 11;
		else
                    cardValue = face;
                
            return cardValue;
	}
        
        public int getValue()
        {
            return _value;
        }
        
        public void setValue(int value)
        {
            _value = value;
        }
	
	public void flipUp()
	{
		faceUp = true;
	}
	
	public void faceDown()
	{
		faceUp = false;
	}
	
	public boolean faceTest()
	{
		return faceUp;
	}
	
	public String toString()
	{	
		String card = "";
		
		String suitString = "";
		if ( suit == 0 )
			suitString = "Spades";
		if ( suit == 1 )
			suitString = "Hearts";
		if ( suit == 2 )
			suitString = "Clubs";
		if ( suit == 3 )
			suitString = "Diamonds";
		
		String faceString = "";
		if (  face <= 10 && face != 1 )
			card = face + " of " + (suitString);
		else if ( face > 10 || face == 1 )
		{
			if ( face == 1)
				faceString = "Ace";
			if ( face == 11 )
				faceString = "Jack";
			if ( face == 12 )
				faceString = "Queen";
			if ( face == 13 )
				faceString = "King";
			
			card = faceString + " of " + (suitString);
		}
		
		if ( faceUp == false )
			card = "Card Faced Down";
		
		return card;
	}
}
