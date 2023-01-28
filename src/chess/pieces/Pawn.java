package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	
	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean [][]mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0,0);
		
		if(getColor() == Color.WHITE) {
			p.setValues(position.getRow() - 1, position.getCollumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getCollumn()] = true;
			}
			//se primeiro movimento pode mover duas casas
			p.setValues(position.getRow() - 2, position.getCollumn());	
			Position p2 = new Position(position.getRow()-1, position.getCollumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) 
					&& getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0 ) {
				mat[p.getRow()][p.getCollumn()] = true;
			}
			//peça nas diagonais
			p.setValues(position.getRow() - 1, position.getCollumn() - 1);	
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getCollumn()] = true;
			}
			p.setValues(position.getRow() - 1, position.getCollumn() + 1);	
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getCollumn()] = true;
			}
			
			//En Passant branco
			if(position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getCollumn()-1);
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && 
						getBoard().getPiece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow()-1][left.getCollumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getCollumn()+1);
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && 
						getBoard().getPiece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow()-1][right.getCollumn()] = true;
				}
			}
			
		}
		else {
			p.setValues(position.getRow() + 1, position.getCollumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getCollumn()] = true;
			}
			//se primeiro movimento pode mover duas casas
			p.setValues(position.getRow() + 2, position.getCollumn());	
			Position p2 = new Position(position.getRow()+1, position.getCollumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) 
					&& getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0 ) {
				mat[p.getRow()][p.getCollumn()] = true;
			}
			//peça nas diagonais
			p.setValues(position.getRow() + 1, position.getCollumn() - 1);	
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getCollumn()] = true;
			}
			p.setValues(position.getRow() + 1, position.getCollumn() + 1);	
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getCollumn()] = true;
			}
			
			//En Passant preto
			if(position.getRow() == 4) {
				Position left = new Position(position.getRow(), position.getCollumn()-1);
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && 
						getBoard().getPiece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow()+1][left.getCollumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getCollumn()+1);
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && 
						getBoard().getPiece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow()+1][right.getCollumn()] = true;
				}
			}
		}
		
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}
}
