package blockchain;

import java.util.LinkedList;

public class Blockchain {
    private int size = 0;
    private final LinkedList<Block> blockchain = new LinkedList<>();;

    public Blockchain(){
    }

    public Blockchain(String... data){
        for (String s : data) {
            add(s);
        }
    }

    public void add(String data){
        Block blockToAdd = new Block(++size, blockchain.peekLast(), data);
        blockchain.add(blockToAdd);
    }

    @Override
    public String toString(){
        if (isEmpty())
            return "";

        StringBuilder out = new StringBuilder("Block:\n");
        for (Block b: blockchain)
            out.append(b.toString()).append("\n\n\nBlock:\n");

        out.setLength(out.length()-10);      //remove last delineator after final block
        return out.toString();
    }

    public boolean isEmpty(){
        return size == 0;
    }
}
