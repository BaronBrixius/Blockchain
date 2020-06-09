package blockchain;

import java.util.Date;

public class Block {
    private final int id;
    private final long timestamp;
    private final String prevBlock;
    private final String thisBlock;

    Block(int id, Block prevBlock, String data) {
        this.id = id;
        this.timestamp = new Date().getTime();
        this.prevBlock = (prevBlock == null) ? "0" : prevBlock.thisBlock;
        this.thisBlock = StringUtil.applySha256(data);
    }

    @Override
    public String toString() {
        return  "Id: " + id +
                "\nTimestamp: " + timestamp +
                "\nHash of the previous block:\n" + prevBlock +
                "\n\nHash of the block:\n" + thisBlock;
    }
}
