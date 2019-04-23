package frontend.views.board;

import backend.board.AbstractBoard;
import configuration.ImportPropertyFile;

public class SquareBoardView extends RectangularBoardView {
    public SquareBoardView(AbstractBoard board, double screenWidth, double screenHeight, double tileHeight, int horizontalTiles, int verticalTiles) {
        super(board, Math.min(screenWidth,screenHeight), Math.min(screenWidth,screenHeight), tileHeight, horizontalTiles, verticalTiles);
    }
}
