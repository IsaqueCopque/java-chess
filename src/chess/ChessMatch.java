package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private List<Piece> piecesOnTheBoard;
	private List<Piece> capturedPieces;
	private boolean check;
	
	public ChessMatch() {
		board = new Board(8,8);
		turn = 1;
		currentPlayer = Color.WHITE;
		piecesOnTheBoard = new ArrayList<Piece>();
		capturedPieces = new ArrayList<Piece>();
		check = false;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE)? Color.BLACK : Color.WHITE;
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
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
		placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
	
	//Coloca peça na partida de acordo com posição do xadrez
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column,row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	//Retorna as posicoes possiveis para movimento
	public boolean [][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.getPiece(position).possibleMoves();
	}
	
	//Realiza o movimento da peca
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source,target);
		
		if(testCheck(currentPlayer)) {
			undoMove(source,target,capturedPiece);
			throw new ChessException("Voce nao pode se colocar em check");
		}
		
		check = (testCheck(opponent(currentPlayer)))? true : false ; 
		 
		nextTurn();
		return (ChessPiece) capturedPiece;
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position))
			throw new ChessException("Nao ha peca na posicao de origem");
		if (currentPlayer != ( (ChessPiece)board.getPiece(position)).getColor())
			throw new ChessException("A peca escolhida nao eh sua");
		if(!board.getPiece(position).isThereAnyPossibleMove())
			throw new ChessException("Nao existe movimento possivel para peca escolhida");
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!board.getPiece(source).possibleMove(target)) 
			throw new ChessException("A peca escolhida nao pode ser movida para este destino");
	}
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter( x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for( Piece p : list) {
			if(p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("Nao ha rei de cor " + color + " no tabuleiro");
	}
	
	//verifica se o jogo está em check
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter( x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for(Piece p : opponentPieces) {
			boolean[][] mat = p .possibleMoves();
			if(mat[kingPosition.getRow()][kingPosition.getCollumn()]) {
				return true;
			}
		}
		return false;
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece piece = board.removePiece(target);
		board.placePiece(piece, source);
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}
	
}
