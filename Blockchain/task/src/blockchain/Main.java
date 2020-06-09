package blockchain;

public class Main {
    public static void main(String[] args) {
        Blockchain chain = new Blockchain();
        chain.add("Open");
        chain.add("Close");
        chain.add("Drop");
        chain.add("Eat");
        chain.add("Push");

        System.out.print(chain.toString());
    }
}
