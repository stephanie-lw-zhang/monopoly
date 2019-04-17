package FrontEnd.Views.Board;

import FrontEnd.Views.Board.RectangularBoardView;

public class SquareBoardView extends RectangularBoardView {
    public SquareBoardView(double screenWidth, double screenHeight, double tileHeight, int horizontalTiles, int verticalTiles) {
        super(Math.min(screenWidth,screenHeight), Math.min(screenWidth,screenHeight), tileHeight, horizontalTiles, verticalTiles);
    }
}
