# Blackjack-Simulator
Blackjack...

# Rationale

Originally, when I had first begun coding this, the process in which I began coding was mayhem. In basic terms, I just started to code. I didn't provide myself with any organization, resulting in an extremely large main method with very little organization, completely disregarding strategically designed methods, well designed objects, and proper designation of security to private and public variables. As a result of this organization problem, I underestimated my ability to produce the second milestone before the time was up. I had found myself "married to my code", a spouse that was completely immutable to error checking or new features or, at its basest, fixing old features. This failure instigated the difficult decision I had to make. My ultimate decision was to throw away my entire source and start off from a completely fresh, more organized plate.


I started this quest for organization by grabbing the two essential elements to coding that I had completely ignored: a pencil and paper. At that moment, I asked myself what exactly made up blackjack. Well, there's a Blackjack game obviously. Blackjack has players. Players have hands. Hands have cards. Cards come from a deck. These, naturally, would be my classes.


From then on, I went off of the methodology of modular pieces. I wanted to make my code have a super small main line that was mainly run from callable methods. These callable methods would make debugging and error fixing much easier to accomplish, narrowing down the problems. Plus, the modular arrangement would make everything easier to understand. Simple method names, such as checkInsurance or playhand, would allow coders to better understand, implement, and change my framework, rather than trying to decipher a hodgepodge of code in a main line. I believe I was successfully able to accomplish that. Allow me to demonstrate. 


My main line starts with:

Player[] players = playerRegistration();
Player dealer = createDealer();


Someone who doesnt' understand coding would see that this is simply creating the playeres and creating the dealer. It was a very, very important thing for me to make the dealer a player, but not consider it in the same array as the players registering. This, in the long run, allowed me to have an extremely easy time implementing the very different procedures for players and the dealer. 
Following this, I ask if they would like to play? If they answer yes, I call method playHand(). This is the important content within playHand.


placeBets(players);	//give players chance to place bets on their hands
deal(players,dealer);	//deal players cards to their hands
checkInsurance(players,dealer);	//check if the dealer has an ace and, if so, allow players to take insurance
checkSplitting(players);	//check if the players can split and, if so, allow them to split their hand for the cost of another bet of equal value
checkDoubling(players);	//check if the player can double based on his bank and, if so, allow them to double up
hitting(players);	//give the players a chance to hit their hands with another card based on their current hand values until they go over 21
dealerHitting(dealer);	//since the dealer hitting process is different, use different method to hit dealer last
payOut(players,dealer);	//pay out winnings or state losses to players
refreshHands(players,dealer);	//make players discard their hands in preparation for the next round


After these methods pass, the program returns to the main, during which it asks whether another round should be played. Based on the answer of the player(s), the game stops or continues.


This is pretty much my main algorithim for blackjack, maintaining the importance of organization and readibility. I simply give the players array and dealer to a bunch of methods, each designated to use the players array/dealer to perform different tasks of the blackjack round. This is much easier than having to rewrite multiple things again and again in a main line. One important to mention is that the deck is created before the main method begins, but it is reset and shuffled every time the deal() main method is started in order to ensure players will receive unique cards every round. The classes/objects Player, Hand, Deck, and Card all have different methods within them to help the methods within Blackjack.java to perform their operations.


One method that was also present within Blackjack was the Hint method. This method provides basic probability information for hitting. This information is static and based off of the total value of the hand at the specific moment. Other hints are printed when certain methods are called. For example, if checkSplitting() is called and passes, only then does the System output my hint for what splitting is and what it does. 


One of the most irritating implemenations was the value of the ace. First of all, it seemed very ambiguous at how many aces you could instate to one or eleven. Based off of most replies on the internet and forums galore, I narrowed down what seems to be the fact that you can only have one ace that is a 1. In that case, my program does just that. Aces are automatically instated as 11. If the hand, at any point, exceeds 21 after a card is dealt or hit, once ace is automatically demoted to 1, in which the program then asks continues to ask for hits. I am still not sure whether or not this is the correct procedure in blackjack and all aces can change values whenever. However, this was based on my research and inexperience with blackjack. The modular design of my program, however, was critical to the implementation of this, as I don't know how I would have sifted through a main barrage of code to change such small and dependent instances. The algorithim works in a slightly complicated way. Every time a card is dealt into a hand, it is checked if it is an ace. If it is an ace, it checks through the hands value to see if the ace's value changes. This procedures occurs every time another card is dealt into the hand. If an ace does exist and the handvalue exceeds over 21, the ace is changed to a 1 and the hitting continues. After that, the value of the ace and any other aces dealt into the hand remain static.


I did not consider the dealer's bank in this, as a casino's bank normally does not die out. However, the code exists to implement this feature for home games if necessary, due to the convenience of the player class. Code also exists to allow players to play multiple hands on one table, but it is not initiated.


Overall, this new source is a huge step up from my first attempt. I have learned a HUGE amount about object oriented programming and pragmatics from this project. The most surprising thing was how much I enjoyed doing it.


# Rules

You begin blackjack by stating the number of players. Each player instantiates his name and bank. Then, the round begins.

The players are asked how much they would like to bet on their hand.

A deck is shuffled and two cards are dealt into the hand of each player. The players do not play against each other. Instead, they play against the dealer, who receives one card face up and one card face down. 

Each player's hand is then checked for advanced features. 

First, insurance is checked. Insurance can be taken whenever the dealer's face up card is an ace. In the event of such an occurence, the player has the opportunity to take half his bet. They can refuse this and continue through the round.

Then, each player's hand is checked for splitting. Splitting can occur when the two cards in a player's hand have the same face. In this case, the player can choose to split the hand into two seperate hands. In order to do this, the player must have enough money in the bank to bet an equal amount on the second hand. After the hand is split, a second card is dealt into each new hand.

Next, a player is asked whether or not he would like to double down. In this case, the player agrees to double his bet while committing to receive one card and stand afterwards. 

After the advanced features are checked, the hitting begins. No, not physical hitting, card hitting. The player is asked whether he would like to hit his hand with another card from the deck. Based on the hand value of their current two cards, the player can choose to hit or stand/committ to his current hand score. The object of hitting is to get the hand value as close to 21 as possible without going over 21, the best possible value. After the player exceeds 21, the player's hand busts, or loses. The player automatically loses his bet and loses the hand.

The dealer goes last after every player. The dealer must hit until his hand value exceeds 16. After his hand value exceeds 16, the dealer automatically stands. The dealer has the ability to bust his hand, in which case every player hand that has not busted wins.

After the dealer goes, the dealer's hand value is compared to the value of the players' hands. If the player's hand exceeds the value of the dealer's hand without busting, the player wins. If it is not greater than the dealer's hand value, the player loses. This situation marks the end of the round.

This process continues until the bank of the player or the dealer runs dry, in which case the game ends. The player can choose to quit at any time before they have placed a bet on a hand. Certain strategies and probabilities exist that can provide information on whether or not a certain move is the right move. 