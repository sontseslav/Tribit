package tribit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 09.10.15.
 */

public class Main {
    public static List<Pyramid> container = new ArrayList<>();
    public static List<Pyramid> container2 = new ArrayList<>();

    public static void main(String[] args){
        String input = args[0];
        //String input = "1110111100111000";
        if (input.isEmpty()){
            System.out.println("Empty parameters, exiting.");
            System.exit(1);
        }
        int levels = (int)(Math.sqrt(input.length())/2);
        int baseQuantity = (1 + 2 * (levels*2 - 1));
        int baseIndex = 0;
        int headIndex = baseQuantity;
        boolean isUpsideDown = false;
        int currentLevel = levels;
        //queue?
        for (int i = 0; i < levels; i++){
            int iterationOnLevel = 1 + 2 * (currentLevel - 1 );
            for (int j = 0; j < iterationOnLevel; j++) {
                container.add(new Pyramid(isUpsideDown, (byte)Character.getNumericValue(input.charAt(headIndex++)),
                        (byte)Character.getNumericValue(input.charAt(baseIndex++)),
                        (byte)Character.getNumericValue(input.charAt(baseIndex++)),
                        (byte)Character.getNumericValue(input.charAt(baseIndex++))));
                if (j < iterationOnLevel - 1) {
                    isUpsideDown = !isUpsideDown;
                    baseIndex = baseIndex ^ headIndex;
                    headIndex = baseIndex ^ headIndex;
                    baseIndex = baseIndex ^ headIndex;
                }
            }
            if (i < levels - 1) {
                currentLevel--;
                baseQuantity = 1 + 2 * (currentLevel * 2 - 1);
                baseIndex = headIndex;
                headIndex += baseQuantity;
            }
        }
        printContainer();
        while (container.size() > 1){
            if (checkIsAllComplete()){
                if (isAllBitsIdentic()){
                    System.out.println(container.get(0).result);
                    break;
                }
                levels = (int)(Math.sqrt(container.size())/2);
                baseQuantity = (1 + 2 * (levels*2 - 1));
                baseIndex = 0;
                headIndex = baseQuantity;
                isUpsideDown = false;
                currentLevel = levels;
                for (int i = 0; i < levels; i++){
                    int iterationOnLevel = 1 + 2 * (currentLevel - 1 );
                    for (int j = 0; j < iterationOnLevel; j++) {
                        container2.add(new Pyramid(isUpsideDown, toOneBit(container.get(headIndex++).result),
                                toOneBit(container.get(baseIndex++).result),
                                toOneBit(container.get(baseIndex++).result),
                                toOneBit(container.get(baseIndex++).result)));
                        if (j < iterationOnLevel - 1) {
                            isUpsideDown = !isUpsideDown;
                            baseIndex = baseIndex ^ headIndex;
                            headIndex = baseIndex ^ headIndex;
                            baseIndex = baseIndex ^ headIndex;
                        }
                    }
                    if (i < levels - 1) {
                        currentLevel--;
                        baseQuantity = 1 + 2 * (currentLevel * 2 - 1);
                        baseIndex = headIndex;
                        headIndex += baseQuantity;
                    }
                }
                container = new ArrayList<>(container2);
                container2.clear();
                printContainer();
            }
            else{
                container.forEach(tribit.Pyramid::modifyResult);
                printContainer();
            }
        }
        while (!container.get(0).isComplete){
            container.get(0).modifyResult();
            printContainer();
        }
        System.out.println(toOneBit(container.get(0).result));
    }

    private static void printContainer(){
        for(Pyramid p : container){
            System.out.print(p + " ");
        }
        System.out.println();
    }

    private static boolean checkIsAllComplete(){
        boolean result = true;
        for (Pyramid p : container){
            result &= p.isComplete;
        }
        return result;
    }

    private static boolean isAllBitsIdentic(){
        int result = container.get(0).result;
        int count = 0;
        for(int i = 1; i <= container.size();i++){
            if (result == container.get(i).result) count++;
            else break;
        }
        return (count == container.size())?true:false;
    }

    private static byte toOneBit(int result){
        return (byte)((result == 0)?0:1);
    }
}
