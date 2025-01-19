import java.util.Arrays;

public class Purchase {
    private final Machine machine;
    private String productSold;
    private int productSoldPrice;

    public Purchase(Machine machine) {
        this.machine = machine;
    }

    public void processInput(String[] inputFile) {
        for (String line : inputFile) {
            String[] tokens = line.split("\t");
            String choiceType = tokens[2];
            int targetValue = Integer.parseInt(tokens[3]);
            int budget = Arrays.stream(tokens[1].split(" "))
                    .mapToInt(Integer::parseInt)
                    .sum();

            handleTransaction(line, choiceType, targetValue, budget);
        }
    }

    private void handleTransaction(String inputLine, String choiceType, int targetValue, int budget) {
        executeSellOperation(budget, choiceType, targetValue);
        System.out.print("INPUT: ");
        System.out.println(inputLine);

        switch (productSold) {
            case "invalidNumber":
                printMessageWithChange("Number cannot be accepted. Please try again with another number.", budget);
                break;
            case "noMoney":
                printMessageWithChange("Insufficient money, try again with more money.", budget);
                break;
            case "notFound":
                printMessageWithChange("This slot is empty, your money will be returned.", budget);
                break;
            case "noProduct":
                printMessageWithChange("Product not found, your money will be returned.", budget);
                break;
            default:
                printSuccessfulPurchase(budget);
        }
    }

    private void printMessageWithChange(String message, int budget) {
        System.out.println("INFO: " + message);
        System.out.printf("RETURN: Returning your change: %d TL\n", budget);
    }

    private void printSuccessfulPurchase(int budget) {
        System.out.printf("PURCHASE: You have bought one %s\n", productSold);
        System.out.printf("RETURN: Returning your change: %d TL\n", budget-productSoldPrice);
    }
    private void executeSellOperation(int budget, String choiceType, int targetValue) {
        String[][] names = machine.getMachine();
        int[][] counting = machine.getCounting();
        int[][] carb = machine.getCarbs();
        int[][] protein = machine.getProteins();
        int[][] fat = machine.getFats();
        int[][] calorie = machine.getCalories();
        int[][] price = machine.getPrices();

        if ("PROTEIN".equals(choiceType)) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 4; j++) {
                    if (protein[i][j] - 5 <= targetValue && targetValue <= protein[i][j] + 5) {
                        productSoldPrice = price[i][j];
                        if (budget > productSoldPrice) {
                            productSold = names[i][j];
                            counting[i][j]--;
                            return;
                        }
                        productSold = "noMoney";
                        return;
                    }
                }
            }
            productSold = "noProduct";
        } else if ("CARB".equals(choiceType)) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 4; j++) {
                    if (carb[i][j] - 5 <= targetValue && targetValue <= carb[i][j] + 5) {
                        productSoldPrice = price[i][j];
                        if (budget > productSoldPrice) {
                            productSold = names[i][j];
                            counting[i][j]--;
                            return;
                        }
                        productSold = "noMoney";
                        return;
                    }
                }
            }
            productSold = "noProduct";
        } else if ("FAT".equals(choiceType)) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 4; j++) {
                    if (fat[i][j] - 5 <= targetValue && targetValue <= fat[i][j] + 5) {
                        productSoldPrice = price[i][j];
                        if (budget > productSoldPrice) {
                            productSold = names[i][j];
                            counting[i][j]--;
                            return;
                        }
                        productSold = "noMoney";
                        return;
                    }
                }
            }
            productSold = "noProduct";
        } else if ("CALORIE".equals(choiceType)) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 4; j++) {
                    if (calorie[i][j] - 5 <= targetValue && targetValue <= calorie[i][j] + 5) {
                        productSoldPrice = price[i][j];
                        if (budget > productSoldPrice) {
                            productSold = names[i][j];
                            counting[i][j]--;
                            return;
                        }
                        productSold = "noMoney";
                        return;
                    }
                }
            }
            productSold = "noProduct";
        } else if ("NUMBER".equals(choiceType)) {
            if (targetValue > 23) {
                productSold = "invalidNumber";
                return;
            }
            int i = targetValue / 4;
            int j = targetValue % 4;
            if (names[i][j] != null) {
                productSoldPrice = price[i][j];
                if (budget > productSoldPrice) {
                    productSold = names[i][j];
                    counting[i][j]--;
                    return;
                }
                productSold = "noMoney";
                return;
            } else {
                productSold = "notFound";
                return;
            }
        } else {
            productSold = "noProduct";
        }
    }
    public void printMachine() {
        String[][] names = machine.getMachine();
        int[][] calorie = machine.getCalories();
        int[][] counting = machine.getCounting();

        System.out.println("-----Gym Meal Machine-----");
        for (int i = 0; i < names.length; i++) {
            for (int j = 0; j < names[i].length; j++) {
                if (names[i][j] != null) {
                    System.out.print(names[i][j] + "(" + calorie[i][j] + ", " + counting[i][j] + ")___");
                } else {
                    System.out.print("___(0, 0)___");
                }
            }
            System.out.println();
        }
        System.out.println("----------");
    }


}







