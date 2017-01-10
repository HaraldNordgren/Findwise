import java.util.*;


public class Findwise {
    public static void main(String[] args) {
        
        Map<String, String> documents = new HashMap<String, String>();
        documents.put("document 1", "the brown fox jumped over the brown dog");
        documents.put("document 2", "the lazy brown dog sat in the corner");
        documents.put("document 3", "the red fox bit the lazy dog");

        if (args.length < 1) {
            System.err.println("Please provide a search query!");
            System.exit(1);
        }

        String query = args[0];

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

        TreeMap<String, Integer> document_occurences = reversed_index.get(query);
        Set<String> result;

        if (document_occurences == null) {
            System.out.println("<No results>");
        } else {
            result = document_occurences.keySet();
            System.out.println(result);
        }
   }
}
