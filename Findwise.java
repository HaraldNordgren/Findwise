import java.util.*;
import java.lang.Math.*;


public class Findwise {

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
        
        Map<String, String> documents = new HashMap<>();
        documents.put("document 1", "the brown fox jumped over the brown dog");
        documents.put("document 2", "the lazy brown dog sat in the corner");
        documents.put("document 3", "the red fox bit the lazy dog");
        //documents.put("document 4", "brown brown brown");

        if (args.length == 0) {
            System.err.println("Please provide a search query!");
            System.exit(1);
        }

        HashMap<String, TreeMap<String, Integer>> reversed_index = new HashMap<>();

        for (String document_key : documents.keySet()) {

            String document_text = documents.get(document_key);
            StringTokenizer st = new StringTokenizer(document_text);
            
            while (st.hasMoreTokens()) {
                String word = st.nextToken();
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
        }

        double total_documents = documents.size();

        TreeMap<String, Double> total_tf_ids = new TreeMap<>();
        /*for (String key : documents.keySet()) {
            total_tf_ids.put(key, 0.);
        }*/

        for (String query_string : args) {
            //System.out.println(query_string);

            TreeMap<String, Integer> document_occurences = reversed_index.get(query_string);
            if (document_occurences == null) {
                continue;
            }
            
            int documents_containing_word = document_occurences.size();
            double idf = java.lang.Math.log(total_documents / documents_containing_word);

            for (String document : document_occurences.keySet()) {
                int word_occurences = document_occurences.get(document);
                Double tf_id = total_tf_ids.get(document);
                double current_tf_id = (tf_id != null) ? tf_id : 0.;
                total_tf_ids.put(document, current_tf_id + word_occurences * idf);
            }
        }


        System.out.println(entriesSortedByValues(total_tf_ids));
   }
}
