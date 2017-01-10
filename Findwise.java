import java.util.*;


public class Findwise {
    public static void main(String[] args) {
        String[] documents = {
            "the brown fox jumped over the brown dog",
            "the lazy brown dog sat in the corner",
            "the red fox bit the lazy dog",
        };
        //System.out.println(Arrays.toString(documents));

        if (args.length < 1) {
            System.err.println("Please provide a search query!");
            System.exit(1);
        }

        String query = args[0];
        //System.out.println(query);

        /*StringTokenizer st = new StringTokenizer(documents[0]);
        while (st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }*/

        HashMap<String, Integer> map = new HashMap<>();

        for (String document : documents) {
            //System.out.println(document);

            StringTokenizer st = new StringTokenizer(document);
            while (st.hasMoreTokens()) {
                //System.out.println(st.nextToken());
                String word = st.nextToken();
                Integer freq = map.get(word);
                map.put(word, (freq == null) ? 1 : freq + 1);
            }
        }

        for (String name: map.keySet()){
            String key = name.toString();
            String value = map.get(name).toString();  
            System.out.println(key + " " + value);
        }
   }
}
