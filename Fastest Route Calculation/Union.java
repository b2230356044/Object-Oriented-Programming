import java.util.*;

/**
 * This class implements the Union-Find data structure (also known as Disjoint Set Union, DSU).
 * It supports union and find operations with path compression and union by rank.
 */
public class Union {
    private final Map <String, String> parent = new HashMap<>();
    private final Map <String, Integer> rank = new HashMap<>();

    /**
     * Initializes a new set for the given item. The item is its own parent initially, and its rank is set to 0.
     *
     * @param item the item to create a new set for
     */
    public void makeSet(String item) {
        parent.put(item, item);
        rank.put(item, 0);
    }

    /**
     * Finds the representative (root) of the set containing the given item.
     * Implements path compression to flatten the structure for efficient future operations.
     *
     * @param item the item whose set representative is to be found
     * @return the representative of the set containing the item
     */
    public String find(String item) {
        if (!parent.get(item).equals(item)) {
            parent.put(item, find(parent.get(item)));
        }
        return parent.get(item);
    }

    /**
     * Unites the sets containing the two specified items. Uses union by rank to attach the smaller tree under the larger tree.
     *
     * @param set1 an item in the first set to be united
     * @param set2 an item in the second set to be united
     */
    public void union(String set1, String set2) {
        String root1 = find(set1);
        String root2 = find(set2);

        if (root1.equals(root2)) return;

        int rank1 = rank.get(root1);
        int rank2 = rank.get(root2);

        if (rank1 > rank2) {
            parent.put(root2, root1);
        } else if (rank1 < rank2) {
            parent.put(root1, root2);
        } else {
            parent.put(root2, root1);
            rank.put(root1, rank1 + 1);
        }
    }
}

