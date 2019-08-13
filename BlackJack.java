/**
	*  Author: Valentinos Pariza
	*  Written: 22/10/2017
	*  Last updated: 30/10/2017
	*
	*  Compilation from source directory
	*  Compilation: javac -cp stdlib.jar BlackJack.java
	*  Execution: java -cp .:stdlib.jar BlackJack
	* 
    *  
	* 
	*  This game is a game created by image as the original  Blackjack game(21 called sometimes).It has many 
	*  similarities,although it has some unique differences.Player plays against dealer .his program asks for the 
	*  player's stake and by the laws of the original Blackjack game player starts playing just like the game of 
	*  Blackjack.Player takes initially in every game two starting cards which cards are shown to dealer,and dealer 
	*  takes two cards which only the one is being showed to player(program uses arrays for creating a deck with 52 
	*  cards and another array for the player's initial cards).After these player draw cards until he/she wants 
	*  to stop(cards are taken from the top of the array-deck).After these the program  plays the dealer by drawing 
	*  cards until pass value of 17 or being burned or have a blackjack.If the player and the dealer do not have 
	*  Natural Blackjacks,or Blackjacks,or being Burned,the program compares the value of cards which player and dealer
	*  holds.It uses various methods to complete its targets.At the end it calculates the winning,or the losing of 
	*  money or the fact of not losing or not winning any money.
	* 
	* 
	*
	*/



public class BlackJack {
	
public	static final int INITIAL_NUMBER_OF_CARDS=4;  //  constant which represents the starting cards which are given 
													 //  two for player and two for the dealer

public static final int PRINTING_SPACES=6;  //  Number of spaces which will be printed for classification of output 

public static String[] createShuffledeck() {
	 									//  this method creates an array with 52 positions for 52 types of cards,after
	 									//  these it shuffles their positions(in the array) and returns the array-deck 
	 
	String[] suit= { "Clubs", "Diamonds", "Hearts", "Spades" };
	String[] rank={ "2", "3", "4", "5", "6", "7", "8", "9","10", "Jack", "Queen", "King", "Ace"};
	int SUITS = suit.length; 
	int RANKS = rank.length; 
	int N = SUITS * RANKS; 
	 String[] deck = new String[N];
	 
	 for (int i = 0; i < RANKS; i++)      
		 for (int j = 0; j < SUITS; j++) 
			 deck[SUITS*i + j] = rank[i] + " of " + suit[j]; 
	 
	 for (int i = 0; i <= 100000; i++){ 
		 int c1 = (int) (Math.random() * N); 
		 int c2 = (int) (Math.random() * N); 
		 String t = deck[c1]; 
		 deck[c1] = deck[c2]; 
		 deck[c2] = t; }
	 return deck;
	 }



public static int calcState(int sum,int aces) {
	//  this method takes the sum of the cards which are not aces and the number of aces.Estimates if there is a   
	//  blackjack or if there is burn or nothing of these and returns symbolic numbers (1 if is a blackjack, -1 if
	//  it is burn ,0 if he do not have nothing of these
	 
	if (aces>=1) {
		if ((sum+11+(aces-1)==21)) return 1;}			//  if there are more than one holding aces,it searches if there 
	    												//  is a blackjack by considering one ace as 11 and the others 
														//  as 1.Considering two aces as 11 is not possible((11+11)>21)
										
	if ((sum+aces)>21) return (-1);						
		else if ((sum+aces)==21) return 1;				//  Estimates if there is a blackjack or a burn ,or nothing 
		else return 0;									//  by considering all aces as 1 (if there are) 
	}


public static void newlines(int lines) {  //  prints a number of lines which is given
for(int i=1;i<=lines;i++) StdOut.println();	 }


public static void printspaces(int spaces) {     //  prints a number of spaces which is given 
	for(int i=1;i<=spaces;i++)StdOut.print(" ");  }


public static int validEndChoice() { //  prints the message of continuing or stopping the game and asks with an input 
									 //  the player's decision.Also checks if the input is a number and 1 or 2
	String choice;                 
	int choicein=0;
	do {
	StdOut.println("Do you want to continue?");
	  printspaces(PRINTING_SPACES);
	StdOut.println("1.CONTINUE");
	  printspaces(PRINTING_SPACES);
    StdOut.println("2.STOP");
    choice=StdIn.readLine();
    
    while(!(isNumber(choice))) {											//  checks if input  contains only numbers
    	StdOut.println("Your choices are (1) or (2) ,not a String ");
    	choice=StdIn.readLine();                                           //  use String for choice input
    }
    
    choicein=Integer.parseInt(choice.trim());   			//  take the String number and turn it into to an integer
    														//  after removing the spaces before and after the number
      newlines(2);
	}while((choicein!=1)&&(choicein!=2));
	
  return choicein;										//returns the correct choice of player('1' or '2') 
}


public static String giveCard(String[] deck) {  //  takes the next card of the top of the deck(array) and gives it 
	
	String card="";
	for(int i=0;i<=(deck.length-1);i++) {
		    if(deck[i]!="E") {					//  takes the next available card from the top of the deck(array) 
			  card=deck[i];						//  (the card which is not "E"(Empty))and replace its position with the 
			  deck[i]="E";						//  "E"(Empty)
			  return card;  }
			}
	return card;
	}


public static int valueOfCard(String card) { //  takes the name of a card (String),calculate its value and return it 
											 //	 back
	
	char firstLetter=card.charAt(0);           //  recognize cards by its unique first character of String 
	int value=0;
	
	if ((firstLetter>='2')&&(firstLetter<='9')) value=(int)(firstLetter-'0'); // if starts from a number turns into the
	else if (firstLetter=='1') value=10;      								  // character to number directly 		
	else if ((firstLetter=='J')||(firstLetter=='Q')||(firstLetter=='K')) value=10;	//  if it is a letter(J,Q,K) or if
	else if(firstLetter=='A') value=1;												//  starts with 1(symbolizes 10) 
																					//  returns 10 
	return value;																	//   if is an ace returns 1

}


public static int displayMainChoices(int counter) {
												   //  print main choices (hit,stand,double down,surrender if  
	int action=0;								   //  it is available) and asks for the player's choice.Also 
	String choice;								   //  checks if the answer(from input),choice is a number and 
												   //  if is 1 or 2 or 3 or 4 when 4 is available by checking a counter
	do {											
	StdOut.println("Please select one of the following actions:");
	   printspaces(PRINTING_SPACES);
	StdOut.println("1.Hit");
	   printspaces(PRINTING_SPACES);
	   StdOut.println("2.Stand");
	   printspaces(PRINTING_SPACES);
    StdOut.println("3.Double Down");
    
    if (counter==0) {
    	printspaces(PRINTING_SPACES);
    	StdOut.println("4.Surrender");}
    
    StdOut.print("Enter Chosen action: ");
    choice=StdIn.readLine();							//  take choice as a String
    
    
    while(!(isNumber(choice))) {                    	//  checks if it contains only digits(if is a number)
    	newlines(1);
    	StdOut.println("(actions limited to '1' or '2' or'3' or '4',when you can surrender)");
    	StdOut.print("Enter Chosen action:  ");
    	choice=StdIn.readLine();                  
    	}
    
   
    action=Integer.parseInt(choice.trim());					//  take the String number and turn it into to an integer
    														//  after removing the spaces before and after the number
    if((counter>0)&&(action==4)) {	
    StdOut.println("You can not Surrender");
       action=-1;       }								    //  If the counter>0,which means he can not take the choice 
    														//  of Surrender then he asks for another number and prints
    														//  a message for that.Also change the number to a negative 
    														//  in order to ask again for a number which is betwenn 
      														//  1-3
	}while((action<1)||(action>4));
    
    return action;
}


public static double validStake() {
	//  asks for the stake which player wants to use and checks if the stake is actually a number and
	//  greater than 0. If not it asks again for other input until the conditions are true 
	
	double stake;
	do{StdOut.print("Enter your stake in Euro: ");
	String ans =StdIn.readLine();						//  take input-stake as a String
	
	while(!(isNumber(ans))) {							//  checks if the String input-stake contains only numbers
		StdOut.println("You have to give a valid positive number,not String (stake>=0)");  //  positive numbers are 
		 ans=StdIn.readLine();															   //  checked in method	
	}																					   //  isNumber		
	
	stake=Double.parseDouble(ans);					//  removes the spaces before and after the number(String)  
													//  and turn the number into a Double number
	 
		
		
	 
	}while(stake<=0 );
	
	return stake;
}


public static double opportunityReducingStake(String dealercard1,double stake) 
{//  if the first revealed card of dealer is an ace then with this method ,player has the opportunity to reduce its 
 //  stake up to half(because maybe dealer has a Natural Blackjack).It prints the appropriate messages for instructing
 //  player to give a correct reduction(if player wants).If the reduction is not correct(less than zero or greater 	
 //  than the half of the stake) it asks again for other number.Also checks if the input is a number and asks until 	
 //  player give a number		
	
	double reduction=0;
	if (valueOfCard(dealercard1)==1) {
		String answer;
		do {
		 StdOut.print("Do you want to reduce your stake up to half of your initial? (Y/N): ");
		 answer=StdIn.readLine();
		 newlines(1);
		}while(!(answer.charAt(0)=='Y')&&!(answer.charAt(0)=='y')&&!(answer.charAt(0)=='n')&&!(answer.charAt(0)=='N'));
		
							//takes the first Letter of player's decision to reduce stake or not(that matters for us) 
		
		
		if (answer.charAt(0)=='Y'||answer.charAt(0)=='y') {  
			
		  StdOut.println("How much do you want to reduce your initial stake!");   //  if player wants to change initial
				String ans;														  //  stake it asks him for the 			
			
			do {																  //  reduction
			    StdOut.print(" Reduction up to half of initial stake!: ");
			    ans=StdIn.readLine();                                           //  Take the reduction as String
			 
			    while(!(isNumber(ans))) {								 //  checks if contains only numbers-digits
				   StdOut.println("Give a number not a String");
				   ans=StdIn.readLine();
			    }
			 
			   reduction=Double.parseDouble(ans.trim());        //  removes spaces before and after String number 
			 												    //  and turn into the String number to Double	
			   newlines(1);
			}while((reduction<0)||(reduction>stake/2));
				
		}
		
    }
	return reduction;
}


public static boolean stopDealer(int sumdealer,int dealeraces) {   
//  checks if dealer has to stop or continue,by checking the sum of the values of the cards who holds
//  and returns the true if dealer stops or false if continues	
	
	if(dealeraces>0) {
	    if (((sumdealer+11+dealeraces-1)>17)&&((sumdealer+11+dealeraces-1)<21)) return true;   
	      }																			//  considering 1 ace as 11	  
																					//  and the rest as 1
	 if ((sumdealer+dealeraces)<=17)return false;									//  else consider all aces as 1
	  else return true;
}


public static double compareCardValues(int sumplayer,int playeraces,int sumdealer,int dealeraces,double stake) {
	//  compares the values of cards of player and dealer which are less than 21 and returns the stake if player
	//  has more points than dealer,returns 0 if they have the same,or -stake if the dealer has more
	//  this values which are returned represent the money that player lose or win or nothing of these 
	int playersumcards=0;
	int dealersumcards=0;
	
	if (playeraces>0) {
		if ((sumplayer+11+(playeraces-1))<21) {                //  checks if player has an ace which he/she considered
			playersumcards=(sumplayer+11+(playeraces-1));	   //  it as 11, and the sum of the ace as 11 and the others 
		}													   //  cards is less than 21(the rest aces are considered  	
		else playersumcards=(sumplayer+playeraces);			   //  as 1). Else takes the sum of the cards by considering 
	}														   //  all aces as 1.	
	else playersumcards=sumplayer;
	
	
	
	if(dealeraces>0) {     										//  checks dealer cards if dealer's ace was considered 
		 if ((sumdealer+11+(dealeraces-1))<21)					//  as 11 else all aces are considered as 1 in the
			 dealersumcards= (sumdealer+11+(dealeraces-1));  	//  sum of the cards which holds	
		 else dealersumcards=(sumdealer+dealeraces);
		    }
	else dealersumcards=(sumdealer+dealeraces);
	
	
	if(dealersumcards>playersumcards) {								//  compares the values of the cards of dealer
		StdOut.printf("You have lost "+"%.1f"+" Euro",stake);		//  and player and prints the appropriate message	
		return (-stake); }	
																	//  of winning,losing,or nothing(draw) for player 
	else if(dealersumcards<playersumcards) {
		StdOut.printf("You have won "+"%.1f"+" Euro",stake);
		return stake;
	}
	
	else {StdOut.print("It's a draw!You get your money back ");
		return 0;}	
}



public static boolean isNumber(String answer) {      
	//  checks if a sequence of characters(String) contains only numbers
	
	int dot=0;               //  represents how many dots are contained in every String-input of player
	int digits=0;			 //  represents how many digits are contained in every String input 
	
	for(int i=0;i<answer.length();i++) {     //  calculates how many dots and digits are in the String-Input 
		
		if (Character.isDigit(answer.charAt(i)))digits+=1;    
			
		if(answer.charAt(i)=='.') dot+=1;
	}
	
	
	if ((dot>1)||(digits==0))return false;        //  if there are more than 1 dots,it is sure that is not a
												  //  double number( for instance .222.2 not double,( .2 or 2. double))
	for(int i=0;i<answer.length();i++) {		  //  checks for positive numbers and zero(negative numbers are denied)	
		if (!(Character.isDigit(answer.charAt(i))||(answer.charAt(i)=='.')) ) return false;   //  checks if all 
	}																						  //  characters of String						
 return true;																				  //  are digits or	
}																							  //  dot	




public static void main(String[] args) {

	StdOut.println("*******  WELCOME TO BLACKJACK  ********");
	
	int endchoice=0;		  //  represents the player's ending choice of  	
	double money=0;			  //  represents the sum of money which player lose or win during his/her games 
	int game=1;               //  game represents the number of the games which player has played one after the other
	newlines(2);
	
	do {															//  prints the n-tuple starting game heading (n is
																	// the number of the games which player played ) 
		StdOut.println(">>>> We are about to start Game number "+game+" <<<<");      
		   newlines(3);
		
	    double stake=validStake();		//  takes the correct stake of player and assigns it to variable stake
		    newlines(1);
		    
		    String[] deck=createShuffledeck();       //  create and shuffle the deck
		
		    String[] cards=new String[INITIAL_NUMBER_OF_CARDS];  //  create an array in which there will be put the
		    													 //  first two cards of player and dealer
		    for(int i=0;i<cards.length;i++) 
		    	cards[i]=giveCard(deck);					//  take 4 cards and put them in the array 'cards'
		    
		    int sumplayer=0;						//  represents the sum of player's cards which are not aces 
		    int playeraces=0;						//  number of player's aces
		    int dealeraces=0;						//  number of dealer's aces	
		    int sumdealer=0;					    //  represents the sum of dealer's cards which are not aces 
		    
		    
		    
		    for(int i=0;i<(cards.length-2);i++) { 
		    										   //  checks for the value of player's first two cards if they are 
		    	if (valueOfCard(cards[i])==1)		   //  1 (ace) then increase the value of the playeraces variable
		    		 playeraces+=1;					   //  by 1 else add the value of the card to  sumplayer variable
		    										
		    	else sumplayer+=valueOfCard(cards[i]);
		    	
		    	if (valueOfCard(cards[i+2])==1)        //  checks for the value of delaer's first two cards if they are
		    		 dealeraces+=1;				       //  1 (ace) then increase the value of the dealeraces variable	
		    	 
		    	else sumdealer+=valueOfCard(cards[i+2]);		//   by 1 else add the value of the card to  sumdealer 
		    													//  variable
		       }
		    	
		    						 
		    						
			
			StdOut.println("You have the following two cards:");
			 printspaces(PRINTING_SPACES);
			StdOut.println(cards[0]);									//  Prints centered the two starting cards
			 printspaces(PRINTING_SPACES);								//  of player and the first card of dealer 
			StdOut.println(cards[1]);
			  newlines(1);
			StdOut.println("The first of my initial two cards is "+cards[2]);
			
																
		      newlines(1);									  	
		      int surrender=0;									//  represents a value which later is being checked for
		    													//  surrender choice
		      double  stake2=0;
		      if (calcState(sumplayer,playeraces)!=1)	
		     stake2=opportunityReducingStake(cards[2],stake);	      //  reduce by a value if dealer's first card  
		    						    							  //  is an ace and if player wants to reduce it
		  															  //  this happens when player do not have 
	
		      														  //  Blackjack	
		      
		    if (stake2!=0) {surrender=1;							//  if player made a reduction to his initial stake				
		    			  stake-=stake2;}						    //  then the program won't let him have the choice
		    														//  (increasing by 1  of surrender (value of 
		    														//  surrender constant which will be checked for
		    														//  letting or not player to surrender
 		    																		
		    
	  if((calcState(sumdealer,dealeraces)==1)&&((calcState(sumplayer,playeraces)==1)))	
	  {   StdOut.println("My second card is "+cards[3]);
		  StdOut.println("We both two have Natural BlackJack!You don't lose or win anything! ");     } 	
		    													//  checks if both player and dealer has
	  															//  Natural Blackjack and prints a message for this
	  
	   else if(calcState(sumdealer,dealeraces)==1)
		  {StdOut.println("My second card is "+cards[3]);
		   StdOut.println("As you see I have a natural blackjack,so you lose your stake"); 	
			money-=stake; }											//  checks for a Natural Blackjack for the dealer
		      														//  and if there is ,reduce the money of player
		    														//  by the value of the stake
	  
	   else if (calcState(sumplayer,playeraces)==1)				
		 {StdOut.println("Natural Blackjack for you! You have won "+(stake*1.5)+" Euro");	
		 	 money+=stake*1.5; }									//  checks for a Natural Blackjack for the player	
		 															//  and if there is ,increase the money of player
		   															//  by the value of the stake*1.5
		  
	  
		else { 										   //  Now player's turn start if there was not a Natural Blackjack
		  
		StdOut.println("Your turn to play!!");
		  newlines(1);
		
		int state=0;	//  state represents the fact of having Blackjack,or being burned,or nothing of these(1,-1,0)
		 						 					
		int choice=1;						//  represents the choice which player choose for hit,stand,double down,...
		
		
		
		while((state==0)&&(choice==1)) {
			choice=displayMainChoices(surrender);			//  prints the main choices and returns to variable choice
			newlines(1);									//  the correct choice of player
			
			
			if((choice==1)||(choice==3)) {
				cards[0]=giveCard(deck);		//  use the first position of array cards to put the player's next card
				
				printspaces(PRINTING_SPACES);
				StdOut.println("Your next card is "+cards[0]);   //  print the next player's card
				
				if (valueOfCard(cards[0])==1)  playeraces+=1;  //  checks if the card of player is ace or anything else
				else sumplayer+=valueOfCard(cards[0]);		   //  and changes the values of the variable sumplayer
															   //  	if is not an ace or increase by one the variable
															   //   playeraces if is an ace	
				state=calcState(sumplayer,playeraces);		   //  change the state of player by using the new 
															   //  last value of card(into the sumplayer,playeraces)	
				if (choice==3)stake*=2;					     //  if player choose double down,it doubles player's stake
				}
			
			else if((choice==4)&&(surrender==0)) 		     	
				{money-=stake/2; }						//  if player surrender,he/she loses half of his/her initial
			surrender+=1;								//  stake
			
			newlines(1);
		}
		    
		
		
	   if(choice==4)
		  { StdOut.printf("By surrending you only lose "+"%.1f"+" Euro",(stake/2)); }  //  if player surrenders prints 
	   else if (state==-1) 															   //  the appropriate message	
	      {StdOut.printf("You have been burned and have lost "+"%.1f"+" Euro",stake);  //  if player's state is -1	
	      money-=stake; }															   //  it prints that he is burned	
	   else if(state==1) {															   //  if player's state is 1	
		   StdOut.printf("BlackJack for You!!You have just won "+"%.1f"+" Euro",stake);//  prints that he has a 
		   money+=stake; }															 //  Blackjack.Also by being burned	
	   else {							  //  player loses his/her stake,and by having Blackjack winning initial stake
		   
		   
		   StdOut.println("My turn to play... ");
		   newlines(1);
		   
		   StdOut.println("My second initial card is "+cards[3]);  // Prints the second card of dealer
		   newlines(1);
		 
		   int dealerstate=0;	//dealerstate represents the state of dealer(1 has Blackjack,-1,being burned,0 nothing)
		   
		   while(!(stopDealer(sumdealer,dealeraces))&&(dealerstate==0)) { //  checks if dealer's sum value cards are   
			   													//  greater or equal than 17(stopDealer) and if 
			   cards[3]=giveCard(deck);							//  dealerstate is 0(dealer not burned or not
			 StdOut.println("My next card is "+cards[3]);		//  having a Blackjack)
			 newlines(1);										//  Prints the next card of dealer.Uses the third 
			 													//  position of array cards for the next dealer's card
			 if (valueOfCard(cards[3])==1)dealeraces+=1;
			 else sumdealer+=valueOfCard(cards[3]);				//  checks if dealer's card is an ace(then increase by 
			 													//  one variable dealeraces else add card's value to  
			 dealerstate=calcState(sumdealer,dealeraces);  }	//  variable sumdealer)	
		   														//  after these change the dealerstate with the new
		   StdOut.println("I am stopping ...");					//  cards which dealer draw
		   newlines(1);
		   
		   if (dealerstate==-1) {			//	 prints message if dealer is burned
			   StdOut.printf("I have burned myself and you win "+"%.1f"+" euro",stake);
			   money+=stake;				//  increase player's earned money
			   }
		   
		   else if(dealerstate==1) {				//  prints message if dealer has a Blackjack
			   StdOut.printf("Blackjack for me!!!You have lost "+"%.1f"+" euro",stake);
			   money-=stake;      //  reduce player earnings money 
			   }
		   else
		    { 																				//  compare player and
			   money+=compareCardValues(sumplayer,playeraces,sumdealer,dealeraces,stake);	//  dealer card values and
			 }																				//  and reduce or increase 
	   }																					//  or add 0 to player's 
																							//  money
		}
	  newlines(4);
	  endchoice=validEndChoice();							//  Prints the ending choices of continuing the game or  
	    game+=1;											//  stopping and returning back the correct player's choice
															//  (1 or 2)
		}while(endchoice==1);
	
	
	
	if (money<0) 
	      { StdOut.printf("You have lost %.1f Euro. Next time luckier.",Math.abs(money)); }
	
	else if (money>0) 
	      { StdOut.printf("You have won %.1f Euro. Lucky You!!",money); }
	
	else  { StdOut.print("You haven't won or lost any Euro!");  }
													//  if player stops the game it is printed to him/her the value of
	newlines(2);									//  money he lost or won or nothing gained,plus the ending title of
													//  the game
	
	StdOut.println("********  GOODBYE-COME AGAIN  ********"); 
														
	}												


}
