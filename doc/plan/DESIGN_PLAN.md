### Design Plan

## Introduction
*This section describes the problem your team is trying to solve by writing this program, the primary design goals of the project (i.e., where is it most flexible), and the primary architecture of the design (i.e., what is closed and what is open). Discuss the design at a high-level (i.e., without referencing specific classes, data structures, or code).*

In this project, our team is trying to create a functional game of monopoly for multiple human or computer players that accommodates multiple different types of configurations. The game will implement multiple different abstractions in order to increase the flexibility of the code. For example, there will be abstractions for the board, the board tiles, the player, the dice, and the cards. This is intended to allow for different game rules and board types. These will all be open to extension, but closed for modification. For example, interfaces and abstract classes should be extended in order to create abstraction hierarchies. They should be able to be changed, however, because that could potentially disrupt the structure of the project.

## Overview
*This section serves as a map of your design for other programmers to gain a general understanding of how and why the program was divided up, and how the individual parts work together to provide the desired functionality. Describe specific modules you intend to create, their purpose with regards to the program's functionality, and how they collaborate with each other, focusing specifically on each one's API. Include a picture of how the modules are related (these pictures can be hand drawn and scanned in, created with a standard drawing program, or screen shots from a UML design program). Discuss specific classes, methods, and data structures, but not individual lines of code.*

There is an abstract board class that keeps track of the players and tiles, and the coordinates of each. It can be extended to accommodate different types of boards. It also moves the player and has getter methods to report the coordinates of the player. A handler recognizes the necessary actions that need to be taken based on the change in coordinates. Each tile is extended from an abstract Tile class that could include Jail, DrawCard, Property, and GO. Each of these has specific actions and methods that pertain to its purpose.   Each player is extending an abstract Player class that could include a human user, simple computer, and complex computer. It contains key information about such as each player’s money, properties, cards, and piece. These attributes all have getter methods so that these values can be displayed and accessible to the user. We will also have an abstract dice class for different types of die, to account for any different number of sides for dice, fair vs weighted dice, speed dice, etc. There will also be an abstract Deck class so we can change up how cards in a deck are drawn and put back (putting it on the bottom, shuffling in between, etc).

On the front-end, there are AbstractBoardGUI and AbstractTileGUI. AbstractBoardGUI will create different board patterns, while AbstractTileGUI will create different shapes of tiles that AbstractBoardGUI will add to the screen in the board pattern that is being asked for.

## User Interface
*This section describes what components the user will interact with in your program (keep it simple to start) and how a game is represented to the designer and what support is provided to make it easy to create a game. Include design goals for the implementation of the UI that shows the kind of abstractions you plan to build on top of OpenJFX's standard components and how they can be flexibly exchanged within the GUI itself. Finally, describe any erroneous situations that are reported to the user (i.e., bad input data, empty data, etc.).*

The display will consist mostly of the board, which contains all of the tiles, cards, and pieces. There is a text display that tells the user how much money they have. There are three buttons: “Roll!”, “Properties”, and “Cards.” The player presses Roll! to begin their turn, which prompts a popup that tells the user what they rolled, prompts the moving of the player, etc. The Properties button prompts a pop-up that displays the properties that the user owns. The Cards button prompts a pop-up that displays if the player has any held cards, such as a Get Out of Jail Free card. As we start to implement more features, we will try to create a “Trade” button in which the user can attempt to trade properties and money for other players’ money or properties.

There are some exceptions that need to be handled and might be reported to the user through a pop-up. An exceptions would be thrown if there was a problem with the configuration files initializing the game, such as invalid values or null data names. Other possible examples include: there is a missing value in a properties file, a file cannot be found, an unknown type is created (ie. invalid Tile name, Dice type, Board type, etc).

## Design Details
*This section describes each module introduced in the Overview in detail (as well as any other sub-modules that may be needed but are not significant to include in a high-level description of the program). Describe how each module handles specific features given in the assignment specification, what resources it might use, how it collaborates with other modules, and how each could be extended to include additional requirements (from the assignment specification or discussed by your team). Note, each sub-team should have its own API(s). Finally, justify the decision to create each module with respect to the design's key goals, principles, and abstractions.*

We need the backend Model module (with Board, Tile, Dice, Player, etc) to encapsulate backend classes. We need to create the different classes to keep it organized, flexible, and easier to read. All the modules will initialize from properties files. 

**Abstract Board class**
* This abstract class will keep a map of players (keys) and the tile they are currently on (values). It will also hold a data structure (graph for now) that contains the different paths on board that the player can take, meaning the tiles that a player can travel from based on where they currently are. It also stores the decks of drawable cards.
* This class moves the players and checks when they pass GO or if they’re in jail before/while moving them
* This board will require a csv configuration to initialize the graph, which will be passed in from Driver
* The subclasses will be the different types of Monopoly games, e.g. the different rules needed for each Game. This can be easily extended since it is abstract to add these new types of games.
* This class interacts with the Player class and Tile classes
* We wanted to make this abstract so that it would be open for extension. Additionally, we wanted to separate it from the front-end grid to make sure that we can change the back-end without breaking the front-end. 

**controller class**
* The controller class will handle the interactions resulting from the player moving on the board. It will receive the coordinates from the board after the player moves and enact the specific response.
* Serves to pass information from the Model (board) to the View
* Handles when to draw a card, when to show a pop-up about buying property, when to award $200 for passing GO, etc.

**Abstract Dice class**
* We will have an abstract dice class for different types of die, to account for any different number of sides for dice, fair vs weighted dice, speed dice, etc. 
* The subclass of dice that will be used will be determined in the XML file 
* This class will be used in the controller, which will generate the new dice roll 
* The controller will pass this to the Board, as well as to the AbstractBoardGUI so that it will be displayed for the user to see
* Since this is abstract, it will be easy to add new dice 
* We wanted to make this abstract so that it would be open for extension. 

**Abstract Player**
* Each player is extending an abstract Player class that could include a human user, simple computer, and complex computer. It contains key information about such as each player’s money, properties, cards, and piece.
* We abstracted the Player class so that there could be an automated player as well in the future
* This class will be used in the abstract board class, which will move the players and update the board accordingly
* We will create several Player objects per game, depending on the number of players there are
* Since this is abstract, it will be easy to add a new type of automated player
* We wanted to make this abstract so that it would be open for extension. 

**Abstract Card**
* The abstract Card class includes a method to get the text associated with the card, and a method to enact the card’s purpose. This is specifically for Chance cards and Community Chest cards. 
* If the player lands on a tile on the board that indicates that either of these cards should be drawn, then the View will display the card’s message and the controller will enact the action.
* We made this abstract to make it easy to add new types of cards 
* Because it is abstract, it is open for extension. Additionally, we can have each card type associated with an action that it can perform on a player (single responsibility per card type).

**Abstract Deck**
* There will also be an abstract Deck class so we can change up how cards in a deck are drawn and put back (putting it on the bottom, shuffling in between, etc).
* We made this abstract so that there may be a way to shuffle cards differently based on the game.
* Abstraction makes this class flexible.

**Abstract Tile Class**
* Each tile is extended from an abstract Tile class that could include Jail, DrawCard, Property, and GO. Each of these has specific actions and methods that pertain to its purpose. 
* Abstraction makes this class flexible.
* Because it is abstract, we can have each tile type associated with an action that it can perform (single responsibility per tile type).

**Bank**
* The bank class keeps track of the types and amount of each bill, which is relevant when playing with money bills because if there isn’t exact change the player just won’t get any money. Also, bank can have limited money and run out. 
* Bank mainly interacts with the Player class.

**AbstractBoardGUI**
- AbstractBoardGUI will create different board patterns
- AbstractBoardGUI will add tiles to the screen in the board pattern that is being asked for.
- Abstract because there could be different board patterns depending on the game.

**AbstractTileGUI**
- AbstractTileGUI will create different shapes of tiles
- Abstract because there can be different tile shapes (shape, hexagon, etc.)

## Example games
*Describe three example games in detail that differ significantly. Clearly identify how the functional differences in these games is supported by your design. Use these examples to help clarify the abstractions in your design.*
- Monopoly Millionaire
    - Winning is determined by the Board class, and it checks every turn if someone has won. The Board is an abstract class, and because different games have different ways to win, the win method would be abstract (specified in the subclass). 
- Monopoly Jr.
    - The board GUI is abstract, so we can create new boards with a simpler design for this monopoly by creating a subclass. The specific names and dimensions of the board will be determined by a data file. 
    - The amount of money collected from Go and specific properties will be read in from a resource file. 
    - A win happens when one player goes bankrupt, and the person with the highest score whens. This will be specified in the Board class in the win method.
- Monopoly City
    - There are more types of buildings, which will be specified through the value of a property. We’ll also add different types of cards (such as “reminder cards”), which can be created by created Card subclasses. 

## Design Considerations 
*This section describes any issues which need to be addressed or resolved before attempting to devise a complete design solution. Include any design decisions that each sub-team discussed at length (include pros and cons from all sides of the discussion) as well as any ambiguities, assumptions, or dependencies regarding the program that impact the overall design.*
- Which class moves the player: We decided that the Board class should.
    - Pros
        - The Player class doesn’t have to know where the player is on the board, thus keeping the knowledge within the class.
    - Cons
        - The Board class is handling many methods, so it might get really long.
- Using a tree for the path players can take.
    - Pros
        - It’s more flexible because it allows for non-linear paths.
    - Cons
        - It’s complicated to implement and probably read afterwards.
        - Makes file initializing these types of paths harder to read and create.