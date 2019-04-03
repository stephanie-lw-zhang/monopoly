# Project Plan

### Responsibilities by people 
- Sam -- Movement, engine, fullstack
- Luis -- configuration, data, integration
- Edward -- fullstack, display, paths
- Matt -- backend class: Board, Controller
- Connie -- backend class: Tile and all its subclasses
- Stephanie -- backend classes: Dice, Player, Bank, Deck, Card

### Sprint 1
- Goal: Play an original, standard game of monopoly with just one human player to make sure it runs 
- Adv. Goal: Play original monopoly with multiple human players
### Sprint 2
- Goal: Play multiple types of monopolies (at least 3) with multiple players and be able to save
- Adv. Goal: Implement Hexagonal or other shaped cells, play against bots
### Sprint 3
- Goal: Play many monopolies with multiple shapes and players
- Adv Goal: Move in different directions, make front end beautiful (add little rectangles)
### Challenges
- Trade/Auction with others
- Play on servers with other live players

# User Interface Wireframe

![alt text](https://coursework.cs.duke.edu/compsci307_2019spring/monopoly_team05/raw/master/data/Frame1.png "Frame 1")

![alt text](https://coursework.cs.duke.edu/compsci307_2019spring/monopoly_team05/raw/master/data/Frame2.png "Frame 2")

![alt text](https://coursework.cs.duke.edu/compsci307_2019spring/monopoly_team05/raw/master/data/Frame3.png "Frame 3")

![alt text](https://coursework.cs.duke.edu/compsci307_2019spring/monopoly_team05/raw/master/data/Frame4.png "Frame 4")

![alt text](https://coursework.cs.duke.edu/compsci307_2019spring/monopoly_team05/raw/master/data/Frame5.png "Frame 5")

![alt text](https://coursework.cs.duke.edu/compsci307_2019spring/monopoly_team05/raw/master/data/Frame6.png "Frame 6")

![alt text](https://coursework.cs.duke.edu/compsci307_2019spring/monopoly_team05/raw/master/data/Frame7.png "Frame 7")

![alt text](https://coursework.cs.duke.edu/compsci307_2019spring/monopoly_team05/raw/master/data/Frame8.png "Frame 8")

![alt text](https://coursework.cs.duke.edu/compsci307_2019spring/monopoly_team05/raw/master/data/Frame9.png "Frame 9")

![alt text](https://coursework.cs.duke.edu/compsci307_2019spring/monopoly_team05/raw/master/data/Frame10.png "Frame 10")

![alt text](https://coursework.cs.duke.edu/compsci307_2019spring/monopoly_team05/raw/master/data/Frame11.png "Frame 11")

![alt text](https://coursework.cs.duke.edu/compsci307_2019spring/monopoly_team05/raw/master/data/Frame12.png "Frame 12")


# Design Plan

* API and Overview

![alt text](https://coursework.cs.duke.edu/compsci307_2019spring/monopoly_team05/blob/master/doc/api/initial_uml_overview.png)

* Design Goals
    * Data Driven:
 ```xml
 <OGMonopoly>
    <BoardLayout>OriginalMonopoly.csv</BoardLayout>
    <Tile>
        <TileNumber>0</TileNumber>
        <TileType>GO</TileType>
        <TileColor>GREEN</TileColor>
    </Tile>
    <Tile>
        <TileNumber>1</TileNumber>
        <TileType>Property</TileType>
        <TileColor>Brown</TileColor>
        <TileName>Chris's Closet</TileName>
        <TileRent>2</TileRent>
        <TileRentWithColorSet>4</TileRentWithColorSet>
        <TileRent1House>10</TileRent1House>
        <TileRent2House>30</TileRent2House>
        <TileRent3House>90</TileRent3House>
        <TileRent4House>160</TileRent4House>
        <TileRentHotel>250</TileRentHotel>
        <TileMortgageValue>30</TileMortgageValue>
        <TileHousePrice>50</TileHousePrice>
        <TileHotelPrice>50</TileHotelPrice>
    </Tile>
    <Tile>
        <TileType>Giggity Giggity Giggity</TileType>
       </Tile>
</OGMonopoly>
``` 
* Use Cases
    * Drawing a Card
    * Being in Jail
    
* Alternative Designs
    * Storing Tiles in an Array
        * Doesnt allow for dynamic movement along tiles
        * Would be good if movement was strictly linear
    * Tiles as Nodes in a Graph
        * Allows to dynamically move from one tile to another based on movement criteria