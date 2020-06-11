package blockchain;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class Block implements Serializable {
    final int id;
    final long timestamp;
    final String prevHash;
    final int magic;
    final String thisHash;
    final long generationTime;

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
        } while (!thisHash.substring(0, leadingZeros).matches("0*"));
        this.magic = magic;
        this.thisHash = thisHash;

        this.timestamp = timestamp;
        this.generationTime = timestamp - startTime;
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
