package ca.mcscert.se2aa4.tools.mazegen;

public enum Tile {
    WALL('#'), EMPTY(' ');

    private final char symbol;

    Tile(char symbol) { this.symbol = symbol; }

    @Override
    public String toString() { return "" + symbol; }
}
