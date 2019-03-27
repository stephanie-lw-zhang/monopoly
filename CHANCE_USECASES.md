### A detailed description of how the classes' methods will handle the use cases below:
- A player lands on Chance, draws "Bank pays you a dividend of $50", your funds are updated appropriately, and the card is returned to the bottom of the deck.
     - Check if the player has landed on chance. If they have, apply the card to the player. Each AbstractPlayer will have some instance variable ?funds? (most likely a double) associated with that player to represent their funds. Funds would just be incremented or decremented based on the drawn card. The card would go back to the deck (This deck might need to be represented using a Queue--but could be shuffled)
- A player lands on Chance, draws "Advance to Go, collect $200", your token is moved and your funds are updated appropriately, and the card is returned to the bottom of the deck.
    - Check if player has landed on chance. If they have, take first card out of the deck (set/queue), call card.updatePlayer, which will update the player?s position and its funds (both of these will be member variables of the Player class), add card back to the end of the set/queue
- A player lands on Chance, draws "Get Out of Jail Free", and it is saved with your inventory, and the card is returned to the bottom of the deck.
    - ChanceCard is added to the AbstractPlayer?s list of Cards. 
- A player lands on Chance, draws "Go to Jail, Go directly to Jail", your token is moved (passing Go, but not collecting any money) and placed in jail, and the card is returned to the bottom of the deck.
    - Check if the player has landed on chance. If they have, apply the card to the player. Check if the player has a get out of jail free card and wants to use it. Otherwise just go to jail, directly to jail. Make sure they can?t play for a certain number of turns. Also make sure the funds are not incremented if the player passes Go.
- A new rule is added that lets a player draw a Chance card when landing on Free Parking space.
    - Upon landing on free parking, call method that applies card to the player.
    - Check if the player has landed on chance. If they have, apply the card to the player, so they go to the free parking space. At the free parking space, the player gets the communal money. If there is no communal money, they get 100. 
- A new theme for the game is designed, creating cards in a different language.
    - Properties files would need to change
    - Reflection can be used to generalize stuff