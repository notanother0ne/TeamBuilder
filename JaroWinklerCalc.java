class JaroWinklerCalc {
    // Function to calculate the Jaro Similarity of two strings 
    static double jaroDistance(String s1, String s2) { 
        // If the strings are equal 
        if (s1 == s2) 
            return 1.0; 
     
        // Length of two strings 
        int len1 = s1.length(), 
            len2 = s2.length(); 
        if (len1 == 0 || len2 == 0) 
            return 0.0; 
     
        // Maximum distance up to which matching is allowed 
        int maxDist = (int)Math.floor(Math.max(len1, len2) / 2) - 1; 
        // Count of matches 
        int match = 0; 
        // Hash for matches 
        int hashS1[] = new int [s1.length()]; 
        int hashS2[] = new int[s2.length()]; 
     
        // Traverse through the first string 
        for (int i = 0; i < len1; i++) { 
            // Check if there is any matches 
            for (int j = Math.max(0, i - maxDist); 
                j < Math.min(len2, i + maxDist + 1); j++) 
                // If there is a match 
                if (s1.charAt(i) == s2.charAt(j) && hashS2[j] == 0) { 
                    hashS1[i] = 1; 
                    hashS2[j] = 1; 
                    match++; 
                    break; 
                } 
        } 
        // If there is no match 
        if (match == 0) 
            return 0.0; 

        // Number of transpositions 
        double t = 0; 
        int point = 0; 
        // Count number of occurrences where two characters match but there is a third matched character 
        // in between the indices 
        for (int i = 0; i < len1; i++) 
            if (hashS1[i] == 1) { 
                // Find the next matched character in second string 
                while (hashS2[point] == 0) 
                    point++; 
                if (s1.charAt(i) != s2.charAt(point++)) 
                    t++; 
            } 
        t /= 2; 
        // Return the Jaro Similarity 
        return (((double)match) / ((double)len1) 
                + ((double)match) / ((double)len2) 
                + ((double)match - t) / ((double)match)) 
            / 3.0; 
    } 
     
    // Jaro Winkler Similarity 
    static double jaroWinkler(String s1, String s2) { 
        double jaroDist = jaroDistance(s1, s2); 
        // If the jaro Similarity is above a threshold 
        if (jaroDist > 0.7) { 
            // Find the length of common prefix 
            int prefix = 0; 
            for (int i = 0; i < Math.min(s1.length(), s2.length()); i++) { 
                // If the characters match 
                if (s1.charAt(i) == s2.charAt(i)) 
                    prefix++; 
                else
                    break; 
            } 
            // Maximum of 4 characters are allowed in prefix 
            prefix = Math.min(4, prefix); 
            // Calculate jaro winkler Similarity 
            jaroDist += 0.1 * prefix * (1 - jaroDist); 
        } 
        return jaroDist; 
    }
}
