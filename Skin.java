/**
 * Represents a cosmetic skin that can be applied within a game.
 *
 * @author Brian Yue
 * @version 1.0
 */
public class Skin {
    private String name;
    private int cost;
    private String rarity;

    /**
     * Creates a new Skin object.
     *
     * @param name    The name of the skin
     * @param cost    The cost of the skin (likely in some in-game currency)
     * @param rarity  A string indicating the rarity of the skin (e.g., "Common", "Rare")
     */
    public Skin(String name, int cost, String rarity){
        this.name = name;
        this.cost = cost;
        this.rarity = rarity;
    }
    /**
     * Gets the name of the skin (which might be the filepath after applying).
     *
     * @return The name of the skin.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Gets the cost of the skin.
     *
     * @return The cost of the skin.
     */
    public int getCost(){
        return this.cost;
    }

    /**
     * Gets the rarity of the skin.
     *
     * @return The rarity of the skin.
     */
    public String getRarity(){
        return this.rarity;
    }
}
