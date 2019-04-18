package frontend.Views.Board;

import configuration.ImportPropertyFile;

public class SquareBoardView extends RectangularBoardView {
    public SquareBoardView(double screenWidth, double screenHeight, double tileHeight, int horizontalTiles, int verticalTiles, ImportPropertyFile propertyFile) {
        super(Math.min(screenWidth,screenHeight), Math.min(screenWidth,screenHeight), tileHeight, horizontalTiles, verticalTiles,propertyFile);
    }
}
