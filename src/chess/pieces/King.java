package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	
	private ChessMatch chessMatch;	
	
	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}
	@Override
	public String toString() {
		return "K";
	}
	
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().getPiece(position);
		return p == null || p.getColor() != getColor();
	}
	
	//testa se a torre permite movimento Roque
	private boolean testRookCastling(Position postion){
		ChessPiece p = (ChessPiece)getBoard().getPiece(postion);
		return p != null && p instanceof Rook && p.getColor() == getColor() && 
				p.getMoveCount() == 0;
	}
	
	
	@Override
	public boolean[][] possibleMoves() {
		boolean [][]mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		//acima
		p.setValues(position.getRow() - 1, position.getCollumn());
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		
		//abaixo
		p.setValues(position.getRow() + 1, position.getCollumn());
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		
		//esquerda
		p.setValues(position.getRow(), position.getCollumn() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		
		//direita
		p.setValues(position.getRow(), position.getCollumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		
		//diagonal esquerda superior
		p.setValues(position.getRow() - 1, position.getCollumn() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		
		//diagonal direita superior
		p.setValues(position.getRow() - 1, position.getCollumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		} 
		
		//diagonal esquerda infeior
		p.setValues(position.getRow() + 1, position.getCollumn() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		
		//diagonal direita inferior
		p.setValues(position.getRow() + 1, position.getCollumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		} 
		
		//movimento roque
		if(getMoveCount() == 0 && !chessMatch.getCheck()) {
			//Roque Pequeno
			Position posT1 = new Position(position.getRow(), position.getCollumn() + 3);
			if(testRookCastling(posT1)) {
				Position p1 = new Position(position.getRow(), position.getCollumn() + 1);
				Position p2 = new Position(position.getRow(), position.getCollumn() + 2);
				if(getBoard().getPiece(p1) == null && getBoard().getPiece(p2) == null) {
					mat[position.getRow()][position.getCollumn()+2] = true;
				}
			}
			//Roque Grande
			Position posT2 = new Position(position.getRow(), position.getCollumn() - 4);
			if(testRookCastling(posT2)) {
				Position p1 = new Position(position.getRow(), position.getCollumn() - 1);
				Position p2 = new Position(position.getRow(), position.getCollumn() - 2);
				Position p3 = new Position(position.getRow(), position.getCollumn() - 3);
				if(getBoard().getPiece(p1) == null && getBoard().getPiece(p2) == null
						&& getBoard().getPiece(p3) == null) {
					mat[position.getRow()][position.getCollumn() - 2] = true;
				}
			}
		}
		
		return mat;
	}
}
