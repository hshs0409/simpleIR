package com.github;

public class kuir {
    public static void main(String[] args) throws Exception {

        String arg = args[0];
        String path = args[1];
        String query = null;
        if(args.length >=3){
            query= args[3];
        }
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
            case "-s":
                if (args[1] != null) {
                    searcher search = new searcher();
                    search.calcSim(path,query);
                }
                break;
            case "-m":
                if (args[1] != null) {
                    movie movie = new movie();
                    movie.main();
                }
                break;
        }
    }
}
