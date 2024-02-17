package testbestsolds;

import java.io.FileNotFoundException;

public class TestBestSolDS {

    static WordAnalysis w = new WordAnalysis();
    public static void main(String[] args) {

        
        try {
            w.readFileAndAnalyse("ds.txt");
        } catch (FileNotFoundException ex) {
            System.out.println("Exception");
        }
  
        //1
        System.out.println("documentLength: " + w.documentLength());
        //2
        System.out.println("uniqueWords: " + w.uniqueWords());
        //3
        System.out.println("Total occurences of (the) = " + w.totalOcuurrencesForWord("the"));
        System.out.println("Total occurences of (test) = " + w.totalOcuurrencesForWord("test"));
        //4
        System.out.println("totalWordsForLength (2) = " + w.totalWordsForLength(2));
        System.out.println("totalWordsForLength (4) = " + w.totalWordsForLength(4));
        //5
        System.out.println("operation 5: ");
        w.displayUniqueWords();
        //6
        displayOccurences("data");
        
        
        System.out.println("\ncheckAdjacent for (data) and (the): " +w.checkAdjacent("data", "the"));
        System.out.println("checkAdjacent for (the) and (data): " +w.checkAdjacent("the", "data"));
        System.out.println("checkAdjacent for (In) and (computer): " +w.checkAdjacent("In", "computer"));
        System.out.println("checkAdjacent for (computer) and (collection): " +w.checkAdjacent("computer", "collection"));



    }
    
    public static void displayOccurences(String word){
        LinkedList<WordOccurrence> lw = w.occurrences(word);
        if (!lw.empty()) {
            System.out.println("\noccurances of word: " + word);
            lw.findFirst();
            while (!lw.last()) {
                System.out.println("(" + lw.retrieve().lineNo + "," + lw.retrieve().position + ")");
                lw.findNext();
            }
            System.out.println("(" + lw.retrieve().lineNo + "," + lw.retrieve().position + ")");
            lw.findNext();
        }
        else
            System.out.println("\n### Word \"" + word + "\" dose not exist in the list ###");
    }
    
}
