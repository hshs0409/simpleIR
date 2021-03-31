package com.github;

public class kuir {
    public static void main(String[] args) {

        String arg = args[0];
        String path = args[1];
        switch (arg) {
            case "-c":
                if (args[1] != null) {
                    makeCollection mkCollection = new makeCollection();
                    mkCollection.makeCollection(path);
                }
                break;
            case "-k":
                if (args[1] != null) {
                    makeKeyword mkKeyword = new makeKeyword();
                    mkKeyword.makeKeyword(path);
                }
                break;
            case "-i":
                if (args[1] != null) {
                    indexer index = new indexer();
                    index.makeIndexer(path);
                }
                break;
        }
    }
}
