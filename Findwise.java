import java.util.*;
import java.lang.Math.*;


public class Findwise {

    // Ripped from some Stackoverflow answer
    static <K,V extends Comparable<? super V>>
    SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
            new Comparator<Map.Entry<K,V>>() {
                @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                    int res = e2.getValue().compareTo(e1.getValue());
                    return res != 0 ? res : 1;
                }
            }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    public static void main(String[] args) {
        
        if (args.length == 0) {
            System.err.println("Please provide a search query!");
            System.exit(1);
        }

        Map<String, String> documents = new HashMap<>();
        documents.put("document 1", "the brown fox jumped over the brown dog");
        documents.put("document 2", "the lazy brown dog sat in the corner");
        documents.put("document 3", "the red fox bit the lazy dog");
        //documents.put("document 4", "brown brown brown");

        double total_documents = documents.size();

        Map<String, Integer> words_per_document = new HashMap<>();
        for (String key : documents.keySet()) {
            words_per_document.put(key, 0);
        }

        HashMap<String, TreeMap<String, Integer>> reversed_index = new HashMap<>();

        for (String document_key : documents.keySet()) {

            String document_text = documents.get(document_key);
            StringTokenizer st = new StringTokenizer(document_text);

            int token_counter = 0;
            
            while (st.hasMoreTokens()) {
                String word = st.nextToken();
                token_counter++;

                TreeMap<String, Integer> document_occurences = reversed_index.get(word);

                if (document_occurences == null) {
                    TreeMap<String, Integer> occurence_map = new TreeMap<>();
                    occurence_map.put(document_key, 1);
                    reversed_index.put(word, occurence_map);
                    //System.out.println(word + ": " + document_key + ", 1");
                    continue;
                }
                
                Integer freq = document_occurences.get(document_key);

                if (freq == null) {
                    document_occurences.put(document_key, 1);
                    //System.out.println(word + ": " + document_key + ", 1");
                    continue;
                }

                document_occurences.put(document_key, freq + 1);
                //System.out.println(word + ": " + document_key + ", " + (freq + 1));
            }

            words_per_document.put(document_key, token_counter);
        }

        TreeMap<String, Double> total_tf_ids = new TreeMap<>();

        for (String query_string : args) {

            TreeMap<String, Integer> document_occurences = reversed_index.get(query_string);
            if (document_occurences == null) {
                continue;
            }
            
            int documents_containing_word = document_occurences.size();

            // IDF is 0
            if (total_documents == documents_containing_word) {
                continue;
            }

            double idf = java.lang.Math.log(total_documents / documents_containing_word);

            for (String document : document_occurences.keySet()) {
                double word_occurences = document_occurences.get(document);
                
                // Division seems to give better results, but puts document 3
                // before document 1 for "fox", contradicting the specification.
                double tf = word_occurences / words_per_document.get(document);

                Double tf_id = total_tf_ids.get(document);
                double old_tf_id = (tf_id != null) ? tf_id : 0.;
                
                total_tf_ids.put(document, old_tf_id + tf * idf);
            }
        }

        System.out.println(entriesSortedByValues(total_tf_ids));
   }
}
