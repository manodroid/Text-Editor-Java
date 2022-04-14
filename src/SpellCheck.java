import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class SpellCheck {
    public static void main(String[] args) {
        // a hashset for storing words and a buffReader for reading the dictionary
        Set<String> words = generateWordList("/home/guts/Text-Editor-Java/src/wordlist.txt");
        // create hashmap with each suggestion sorted by coefficient
    }


    public static List<String> suggestions(String input, Set<String> dictionary){
        // generating suggestions by creating all mutations of the given input
        // using the Norvig's algorithm
        Set<String> edits = new HashSet<>();
        for(int i=0; i < input.length(); ++i) edits.add(input.substring(0, i) + input.substring(i+1));
        for(int i=0; i < input.length()-1; ++i) edits.add(input.substring(0, i) + input.charAt(i+1) + input.charAt(i) + input.substring(i+2));
        for(int i=0; i < input.length(); ++i) for(char c='a'; c <= 'z'; ++c) edits.add(input.substring(0, i) + c + input.substring(i+1));
        for(int i=0; i <= input.length(); ++i) for(char c='a'; c <= 'z'; ++c) edits.add(input.substring(0, i) + c + input.substring(i));
        edits.removeIf(edit -> !dictionary.contains(edit));
        return new ArrayList<>(edits);
    }


    public static double filterSuggestions(String input, String word){
        // find the coefficient of similarity of suggestions using jaccard indez
        Set<Character> setInput = input.chars().mapToObj(e -> (char) e)
                                            .collect(Collectors.toSet());
        Set<Character> setWord  = word.chars().mapToObj(e -> (char) e)
                                        .collect(Collectors.toSet());

        var result = new HashSet<>(setInput);
        result.addAll(setWord);
        double union = result.size();
        result.retainAll(setWord);
        double intersect = result.size();
        return 1.0 - (intersect / union);
    }

    public static Set<String> generateWordList(String fileName){
        Set<String> words = new HashSet<>();
        try {
            final BufferedReader bufRead = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufRead.readLine()) != null) words.add(line);
            bufRead.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

}

