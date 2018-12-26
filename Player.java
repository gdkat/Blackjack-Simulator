/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GKatz
 */
public class Player
{
    
    private String _name = "";
    public double bank = 0;
    private Hand[] _hands;
    
    public Player(String name, double bank, int hands)
    {
        _name = name;
	this.bank= bank;
        _hands = new Hand[hands];
        for (int i = 0; i < _hands.length; i++)
        {
            _hands[i] = new Hand();
        }
    }
    
    public String getName()
    {
        return _name;
    }
    
    public void addHand(Hand hand)
    {
        Hand[] temp = new Hand[_hands.length + 1];
        for ( int i = 0 ; i < _hands.length ; i++ )
        {
            temp[i] = _hands[i];
        }
        
        temp[_hands.length] = hand;
        
        _hands = temp;
        
    }
    
    public void setNumHands(int hands)
    {
        _hands = new Hand[hands];
    }
    
    public Hand[] getHands()
    {
        return _hands;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(this.getName());
        sb.append("		$");
        sb.append(this.bank);
        sb.append(System.lineSeparator());
        for ( int i = 0; i < _hands.length; i++ )
        {
            sb.append(_hands[i].toString());
            sb.append(System.lineSeparator());
        }
        
        return sb.toString();
    }
    
    public void increaseBankRoll(double bet)
    {
        bank += bet;
    }
    
    public double decreaseBankRoll(double bet)
    {
        double amountBet = 0;
        if ( bet <= bank )
        {
            bank -= bet;
            amountBet = bet;            
        }
        else
        {
            amountBet = bank;
            bank = 0;
            
        }
        
        return amountBet;
            
    }
    
    public Hand[] split(int j)
    {
        Hand k = new Hand();
        k.addCard(_hands[j].removeCard(1));
        
        this.addHand(k);
        
        Hand[] splitHands = new Hand[2];
        splitHands[0] = k;
        splitHands[1] = _hands[j];
        
        decreaseBankRoll(splitHands[1].bet);
        splitHands[0].bet = splitHands[1].bet;
        
        return splitHands;
    }
    
    public void takeInsurance(Hand playerHand)
    {
        increaseBankRoll(playerHand.bet/2);
        playerHand.isSurrendered = true;
    }
    
    public void doubleDown(Hand playerHand)
    {
        decreaseBankRoll(playerHand.bet);
        playerHand.bet = playerHand.bet*2;
        playerHand.doubleDown();
    }
    
    public void refreshHands(int hands)
    {
        _hands = new Hand[hands];
        for (int i = 0; i < _hands.length; i++)
        {
            _hands[i] = new Hand();
        }
    }
}
