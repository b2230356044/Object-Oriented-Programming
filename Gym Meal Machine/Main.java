public class Main {
    public static void main(String[] args) {
        String[] inputFile = FileInput.readFile(args[0], true, true);
        String[] inputPurchase = FileInput.readFile(args[1], true, true);

        Machine machine = new Machine();
        Purchase purchase = new Purchase(machine);

        machine.input(inputFile);
        purchase.printMachine();


        purchase.processInput(inputPurchase);
        purchase.printMachine();



    }
}











