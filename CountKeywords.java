import java.util.*;
import java.io.*;

public class CountKeywords {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java CountKeywords <MyDate.java>");
            System.exit(1);
        }

        String filename = args[0];
        File file = new File(filename);

        if (file.exists()) {
            System.out.println("The number of keywords in " + filename + " is " + countKeywords(file));
        } else {
            System.out.println("File " + filename + " does not exist");
        }
    }

    public static int countKeywords(File file) throws Exception {
        // Array of all Java keywords + true, false and null
        String[] keywordString = { /* ... same as before ... */ };
        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywordString));
        int count = 0;

        try (Scanner input = new Scanner(file)) {
            boolean inComment = false;
            boolean inString = false;

            while (input.hasNextLine()) {
                String line = input.nextLine().trim();

                // Check for start and end of multiline comments
                if (line.contains("/*")) {
                    inComment = true;
                }
                if (line.contains("*/")) {
                    inComment = false;
                    continue;
                }

                // Skip the line if it's a comment
                if (inComment || line.startsWith("//")) {
                    continue;
                }

                // Process each word in the line
                Scanner lineScanner = new Scanner(line);
                while (lineScanner.hasNext()) {
                    String word = lineScanner.next();

                    // Check for string literals
                    if (word.startsWith("\"") && word.endsWith("\"") && !word.startsWith("\\\"")
                            && !word.endsWith("\\\"")) {
                        inString = !inString;
                        continue;
                    }

                    if (!inString && keywordSet.contains(word)) {
                        count++;
                    }
                }
                lineScanner.close();
            }
        }

        return count;
    }
}
