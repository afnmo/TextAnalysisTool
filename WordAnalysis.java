
package testbestsolds;
import java.util.Scanner;
import java.io.*;
public class WordAnalysis {
    private LinkedList<WordInformation>[] arrayOfDifferentLengths;
    private WordInformation sortedArray[];
    private int n;
    private int m;
 
    public void readFileAndAnalyse(String fileName) throws FileNotFoundException {
   
        Scanner read1 = new Scanner(new File(fileName));
        int lineNo = 0;

        // search for k -longest word length-
        // initialize -arrayOfDifferentLengths- by k+1
        String word1 = "", word2;
        while (read1.hasNext()) {
            word2 = read1.next();
            word2 = word2.trim();
            word2 = word2.replaceAll("(?!['-])\\p{Punct}", "");
            if (word2.length() > word1.length()) {
                word1 = word2;
            }
        }
        int k = word1.length();

        arrayOfDifferentLengths = (LinkedList<WordInformation>[]) new LinkedList[k + 1];
        for (int i = 0; i < arrayOfDifferentLengths.length; i++) {
            arrayOfDifferentLengths[i] = new LinkedList<WordInformation>();
        }
        // printArrayContents();

        // read the file
        Scanner read2 = new Scanner(new File(fileName));
        while (read2.hasNextLine()) {
            lineNo++;
            String line = read2.nextLine();
            line = line.trim();
            String[] words = line.split("\\s+");
            int position = 1;
            for (int i = 0; i < words.length; i++) {
                
               // System.out.println("\\n");
                if (words[i].equals("\\n")) {
                    position = 1;
                    lineNo++;
                    continue;
                }
                String updatedWord = words[i].replaceAll("(?!['-])\\p{Punct}", "");
                
                if(updatedWord.equals("")){
                    continue;
                }
                n++;
                int x = updatedWord.length();
              //  System.out.println(updatedWord + " in lineNo = " + lineNo);
                // i think we have to search for the word here
                boolean occuranceAdded = false;

                if (!arrayOfDifferentLengths[x].empty()) {
                    arrayOfDifferentLengths[x].findFirst();
                    while (!arrayOfDifferentLengths[x].last()) {
                        
                        if (arrayOfDifferentLengths[x].retrieve().word.equalsIgnoreCase(updatedWord)) // if exist
                        {
                            occuranceAdded = true;
                            //System.out.println(updatedWord + ": " + arrayOfDifferentLengths[x].retrieve().word.equals(updatedWord));
                            arrayOfDifferentLengths[x].retrieve().occList.insert(new WordOccurrence(lineNo, (position))); // insert a new occ
                            //  arrayOfDifferentLengths[x].size++;
                            
                        }
                        //  System.out.println(updatedWord + ": " + arrayOfDifferentLengths[x].retrieve().word.equals(updatedWord));
                        arrayOfDifferentLengths[x].findNext();
                    }
                    // check last node
                    if (arrayOfDifferentLengths[x].retrieve().word.equalsIgnoreCase(updatedWord)) // if exist last node
                    {
                        occuranceAdded = true;
                        arrayOfDifferentLengths[x].retrieve().occList.insert(new WordOccurrence(lineNo, (position))); // insert a new occ
                        //  arrayOfDifferentLengths[x].size++;
                    }
                }
                // else; not found 
                if (!occuranceAdded) {
                    WordOccurrence myOccurrence = new WordOccurrence(lineNo, (position)); // create a new occ
                    LinkedList<WordOccurrence> occList = new LinkedList<WordOccurrence>(); // new List for occ
                    occList.insert(myOccurrence); // insert the occ
                    WordInformation myWord = new WordInformation(updatedWord, occList.size); // create word obj
                    arrayOfDifferentLengths[x].insert(myWord);
                    //System.out.println(arrayOfDifferentLengths[x].retrieve().word);
                }
                position++;
            }
        }

        for (int i = 1; i < k + 1; i++) {
            if (arrayOfDifferentLengths[i] != null) {
                //System.out.println("number of words with length (" + i + ") = " + arrayOfDifferentLengths[i].size());
                m += arrayOfDifferentLengths[i].size(); // number of unique words m
            }
        }
        
        sortedArray = new WordInformation[m + 1];
        // insert words in sortedArray
        int c = 0;
        for (int i = 0; i < k + 1; i++) {
            if (!arrayOfDifferentLengths[i].empty()) {
                arrayOfDifferentLengths[i].findFirst();
                while (!arrayOfDifferentLengths[i].last()) {
                    sortedArray[c++] = arrayOfDifferentLengths[i].retrieve();
                    arrayOfDifferentLengths[i].findNext();
                }
                sortedArray[c++] = arrayOfDifferentLengths[i].retrieve();
            }
        }

        // order the sortedArray
        WordInformation temp = sortedArray[0];
        for (int i = 0; i < m + 1; i++) {
            for (int j = i + 1; j < m; j++) {
                if (sortedArray[j].occList.size() > sortedArray[i].occList.size()) {
                    temp = sortedArray[i];
                    sortedArray[i] = sortedArray[j];
                    sortedArray[j] = temp;
                }
            }
        }

    }
    
    
    
    public void printArray() {
        for (int i = 1; i < arrayOfDifferentLengths.length; i++) {
            //System.out.println((i) + " " + arrayOfDifferentLengths[i].retrieve().word);
            if (!arrayOfDifferentLengths[i].empty()) {
                arrayOfDifferentLengths[i].findFirst();
                while (!arrayOfDifferentLengths[i].last()) {
                    System.out.println((i) + " " + arrayOfDifferentLengths[i].retrieve().word);
                    arrayOfDifferentLengths[i].findNext();
                }
                System.out.println((i) + " " + arrayOfDifferentLengths[i].retrieve().word);

            }
        }
    }

    // operations 1
    public int documentLength(){ // O(1)
        return n;
    }
    
    // operations 2
    public int uniqueWords(){ // O(1)
        return m;
    }
    
    // operations 3
    public int totalOcuurrencesForWord(String w) { // case 1 = O(m/k), case 2 = O(n) or O(m)
        int totalOcc = 0;
        int x = w.length();
        int arrayLength = arrayOfDifferentLengths.length;
        if ((x > 0) && (x < arrayLength) && (!arrayOfDifferentLengths[x].empty())) {
            arrayOfDifferentLengths[x].findFirst();
            while(!arrayOfDifferentLengths[x].last()){
                if (arrayOfDifferentLengths[x].retrieve().word.equals(w)){
                    totalOcc = arrayOfDifferentLengths[x].retrieve().occList.size();
                } 
                arrayOfDifferentLengths[x].findNext();
            }
            if (arrayOfDifferentLengths[x].retrieve().word.equals(w)){
                    totalOcc = arrayOfDifferentLengths[x].retrieve().occList.size();
                }
        }
        
        return totalOcc;
    }
    
    // operations 4
    public int totalWordsForLength(int l){ // O(1)
        return arrayOfDifferentLengths[l].size();
    }
    
    // operations 5
    public void displayUniqueWords(){ // O(m)
        for(int i = 0; i<m; i++){
            System.out.println("("+sortedArray[i].word + "," + sortedArray[i].occList.size()+")");
        }
    }
    
    // operations 6
    public LinkedList<WordOccurrence> occurrences(String w) { // O(n)
        
        LinkedList<WordOccurrence> tempList = new LinkedList<WordOccurrence>();
        int x = w.length();
        int arrayLength = arrayOfDifferentLengths.length;
        if ((x > 0) && (x < arrayLength) && (!arrayOfDifferentLengths[x].empty())) {
            arrayOfDifferentLengths[x].findFirst();
            while (!arrayOfDifferentLengths[x].last()) {
                if (arrayOfDifferentLengths[x].retrieve().word.equals(w)) {
                    tempList = arrayOfDifferentLengths[x].retrieve().occList;
                }
                arrayOfDifferentLengths[x].findNext();
            }
            if (arrayOfDifferentLengths[x].retrieve().word.equals(w)) {
                tempList = arrayOfDifferentLengths[x].retrieve().occList;
            }
        }



        return tempList;
    }

    // operations 7
    public boolean checkAdjacent(String w1, String w2) { // O(n)
        LinkedList<WordOccurrence> occ1 = occurrences(w1);
        LinkedList<WordOccurrence> occ2 = occurrences(w2);

        boolean adjacent = false;
        if (!occ1.empty() && !occ2.empty()) {
            occ1.findFirst();
            occ2.findFirst();
            while (!occ1.last() && !occ2.last()) {
                if (occ1.retrieve().lineNo == occ2.retrieve().lineNo) {
                    // if w2 before w1                                                if w2 after w1
                    if (occ1.retrieve().position == (occ2.retrieve().position) + 1 || occ1.retrieve().position == (occ2.retrieve().position) - 1) {
                        adjacent = true;
                    }
                }
                occ1.findNext();
                occ2.findNext();
            }
            if (occ1.last() && !occ2.last()) { // are we out of loop because of occ1?
                while (!occ2.last()) {
                    if (occ1.retrieve().lineNo == occ2.retrieve().lineNo) {
                        if (occ1.retrieve().position == (occ2.retrieve().position) + 1 || occ1.retrieve().position == (occ2.retrieve().position) - 1) {
                            adjacent = true;
                        }
                    }
                    occ2.findNext();
                }
            }

            if (occ2.last() && !occ1.last()) { // are we out of loop because of occ2?
                while (!occ1.last()) {
                    if (occ1.retrieve().lineNo == occ2.retrieve().lineNo) {
                        if (occ1.retrieve().position == (occ2.retrieve().position) + 1 || occ1.retrieve().position == (occ2.retrieve().position) - 1) {
                            adjacent = true;
                        }
                    }
                    occ1.findNext();
                }
            }

            // now we have to compare the last node for both occ1 & occ2
            if (occ1.retrieve().lineNo == occ2.retrieve().lineNo) {
                if (occ1.retrieve().position == (occ2.retrieve().position) + 1 || occ1.retrieve().position == (occ2.retrieve().position) - 1) {
                    adjacent = true;
                }
            }
        }
        return adjacent;


    }
}