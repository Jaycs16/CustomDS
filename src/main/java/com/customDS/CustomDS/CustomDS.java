package com.customDS.CustomDS;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@RestController
class CustomDS{

    private int sizeOfArrayOfNumbers;
    private HashMap<Integer,Integer> mapOfNumber;
    private ArrayList<Integer> arraylistOfNumbers;
    private ArrayList<Integer> arraylistOfFrequencyOfNumbers;
    public CustomDS() {

        mapOfNumber = new HashMap<>();
        arraylistOfNumbers = new ArrayList<>();
        arraylistOfFrequencyOfNumbers = new ArrayList<>();
        sizeOfArrayOfNumbers =0;
    }

    @GetMapping("/add")
    boolean insert(@RequestParam(value ="number",defaultValue = "10") int number){
        if(mapOfNumber.containsKey(number)){
            Integer indexOfGivenNumber = mapOfNumber.get(number);
            arraylistOfFrequencyOfNumbers.set(indexOfGivenNumber,arraylistOfFrequencyOfNumbers.get(indexOfGivenNumber)+1);
        }else{
            int nextVacantIndex = arraylistOfNumbers.size();
            arraylistOfNumbers.add(nextVacantIndex,number);
            mapOfNumber.put(number, nextVacantIndex);
            arraylistOfFrequencyOfNumbers.add(nextVacantIndex,1);
        }
        sizeOfArrayOfNumbers++;
        return true;
    }
    @GetMapping("/delete")
    boolean delete(@RequestParam(value = "number") int number){
        if(mapOfNumber.containsKey(number)){
            Integer indexOfGivenNumber = mapOfNumber.get(number);
            Integer frequencyOfNumber = arraylistOfFrequencyOfNumbers.get(indexOfGivenNumber);
            if(frequencyOfNumber>1){
                arraylistOfFrequencyOfNumbers.set(indexOfGivenNumber,frequencyOfNumber-1);
            }else{
                int indexOfLastElement = arraylistOfNumbers.size()-1;
                int lastItemOfArrayOfNumbers = arraylistOfNumbers.get(indexOfLastElement);
                int indexOfNumberToDelete = mapOfNumber.get(number);
                mapOfNumber.put(lastItemOfArrayOfNumbers,indexOfNumberToDelete);
                arraylistOfNumbers.set(indexOfNumberToDelete,lastItemOfArrayOfNumbers);
                arraylistOfNumbers.remove(indexOfLastElement);
                mapOfNumber.remove(number);
                Integer frequencyOfLastItemOfArrayOfNumber = arraylistOfFrequencyOfNumbers.get(indexOfLastElement);
                arraylistOfFrequencyOfNumbers.set(indexOfNumberToDelete, frequencyOfLastItemOfArrayOfNumber);
                arraylistOfFrequencyOfNumbers.remove(indexOfLastElement);
            }
            sizeOfArrayOfNumbers--;
            return true;
        }

        return false;
    }
    @GetMapping("/search")
    boolean search(@RequestParam(value = "number") int number){
        if(mapOfNumber.containsKey(number)){
            return true;
        }
        return false;
    }
    @GetMapping("/random")
    int random(){
        int randomIndex = new Random().nextInt(arraylistOfNumbers.size()-1);
        return arraylistOfNumbers.get(randomIndex);
    }
    @GetMapping("/size")
    int size(){
        return sizeOfArrayOfNumbers;
    }
    @GetMapping("/allDistinctItem")
    ArrayList<Integer> getAllDistinctItem(){
        return arraylistOfNumbers;
    }
    @GetMapping("/allItem")
    ArrayList<Integer> getAllItems(){
        ArrayList<Integer> alWithDuplicates = new ArrayList<>(sizeOfArrayOfNumbers);
        for (int i=0;i<arraylistOfNumbers.size();i++){
            for(int j=0;j<arraylistOfFrequencyOfNumbers.get(i);j++){
                alWithDuplicates.add(arraylistOfNumbers.get(i));
            }
        }
        return alWithDuplicates;
    }
    @Override
    public String toString() {
        return "CustomDS{" +"\n"+
                "mapOfNumber" + mapOfNumber +"\n"+
                "arraylistOfNumbers" +arraylistOfNumbers+"\n"+
                "arraylistOfFrequencyOfNumbers" +arraylistOfFrequencyOfNumbers+"\n"+
                '}';
    }
}