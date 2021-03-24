package com.github;

public class kuir {
    public static void main(String[] args) {

        String arg = args[0];
        String path = args[1];
        if (arg.equals("-c")) {
            if (args[1] != null) {
                makeCollection mkCollection = new makeCollection();
                mkCollection.makeCollection(path);
            }
        } else if (arg.equals("-k")) {
            if (args[1] != null) {
                makeKeyword mkKeyword = new makeKeyword();
                mkKeyword.makeKeyword(path);
            }
        }
    }
}
