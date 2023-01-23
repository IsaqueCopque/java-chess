package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Board board;
	
	public ChessMatch() {
		board = new Board(8,8);
		initialSetup();
	}
	
	public ChessPiece[][] getPieces(){
		ChessPiece[][] matriz = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i=0;i<board.getRows();i++) {
			for(int j=0; j<board.getColumns();j++) {
				matriz[i][j] = (ChessPiece) board.getPiece(i, j);
			}
		}
		return matriz;
	}
	
	public void initialSetup() {
		//board.placePiece(new Rook(board,Color.WHITE), new Position(2,1));
	}
}
