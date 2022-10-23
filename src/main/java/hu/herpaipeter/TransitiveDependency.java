package hu.herpaipeter;

import java.util.Arrays;
import java.util.List;

public class TransitiveDependency {

    List<String> dependencies;

    public TransitiveDependency(List<String> dependencies) {
        this.dependencies = dependencies;
    }

    public String getDependencies(String start) {
        int resulti = -1;
        String result = "";
        while (resulti < result.length()) {
            String startChar = -1 < resulti ? result.substring(resulti, resulti+1) : start;
            for (int listi = 0; listi < dependencies.size(); listi++) {
                String newDeps = getDependencies(dependencies.get(listi), startChar);
                for (int i = 0; i < newDeps.length(); i++)
                    result += result.contains(newDeps.substring(i, i+1)) ? "" : newDeps.substring(i, i+1);
            }
            resulti++;
        }
        return sortResult(result);
    }

    public String getDependencies(String dependencyRule, String start) {
        String result = "";
        for (int stri = 0; stri < dependencyRule.length(); stri++)
            if (1 < dependencyRule.length() && dependencyRule.substring(stri, stri+1).equals(start))
                result += stri+1 < dependencyRule.length() ? dependencyRule.substring(stri+1, stri+2) : "";
        return result;
    }

    public String sortResult(String result) {
        char tempArray[] = result.toCharArray();
        Arrays.sort(tempArray);
        return new String(tempArray);
    }
}
