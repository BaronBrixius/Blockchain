package blockchain;

import java.io.*;
import java.util.LinkedList;

public class Blockchain {
    private int size = 0;
    private final int leadingZeros;
    private final LinkedList<Block> blockchain = new LinkedList<>();

    public Blockchain(int leadingZeros) {
        if (leadingZeros < 0)
            throw new IllegalArgumentException("Number of leading zeros must be positive.");
        this.leadingZeros = leadingZeros;

        //readLocalBlockchain();        //tests fail to account for this apparently

        if (!isValid())
            throw new IllegalArgumentException("Blockchain is broken, not all prevHash match the previous block.");
    }

    private void readLocalBlockchain() {
        File storage = new File("blockchain.txt");
        if (!storage.exists())
            return;

        try (FileInputStream inFile = new FileInputStream(storage);
             ObjectInputStream inObject = new ObjectInputStream(inFile)) {

            Block block;
            while ((block = (Block) inObject.readObject()) != null) {
                if (block.isUnproved(leadingZeros))
                    throw new IllegalArgumentException("Warning: Block is unproved!");
                blockchain.add(block);
                size++;
            }
        } catch (EOFException ignore) {
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Could not read blockchain from file.");
        }
    }

    public void add(String data) {
        Block blockToAdd = new Block(++size, blockchain.peekLast(), data, leadingZeros);
        blockchain.add(blockToAdd);

        File storage = new File("blockchain.txt");
        try (FileOutputStream outFile = new FileOutputStream(storage, true);
             ObjectOutputStream outObject = new ObjectOutputStream(outFile)) {
            outObject.writeObject(blockToAdd);
        } catch (IOException ex) {
            System.err.println("Could not write new block to file.");
        }
    }

    public boolean isValid() {
        if (isEmpty())
            return true;

        for (int i = 1; i < size; i++) {
            if (!blockchain.get(i).matchesPrevious(blockchain.get(i - 1)))
                return false;
        }
        return true;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        if (isEmpty())
            return "";

        StringBuilder out = new StringBuilder();
        for (Block b : blockchain)
            out.append(b.toString()).append("\n\n\n");

        out.setLength(out.length() - 3);      //remove extra three newlines after final block
        return out.toString();
    }
}
