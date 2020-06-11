package blockchain;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class Block implements Serializable {
    private static final long serialVersionUID = 1;
    private final int id;
    private final long timestamp;
    private final String prevHash;
    private final int magic;
    private final String thisHash;
    private final long generationTime;

    Block(int id, Block prevBlock, String data, int leadingZeros) {
        long startTime = new Date().getTime();
        this.id = id;
        this.prevHash = (prevBlock == null) ? "0" : prevBlock.thisHash;

        Random rng = new Random();
        long timestamp;
        int magic;              //local variable while computing so fields can be final
        String thisHash;

        do {
            magic = rng.nextInt();
            timestamp = new Date().getTime();
            thisHash = StringUtil.applySha256(id + timestamp + this.prevHash + magic + data);
        } while (isUnproved(leadingZeros));
        this.magic = magic;
        this.thisHash = thisHash;

        this.timestamp = timestamp;
        this.generationTime = timestamp - startTime;
    }

    public boolean isUnproved(int leadingZeros) {
        return thisHash.substring(0, leadingZeros).matches("0*");
    }

    boolean matchesPrevious(Block prev) {
        return this.prevHash.equals(prev.thisHash);
    }

    @Override
    public String toString() {
        return "Block:" +
                "\nId: " + id +
                "\nTimestamp: " + timestamp +
                "\nMagic number: " + magic +
                "\nHash of the previous block:\n" + prevHash +
                "\nHash of the block:\n" + thisHash +
                "\nBlock was generating for " + generationTime / 1000 + " seconds.";
    }
}
