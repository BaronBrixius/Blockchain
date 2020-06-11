package blockchain;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter how many zeros the hash must start with: ");
        Blockchain chain = new Blockchain(sc.nextInt());

        chain.add("Open");
        chain.add("Close");
        chain.add("Drop");
        chain.add("Eat");
        chain.add("Push");

        System.out.print(chain.toString());
    }
}
