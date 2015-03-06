package net.samagames.samagamesapi.utils.chat;

public enum SymbolsUtils
{
    HEART("\u2764"),
    BASIC_STAR("\u2605"),
    NETHER_STAR("\u2726"),
    CIRCLE("\u25CF");
    
    
    private final String symbol;
    
    private SymbolsUtils(String symbol)
    {
        this.symbol = symbol;
    }
    
    public String getSymbol()
    {
        return this.symbol;
    }
}
