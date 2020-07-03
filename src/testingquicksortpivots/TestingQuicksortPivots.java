/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testingquicksortpivots;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class TestingQuicksortPivots {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        int size = 10000;    // length of array of numbers to be sorted
        int[] setOrigArray = new int[size];    // array of randomly generated numbers
        int[] origArray = new int[setOrigArray.length]; // set array of randomly generated numbers to use with each quicksort type
        long start;     // start time of sorting
        long finish;    // finish time of sorting
        
        // create file or add to existing file to save data to
        java.io.File x = new java.io.File("Beachmarks.txt");
        
        // write "NEW TEST TRIAL" to file
        writeNewTrialToFile(x);
        
        // fill orginal array with random numbers
        for (int i = 0; i< setOrigArray.length; i++)
            setOrigArray[i] = (int)(Math.random()*size + 1);
        
        // start testing each different method
        for (int j = 0; j < 4; j++){
            
            // set new array to original randomly generated array data
            setOriginalRanNumArray(setOrigArray, origArray);
            
            // while testing each different method test for random, ordered and reverse ordered arrays
            for(int k = 0; k < 3; k++){
                
                // on the third trial reverse the array
                if(k == 2)
                    reverseArray(origArray);
                
                // record start time of sorting
                start = System.nanoTime();

                // quicksort array based on what loop for j this is
                quickSortMethod(origArray, 0, origArray.length - 1, j);

                // record finish time of sorting
                finish = System.nanoTime();
                
                //subtract start from finish time and write time to file
                writeToFile(convertAndFormatTime(finish, start, j, k), x);   
            } // end for
            
            //add a line break between trials of different methods
            writeToFile("", x);
    } //end for
} // end main()
//*******************************************************************
    
    public static void setOriginalRanNumArray(int[] orig, int[] newArray){
        //method sets the new array to the original array
        
        for (int i = 0; i < orig.length; i++)
            newArray[i] = orig[i];        
    } // end setOriginalRanNumArray()
//*******************************************************************    
    
    public static String convertAndFormatTime(long finish, long start, int method, int type){
        // finds the duration between the start and finish time of the array being sorted
        // and converts the time into seconds then returns a string with the time and
        // which sorting type is occuring

        String methodType;  // sets the type of partion which is happening when sorting
        String typeOfMethod;    // tells the condition of the array if random, sorted or revsered

        // finding the duration of sorting
        long c = finish - start;
        
        // set which partion is being used for the quicksort
        if(method == 0)
            methodType = "First Variable Partion";
        else if(method == 1)
            methodType = "Middle Variable Partion";
        else if(method == 2)
            methodType = "Random Variable Partion";
        else
            methodType = "Finding Middle Variable Comparing First, Middle and Last Variable Partion";
        
        // set which version of the array is being sorted
        if(type == 0)
            typeOfMethod = "From Random Array Of Numbers";
        else if(type == 1)
            typeOfMethod = "From Already Sorted Array Of Numbers";
        else
            typeOfMethod = "From Reverse of Already Sorted Array Of Numbers";
        
        //format time to seconds
        String d = String.format("%12.8f", (double)c/100000000);
        
        //return string to be written to file
        return "Total Time for Quicksorting By " + methodType + " " + typeOfMethod + " is: " + d + " seconds.";
        
    } // end convertAndFormatTime()
//*******************************************************************    
    
    public static void writeToFile(String a, java.io.File x) throws FileNotFoundException, IOException{
        //writes the string to file x
        
        //create a new printerwriter with the option to append the file
        java.io.PrintWriter y = new java.io.PrintWriter(new FileWriter(x, true));

        //append string to file
        y.println(a);
        
        //close printwriter
        y.close();
        
    } // end writeToFile()
//*******************************************************************    
    
    public static void writeNewTrialToFile(java.io.File x) throws FileNotFoundException, IOException{
        //writes "THIS IS A NEW TRIAL" to file x
        
        //creates a new printwriter with the option to append the file
        java.io.PrintWriter y = new java.io.PrintWriter(new FileWriter(x, true));
        
        //append string to file
        y.println("THIS IS A NEW TRIAL");
        
        //close printerwriter
        y.close();
        
    } // end writeNewTrialToFile()
//*******************************************************************
    
    public static void printArray(int[] a){
        //prints array (used for testing)
        
        for (int i=0; i < a.length; i++){
            System.out.println(a[i]);
        } // end for
    } // end printArray
//*******************************************************************
    
    public static long startTime(){
        // returns system's time (not used)
        
        // set and return start time
        long startTime = System.nanoTime();
        return startTime;
        
    } // end startTime()
//*******************************************************************    
    
    public static void quickSortMethod(int[] origArray, int startIndex, int endIndex, int option){
        // takes in the original array and start of numbers need to be sorted and the end index
        // depending on which loop the program is running on decides which pivot option to run
        // and then sorts the data 
        
        int pivotIndex; // the part of the array picked to be partitioned
        
        // find the partion
        if (startIndex < endIndex){
            
            // partition by first first index
            if (option == 0)
                pivotIndex = partitionFirst(origArray, startIndex, endIndex);
            
            // partition by selecting the middle index
            else if(option == 1)
                pivotIndex = partitionMiddle(origArray, startIndex, endIndex);
            
            // partition by picking a random number
            else if(option ==2)
                pivotIndex = partitionRandom(origArray, startIndex, endIndex);
            
            // partition by picking the mid num compairing the first, last and middle index
            else
                pivotIndex = partitionAverage(origArray, startIndex, endIndex);
            
            //recurcive loop based on the partition picked of lower end
            quickSortMethod(origArray, startIndex, pivotIndex - 1, option);

            //resucrive loop based on the partition picked of higher end
            quickSortMethod(origArray, pivotIndex + 1, endIndex, option);
        } // end if            
    } // end quickSortMethod()              
//*******************************************************************    
    
    public static int partitionFirst(int[] origArray, int startIndex, int endIndex){
        // this method picks the first number in the index as the partition point
        
        int pivotIndex; // pivot index
        int pivot;  // data at pivot point
        int midIndex = startIndex;  // used for swapping positions 
        
        // set the pivot point as the startIndex
        pivotIndex = startIndex;
        
        // set the data from the array
        pivot = origArray[pivotIndex];
        
        // swap the pivot at the end of the array
        swap(origArray, pivotIndex, endIndex);
        
        // for the partition of the array
        for (int i = startIndex; i < endIndex; i++){
            
            if (origArray[i] < pivot){
                
                // swap the numbers if the orig number is less than the pivot point
                swap(origArray, i, midIndex);
                
                // move one up the index
                midIndex = midIndex + 1;
            } // end if
        } // end for
        
        // swap the stored pivot point to the correct location in the array
        swap(origArray, midIndex, endIndex);
        
        // return new mid index
        return midIndex;
    } // end partitionFirst()
//*******************************************************************    
    
    public static int partitionMiddle(int[] origArray, int startIndex, int endIndex){
        // pick the middle number in the partition of the array to be the pivot point
        
        int pivotIndex; // index of pivot point
        int pivot;  // pivot point value
        int midIndex = startIndex;  // used for swapping positions
        
        // set the pivot point to the middle index
        pivotIndex = (startIndex + endIndex) / 2;
        
        // set the pivot point value
        pivot = origArray[pivotIndex];
        
        // move the pivot point to the endIndex so it's out of the way
        swap(origArray, pivotIndex, endIndex);
        
        // for the partition of the array
        for (int i = startIndex; i < endIndex; i++){
            
            if (origArray[i] < pivot){
                
                // swap the numbers if the orig number is less than the pivot point
                swap(origArray, i, midIndex);
                
                // move one up the index
                midIndex = midIndex + 1;
            } // end if
        } // end for
        
        // swap the stored pivot point to the correct location in the array
        swap(origArray, midIndex, endIndex);
        
        // return midIndex location
        return midIndex;
    } // end partitionMiddle()
//*******************************************************************    
    
    public static int partitionRandom(int[] origArray, int startIndex, int endIndex){
        // pick a random number in between the start and end index and return
        // the value as the pivot point
        
        int pivotIndex; // index of the pivot point
        int pivot;  // value of the pivot point
        int midIndex = startIndex;  // used for swapping positions
        
        // pick and set a random number between the start and end indexes
        int randomNum = startIndex + (int)(Math.random() * ((endIndex - startIndex) + 1));
        
        // set the random number generated as the pivot point
        pivotIndex = randomNum;
        
        // store the pivot point's value
        pivot = origArray[pivotIndex];
        
        // move the pivot point to the endIndex so it's out of the way
        swap(origArray, pivotIndex, endIndex);
        
        // for the partition of the array
        for (int i = startIndex; i < endIndex; i++){
            
            if (origArray[i] < pivot){
                
                // swap the numbers if the orig number is less than the pivot point
                swap(origArray, i, midIndex);
                
                // move one up the index
                midIndex = midIndex + 1;
            } // end if
        } // end for
        
        // swap the stored pivot point to the correct location in the array
        swap(origArray, midIndex, endIndex);
        
        //return middleIndex location
        return midIndex;
    } // end partitionRandom()
//*******************************************************************    
    
    public static int partitionAverage(int[] origArray, int startIndex, int endIndex){
        // this method takes the first index and the end index to find the middle
        // index and then finds which number value for those indexes lands in the
        // middle of the three and then sets that as the pivot point and returns the mid index
        
        int pivotIndex; // index of pivot point
        int pivot;  // value of pivot point
        int midIndex = startIndex;  // used for swapping positions
        int middleIndex = (int)((startIndex + endIndex)/2); // finds the middle index point in between the start and end indexes
        
        // if start value > end value > middle value (set pivot index as end index)
        if (origArray[startIndex] > origArray[endIndex] && origArray[endIndex] > origArray[middleIndex])
            pivotIndex = endIndex;
        
        // if middle value > end value > start val (set pivot index as end index)
        else if(origArray[middleIndex] > origArray[endIndex] && origArray[endIndex] > origArray[startIndex])
            pivotIndex = endIndex;
        
        // if mid val > start val > end val (set pivot index as start index)
        else if (origArray[middleIndex] > origArray[startIndex] && origArray[startIndex] > origArray[endIndex])
            pivotIndex = startIndex;
        
        // if end val > start val > mid val (set pivot index as start index)
        else if (origArray[endIndex] > origArray[startIndex] && origArray[startIndex] > origArray[middleIndex])
            pivotIndex = startIndex;
        
        // if end index and start index are not the middle value (set pivot index as mid val)
        else
            pivotIndex = middleIndex;            
        
        // set value of pivot point 
        pivot = origArray[pivotIndex];
        
        // move the pivot point to the endIndex so it's out of the way
        swap(origArray, pivotIndex, endIndex);
        
        // for the partition of the array
        for (int i = startIndex; i < endIndex; i++){
            
            if (origArray[i] < pivot){
                
                // swap the numbers if the orig number is less than the pivot point
                swap(origArray, i, midIndex);
                
                // move one up the index
                midIndex = midIndex + 1;
            } // end if
        } // end for
        
        // swap the stored pivot point to the correct location in the array
        swap(origArray, midIndex, endIndex);
        
        //return middleIndex location
        return midIndex;
    } // end partitionAverage()
//*******************************************************************    
    
    public static void swap(int[] origArray, int first, int second){
        // this method takes in an array and swaps a value for another value 
        // in the same array
        
        int c;  //swapping val
        
        // set c to the first val
        c = origArray[first];
        
        // replace the first val with the second val
        origArray[first] = origArray[second];
        
        // replace second val with the original first val that was stored before swap
        origArray[second] = c;
    } // end swap()
//*******************************************************************    
    
    public static void reverseArray(int[] origArray){
        // this method is used to reverse the original array 
        
        // run this loop for half the array's value
        for (int i = 0; i < (origArray.length / 2); i++){
            
            // set temp val to the val in the orig array
            int temp = origArray[i];
            
            // set the value on the top part of the array with the val of the
            // array on the opposite side of the array
            origArray[i] = origArray[origArray.length - i - 1];
            
            //set the val on the bottom part of the array with the top part of the
            //array's val that was stored
            origArray[origArray.length - i - 1] = temp;
        } // end for
    } // end reverseArray()
//*******************************************************************
    
} // end class TestingQuicksortPivots