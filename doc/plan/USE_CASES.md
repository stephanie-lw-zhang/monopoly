# Use Cases

### 1. Move piece
- In Handler class, update the map that contains all the players and their positions. (Key player, value position). 
Update GUI board so player figure is on the new tile space
### 2. Roll die
- Dice class is abstract. The subclass of dice used will be initialized in our Manager class and remain constant throughout the game. 
Roll the dice by generating number, how you generate a number (whether it be random or weighted) is specific to the subclass.
The number shows up on the GUI.
### 3. Draw card
- First Card object from CardStack data structure (queue) is accessed in Driver
Display card on GUI 
### 4. Store card
- Each card is represented by a Card object. If the card is storable, it is stored with the Player object.
### 5. Use card
- Each card is represented by a Card object. Each card has an apply card method that is called when the card is used. 
### 6. Putting cards back into the deck
- Depends on the game. 
- Generally, add Card object to the end of the CardStack data structure
- Remove card display from GUI 
### 7. Buying properties
- Upon landing on open property can buy or auction
- Color, prices, rent, etc imported from xml
### 8. Transactions between people
- Each player is represented by a player object that is stored in Manager class. Access the specific players, and subtract/add to their funds (an attribute of player class) accordingly. 
### 9. Landing on someone?s property
- Display whose property is being landed on and how much money is owed 
- Deduct that amount of money from the player who landed on the tile and add that amount of money to the player who owns the property
### 10. Upgrade property
- Update the specific Property Tile?s value. 
### 11. Going to jail
- Update the map of the player?s positions in the Board class to the Jail tile.
- The jail Tile holds the player.
- Move Player on the GUI.
### 12. Passing go
- Depends on game 
- Check passedGo in the Board class (backend) and if it?s true, add a certain amount of money to the player
- If landed on go (another method), add another certain amount of money to the player
### 13. Landing on free parking
- If Tile object is a free parking tile, then add a certain amount of money to the player who landed on it
- Certain amount of money determined in rules/xml file 
### 14. Landing on chance/community chest
- Apply the Tile to the player, which prompts it to draw a card from Deck and apply that card to player. Then according to the type of Deck put the card back accordingly.
### 15. Bank loaning money
- Check if bank has exact change, if there is no exact change, the player doesn?t get any money. Otherwise, the player?s funds add in money.
### 16. Turns
- Encapsulated within step, calls on each player in the game to complete their turn
### 17. Mortgaging property
- If the player decides to mortgage a property
### 18. Initializing the board
- Get/make all game objects from .xml file, make the board according to backend
### 19. Landing on railroad
- Same as property management
### 20. Paying utility rent
- Check if the Utility tile is owned, the player rolls the dice to generate the amount of rent paid. The player pays the rent to the owner of the Utility tile.
### 21. Bidding for properties
- Call on each player for bids in rounds; player can either surpass previous bid or pass; if player passes cannot participate in future rounds of bidding
### 22. Getting out of jail (roll doubles or get out of jail free card)
- Have isInJail boolean for each player 
- Board will check if they are in jail -- if so, then allow them the options to either bail out or roll the die 
- Display these options on GUI and allow player to select 
- If bail, deduct a certain amount of money from player and set isInJail to false
- If roll the die, allow player to roll the die 
- If dice are doubles, set isInJail to false
### 23. Paying tax
- Bank/communalfund object receives funds from player
- TaxTile .payTax method -> calls .setMoney() 
### 24. Bank receiving money
- The Bank class adds money to its funds and the player decreases in that money. 
- Bank also keeps track of the type of bills and update bill type (if doing money bill system). 
