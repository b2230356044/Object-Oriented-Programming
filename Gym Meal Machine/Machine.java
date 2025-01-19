import java.util.Arrays;
import java.lang.Math;
public class Machine {
        private  String[][] machine = new String[6][4];
        private int[][] counting = new int[6][4];
        private int[][] prices = new int[6][4];
        private int[][] proteins = new int[6][4];
        private int[][] carbs = new int[6][4];
        private int[][] fats = new int[6][4];
        private int[][] calories = new int[6][4];



        public void input(String[] inputFile) {

            for (String line : inputFile) {
                String[] tokens = line.split("\t");
                String foodName = tokens[0];

                int price = Integer.parseInt(tokens[1]);

                String[] array = tokens[2].split(" ");
                double protein = Double.parseDouble(array[0]);
                double carb = Double.parseDouble(array[1]);
                double fat = Double.parseDouble(array[2]);

                double calorie = carb * 4 + protein * 4 + fat * 9;

                fill(foodName, price, protein, carb, fat, calorie);

            }
        }

        private void fill(String foodName, int price, double protein, double carb, double fat, double calorie){
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 4; j++) {
                    if (machine[i][j] == null || foodName.equals(machine[i][j]) && counting[i][j] < 10){
                        machine[i][j] = foodName;
                        counting[i][j]++;
                        prices[i][j] = price;
                        proteins[i][j] = (int) Math.round(protein);
                        carbs[i][j] = (int) Math.round(carb);
                        fats[i][j] = (int) Math.round(fat);
                        calories[i][j] = (int) Math.round(calorie);
                        return;
                    }

                }
            }


        }

        public String[][] getMachine() {
            return machine;
        }

        public int[][] getCounting() {
            return counting;
        }

        public int[][] getPrices() {
            return prices;
        }

        public int[][] getProteins() {
            return proteins;
        }

        public int[][] getCarbs() {
            return carbs;
        }

        public int[][] getFats() {
            return fats;
        }

        public int[][] getCalories() {
            return calories;
        }
    }




