/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GKatz
 */
public class Hand
{
    
    private int _total = 0;
    private Card[] _cards;
    public double bet = 0;
    public boolean isSurrendered = false;
    private boolean _isDoubleDown = false;
    private boolean _wantsSplit = true;
    private int _value = 0;
    private boolean _aceTriggered = false;
    
    public Hand()
    {
        _cards = new Card[21];
        for ( int i = 0 ; i < _cards.length; i++ )
	{
            _cards[i] = null;
	}
    }
       
    /*public double getBet()
    {
        return _bet;
    }
    
    public void setBet(double bet)
    {
        _bet = bet;
    }*/
    
    public int total()
    {
        int handTotal = 0;
        
        for (int i = 0; i < _cards.length; i++)
        {
            if ( _cards[i] != null )
                handTotal += _cards[i].getValue();
        }
        
        return handTotal;
    }
    
    public Card getCard(int i)
    {
        return _cards[i];
    }
    
    public boolean getDoubleDown()
    {
        return _isDoubleDown;
    }
    
    public void doubleDown()
    {
        _isDoubleDown = true;
    }
    
    public void addCard(Card card)
    {
        for ( int i = 0; i < _cards.length; i++ )
        {
            if ( _cards[i] == null )
            {
                _cards[i] = card;
                if ( hasAce() == true && _aceTriggered == false )
                {
                    setAceValue();
                }
                break;
            }
        }
    }
    
    public Card removeCard(int i)
    {;
        Card holder = new Card(_cards[i].getSuit(),_cards[i].getFace());
        _cards[i] = null;
        return holder;
    }
    
    public boolean isBusted()
    {        
        return (this.total() > 21) ? true : false;
    }
    
    public boolean isBlackjack()
    {        
        return (this.total() == 21) ? true : false;
    }
    
    public String toString()
    {
        int counter = 0;
        if ( isSurrendered == false )
        {
            StringBuilder sb =  new StringBuilder();

            for (int i = 0; i < _cards.length; i++)
            {
                if ( _cards[i] != null )
                {
                   sb.append(_cards[i].toString());
                   sb.append(System.lineSeparator());
                   if (_cards[i].faceTest() == true)
                       counter++;
                }
            }
            if ( counter > 1 )
            {
                sb.append("Hand Value: ");
                sb.append(total());
                sb.append(System.lineSeparator());
            }
            return sb.toString();
        }
        else
            return "";
    }
    
    public boolean isSplittable(double bank)
    {
        int cards = 0;
        for (int i = 0; i < _cards.length; i++)
        {
            if (_cards[i] != null )
                cards++;
        }
        return ((_wantsSplit == true ) && (_cards[0].getFace() == _cards[1].getFace()) &&  (cards == 2) && (this.bet <= bank) && (isSurrendered == false));
    }
    
    public boolean canTakeInsurance()
    {
        int cards = 0;
        
        for ( int i = 0; i < _cards.length; i++ )
        {
            if ( _cards[i] != null )
                cards++;
        }
        
        return ((_cards[1].getFace() == 1) && (_cards[1].faceTest() == true) && (cards == 2));
    }
    
    public boolean canDoubleDown(double bank)
    {
        int cards = 0;
        for (int i = 0; i < _cards.length; i++)
        {
            if (_cards[i] != null )
                cards++;
        }
        return ( (cards == 2) && (this.bet <= bank) && (isSurrendered == false) );
    }
    
    public void noMoreSplit()
    {
        _wantsSplit = false;
    }
    
    public static boolean hasSplittableHand(Hand[] hands, double bank)
    {
        for ( int i = 0; i < hands.length; i++ )
        {
            if (hands[i].isSplittable(bank) == true)
                return true;
        }
        return false;
    }
    
    public void setAceValue()
    {
        int value = total();
        
        if ( _aceTriggered == false )
        {
            int aceCard = -1;
            for ( int i = 0; i < _cards.length; i++ )
            {
                if ( _cards[i] != null )
                {
                    if ( _cards[i].getFace() == 1 )
                    {
                        aceCard = i;
                        break;
                    }
                }
            }

            if ( value > 21 && aceCard != -1 )
            {
                _cards[aceCard].setValue(1);
                _aceTriggered = true;
            }
        }
    }
    
    public boolean hasAce()
    {
        for ( int i = 0; i < _cards.length; i++ )
        {
            if ( _cards[i] != null )
            {
                if ( _cards[i].getFace() == 1 )
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isPlayable()
    {
        if ( isSurrendered == false && isBusted() == false )
            return true;
        
        return false;
    }

}
