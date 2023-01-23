package boardgame;

public class Board {
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		if(rows < 1 || columns < 1) {
			throw new BoardException("Erro criando tabuleiro: É preciso haver ao menos 1 linha e 1 coluna;");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}
	public int getColumns() {
		return columns;
	}
	public Piece getPiece(int row, int column) {
		if(!positionExists(row,column)) {
			throw new BoardException("Posicao nao esta no tabuleiro;");
		}
		return pieces[row][column];
	}
	
	public Piece getPiece(Position position) {
		if(!positionExists(position.getRow(),position.getCollumn())) {
			throw new BoardException("Posicao nao esta no tabuleiro;");
		}
		return pieces[position.getRow()][position.getCollumn()];
	}
	
	public void placePiece(Piece piece, Position position) {
		if(thereIsAPiece(position)) {
			throw new BoardException("Posicao "+ position +" ja ocupada;");
		}
		pieces[position.getRow()][position.getCollumn()] = piece;
		piece.position = position;
	}
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getCollumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Posicao nao esta no tabuleiro;");
		}
		return getPiece(position) != null;
	}
	
}