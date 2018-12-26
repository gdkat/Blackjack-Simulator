/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GKatz
 */
public class Blackjack 
{

    /**1
     * @param args the command line arguments
     */
    private static Deck d = new Deck();
    
    public static void main(String[] args) 
    {
        Player[] players = playerRegistration();
        Player dealer = createDealer();
        
        System.out.println("Ready to play?");
        boolean ready = IO.readBoolean();
        while ( ready == true )
        {
            playHand(players,dealer);
            System.out.println("Play another round?");
            ready = IO.readBoolean();
        }
        //System.out.println(players[i].toString());
        System.out.println("THANKS FOR PLAYING! COME BACK SOON!");
     }
    
    public static Player[] playerRegistration()
    {
        int playerCount;
	do
	{
            System.out.println("Enter a valid number of players (Max of 6)");
            playerCount = IO.readInt();
	} while ( playerCount < 1 || playerCount > 6 );
        
        int playerNum = 0;
	Player[] players = new Player[playerCount];
	do
	{
            System.out.println("What is player "+ (playerNum + 1) + "'s name?" );
            String name = IO.readString();
            System.out.println("What is " + (name) + " 's starting bank value in US dollars?");
            double bank = IO.readDouble();
            players[playerNum] = new Player(name, bank, 1);
            playerNum++;
        } while ( playerNum < playerCount );
		
        System.out.println("Listing Players: ");
        for ( int i = 0; i < playerCount ; i++ )
        {
            System.out.println(players[i].getName() + "		$" + (players[i].bank));
	}
        
        return players;
    }
    
    public static Player createDealer()
    {
        Player dealer = new Player("Dealer", 1000000000, 1);
        return dealer;
    }
    
    public static void playHand(Player[] players, Player dealer)
    {
        placeBets(players);
        deal(players,dealer);
        for ( int i = 0; i < players.length; i++ )
        {
            System.out.println(players[i].toString()); 
        }
        System.out.println(dealer.toString());
        checkInsurance(players,dealer);
        checkSplitting(players);
        checkDoubling(players);
        hitting(players);
        dealerHitting(dealer);
        payOut(players,dealer);
        refreshHands(players,dealer);
        for ( int i = 0; i < players.length; i++ )
        {
            System.out.println(players[i].toString()); 
        }
    }
    
    public static void placeBets(Player[] players)
    {
        for (int i = 0; i < players.length; i++)
        {
            for (int j = 0; j < players[i].getHands().length; j++)
            {
                System.out.println("How much would " + (players[i].getName()) +" like to bet on this hand?");
                double bet = IO.readDouble();
                bet = players[i].decreaseBankRoll(bet);
                Hand[] hands = players[i].getHands();
                hands[j].bet = bet;
                System.out.println();
            }
        }
    }
    
    public static void deal(Player[] players, Player dealer)
    {
        d = new Deck();
        d.shuffle();
        for (int i = players.length-1 ; i > -1 ; i-- )
        {
            for (int j = 0; j < players[i].getHands().length; j++)
            {
                Hand[] hands = players[i].getHands();
                hands[j].addCard(d.deal());
                hands[j].addCard(d.deal());
            }
        }
        Hand[] dealerHand = dealer.getHands();
        dealerHand[0].addCard(d.deal());
        dealerHand[0].getCard(0).faceDown();
        dealerHand[0].addCard(d.deal());
    }
    
    public static void checkSplitting(Player[] players)
    {
        for ( int i = 0 ; i < players.length ; i++ )
        {
            Hand[] hands = players[i].getHands();
            while ( Hand.hasSplittableHand(hands, players[i].bank) )
            {
                for (int j = 0; j < hands.length; j++)
                {
                    if ((hands[j].isSplittable(players[i].bank)))
                    {
                        System.out.println("Hand " + (j+1) + " is eligible to split.");
                        System.out.println("Would you like to split?");
                        boolean split = IO.readBoolean();
                        if (split == true)
                        {
                            Hand[] splitHands = players[i].split(j);
                            for ( int k = 0; k < splitHands.length; k++ )
                            {
                                splitHands[k].addCard(d.deal());
                            }
                            System.out.println(players[i].toString());
                        }
                        else
                        {
                            hands[j].noMoreSplit();
                        }
                    }
                }
                hands = players[i].getHands();
            }
        }
    }
    
    public static void checkInsurance(Player[] players, Player dealer)
    {
        for ( int i = 0; i < players.length; i++ )
        {
            Hand[] playerHands = players[i].getHands();
            Hand dealerHand = dealer.getHands()[0];
            if ( dealerHand.canTakeInsurance() == true)
            {
                System.out.println("Since dealer has an Ace face up, you have the opportunity to take insurance.");
                System.out.println("Would you like to take insurance?");
                boolean insuranceAvailable = IO.readBoolean();
                if ( insuranceAvailable == true )
                {
                    for ( int j = 0; j < playerHands.length; j++ )
                    {
                        System.out.println("Would you like to take insurance on hand " + (j+1) + "?");
                        boolean wantInsurance = IO.readBoolean();
                        if (wantInsurance == true)
                        {
                            players[i].takeInsurance(playerHands[j]);
                            System.out.println(playerHands[j].bet/2 + " has been returned to your bank");
                        }
                    }
                }
            }
        }
    }
    
    public static void checkDoubling(Player[] players)
    {
        for ( int i = 0; i < players.length; i++ )
        {
            Hand[] playerHands = players[i].getHands();
            System.out.println("Would " + (players[i].getName()) +" like to double down? Current Bank: " + (players[i].bank));
            boolean wantDouble = IO.readBoolean();
            if ( wantDouble == true )
            {
                for ( int j = 0; j < playerHands.length; j++ )
                {
                    if ( playerHands[j].isSurrendered == false )
                    {
                        if ( playerHands[j].canDoubleDown(players[i].bank) == true )
                        {
                            System.out.println("Double down hand " + (j+1) + "? Current bet on hand: " + (playerHands[j].bet));
                            boolean doubling = IO.readBoolean();
                            if ( doubling == true )
                            {
                                players[i].doubleDown(playerHands[j]);
                            }
                        }
                        else
                            System.out.println("You do not have enough bank to double down hand " + (j+1));
                    }
                }
            }
        }
    }
    
    public static void hitting(Player[] players)
    {
        for ( int i = 0; i < players.length; i++ )
        {
            Hand[] playerHands = players[i].getHands();
            for ( int j = 0; j < playerHands.length; j++ )
            {
                if ( playerHands[j].isSurrendered == false && playerHands[j].getDoubleDown() == false )
                {
                    hint(playerHands[j]);
                    System.out.println(players[i].getName() + ", would you like to hit hand " + (j + 1) + "?");
                    boolean wantHit = IO.readBoolean();
                    while ( wantHit == true)
                    {
                        playerHands[j].addCard(d.deal());
                        System.out.println(players[i].toString());
                        if ( playerHands[j].isBusted() == false )
                        {
                            hint(playerHands[j]);
                            System.out.println("Hit again?");
                            wantHit = IO.readBoolean();
                        }
                        else if ( playerHands[j].isBusted() == true )
                        {
                            wantHit = false;
                            System.out.println("BUSTED! You have lost this hand.");
                            System.out.println();
                        }
                    }
                }
                if ( playerHands[j].getDoubleDown() == true )
                {
                    System.out.println("You will receive one card for doubled down hand" + (j+1));
                    playerHands[j].addCard(d.deal());
                    System.out.println(players[i].toString());
                }
            }           
        }
    }
    
    public static void dealerHitting(Player dealer)
    {
        System.out.println("Dealers Turn:");
        Hand dealerHand = dealer.getHands()[0];
        dealerHand.getCard(0).flipUp();
        while ( dealerHand.total() < 17 )
            dealerHand.addCard(d.deal());
        System.out.println(dealer.toString());
    }
    
    public static void payOut(Player[] players, Player dealer)
    {
        Hand dealerHand = dealer.getHands()[0];
        for ( int i = 0; i < players.length; i++ )
        {
            Hand[] hands = players[i].getHands();
            for ( int j = 0; j < hands.length; j++)
            {
                if ( hands[j].isSurrendered == false )
                {
                    System.out.println("Dealer's " + (dealerHand.total()) + " vs " + (players[i].getName()) + "'s " + (hands[j].total()));
                    if ( (hands[j].total() > dealerHand.total() || dealerHand.isBusted() == true || hands[j].isBlackjack() == true ) && hands[j].isPlayable() == true )
                    {
                        System.out.println(players[i].getName() + " has earned $" + (hands[j].bet) + " for hand " + (j+1));
                        players[i].bank += hands[j].bet*2;
                    }
                    else if ( hands[j].total() == dealerHand.total() && hands[j].isPlayable() == true )
                    {
                        System.out.println("Push! Dealer hand and player hand " + (j + 1) + " are tied.");
                        System.out.println(players[i].getName() + "'s bet has been returned.");
                        players[i].bank += hands[j].bet;
                    }
                    else
                        System.out.println(players[i].getName() + " has lost hand " + (j + 1) + ". No money earned.");
                }
                System.out.println();
            }
        }
    }
    
    public static void refreshHands(Player[] players, Player dealer)
    {
        for ( int i = 0; i < players.length; i++ )
        {
            players[i].refreshHands(1);
        }
        dealer.refreshHands(1);
    }
    
    public static void hint(Hand hand)
    {
        int score = hand.total();
					
        if(score == 21)
                System.out.println("Hint: Blackjack!");
        if(score == 20) 
                System.out.println("Hint: 92 % chance you will bust if you hit.");
        if(score == 19)
                System.out.println("Hint: 85 % chance you will bust if you hit.");
        if(score == 18)
                System.out.println("Hint: 77 % chance you will bust if you hit.");
        if(score == 17)
                System.out.println("Hint: 69 % chance you will bust if you hit.");
        if(score == 16)
                System.out.println("Hint: 62 % chance you will bust if you hit.");
        if(score == 15)
                System.out.println("Hint: 54 % chance you will bust if you hit.");
        if(score == 14)
                System.out.println("Hint: 46 % chance you will bust if you hit.");
        if(score == 13)
                System.out.println("Hint: 38 % chance you will bust if you hit.");
        if(score == 12)
                System.out.println("Hint: 31 % chance you will bust if you hit.");
        if(score <= 11)
                System.out.println("Hint: No chance that you will bust if you hit.");
    }
}
