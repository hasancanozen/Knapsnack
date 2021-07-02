import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class separateInput {

    int largestNo = 0;
    int largestNoIndex;

    readInput readInput;
    writeOutput writeOutput = new writeOutput();
    int knapsackNo;
    int itemNo;
    int linesItem;
    int index = 1;
    ArrayList<Integer> valuesOfItems = new ArrayList<>();
    ArrayList<String> output = new ArrayList<>();
    Integer[] knapsackCapacities;
    Integer[][] matrixConstraints;
    String[] elementsItems;
    String[] elementsWeights;
    Integer[] itemsStored;
    int sayac =0;


    public void SeparateInput() throws IOException {
        //Scanner sc = new Scanner(System.in);
        //System.out.print("\nPlease enter pathname for input text file,");
        //System.out.print("\033[1m Format-> C:/Users/desktop/input.txt : \033[0m");
        //String pathname = sc.nextLine();
        String pathname = "C:\\Users\\hsncn\\Desktop\\sample2.txt";
        readInput = new readInput();
        readInput.readFile(pathname);

        System.out.println();

        elementsItems = readInput.input.get(0).split(" ");
        knapsackNo = Integer.parseInt(elementsItems[0]);
        itemNo = Integer.parseInt(elementsItems[1]);
        knapsackCapacities = new Integer[knapsackNo];
        matrixConstraints = new Integer[itemNo][knapsackNo];

        itemsStored = new Integer[itemNo];

        for (int i=0;i<itemsStored.length;i++){
            itemsStored[i]=0;
        }

        if (itemNo % 10 != 0)
            linesItem = (int) (itemNo / 10) + 1;
        else
            linesItem = (int) (itemNo / 10);

        for (int i = 1; i <= linesItem; i++) {
            elementsItems = readInput.input.get(i).split(" ");
            for (int j = 0; j < readInput.input.get(i).split(" ").length; j++) {
                valuesOfItems.add(Integer.parseInt(elementsItems[j]));
            }
            index++;
        }

        int linesKnapsack = 0;
        if (knapsackNo > 10 && knapsackNo % 10 != 0)
            linesKnapsack = (int) (knapsackNo / 10) + 1;
        else if (knapsackNo > 10 && knapsackNo % 10 == 0)
            linesKnapsack = (int) (knapsackNo / 10);
        else
            linesKnapsack = 1;

        int indexKnapsack = 0;

        for(int t=0;t<linesKnapsack;t++) {
            elementsItems = readInput.input.get(index).split(" ");

            for (int j = 0; j < elementsItems.length; j++) {
                knapsackCapacities[indexKnapsack] = Integer.parseInt(elementsItems[j]);
                indexKnapsack++;
            }

            index++;
        }

        int lineStart = 1 + linesItem + linesKnapsack;

        int t=0; //knapsack sayısına göre ayrılan veri bütünlerinin indexi
        int currentLine = lineStart; //matrix verilerinin başladığı line sayısını tutma

        for (int k = lineStart; k < readInput.input.size(); k = k + linesItem) {


            for (int i = 0; i < linesItem; i++) {

                elementsWeights = readInput.input.get(currentLine).split(" ");
                currentLine++;

                for (int j = 0; j < elementsWeights.length; j++) {

                    if (matrixConstraints[j][t] == null){

                        matrixConstraints[j][t] = Integer.parseInt(elementsWeights[j]);
                    }else{
                        matrixConstraints[j+sayac][t] = Integer.parseInt(elementsWeights[j]);
                    }

                }
                sayac += 10;
            }
            t++;
            sayac = 0;
        }

        sortingValues();

    }

    private void sortingValues() throws IOException {

        ArrayList<Integer> copyValuesOfItems = new ArrayList<>(valuesOfItems);

        float temp = 0;

        ArrayList<Float> birimDeger = new ArrayList<>();

        int[] cantaDeger = new int[knapsackNo];

        Collections.sort(copyValuesOfItems);
        Collections.reverse(copyValuesOfItems);

        for (int i=0; i<valuesOfItems.size(); i++){

            for (int j=0; j<knapsackNo; j++){

                temp += matrixConstraints[i][j];
            }

            temp = (float) temp / valuesOfItems.get(i);
            birimDeger.add(temp);
        }

        ArrayList<Float> copyBirimDeger = new ArrayList<>(birimDeger);
        Collections.sort(copyBirimDeger);

        for (int i=0; i<valuesOfItems.size(); i++){

            largestNoIndex = birimDeger.indexOf(copyBirimDeger.get(i));

            for (int j=0; j<knapsackNo; j++){

                cantaDeger[j] += matrixConstraints[largestNoIndex][j];
                itemsStored[largestNoIndex]=1;

                if (cantaDeger[j] > knapsackCapacities[j]){

                    for (int k=0; k<=j; k++){

                        cantaDeger[k] -= matrixConstraints[largestNoIndex][k];
                        itemsStored[largestNoIndex]=0;

                    }

                    largestNo -= valuesOfItems.get(largestNoIndex);
                    break;
                }
            }
            largestNo += valuesOfItems.get(largestNoIndex);
        }



        System.out.println(largestNo);

        for(int i = 0; i < itemsStored.length; i++){

            output.add("" + itemsStored[i]);
            System.out.println(itemsStored[i]);
        }

        writeOutput.createOutputFile(largestNo, output);

    }

}
