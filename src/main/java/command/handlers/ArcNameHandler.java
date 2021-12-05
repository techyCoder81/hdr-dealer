package command.handlers;

import java.util.HashSet;

public class ArcNameHandler extends SearchHandler {

    private static HashSet<String> arcSearchables = null;

    @Override
    protected HashSet<String> getSearchables() {
        return arcSearchables;
    }

    @Override
    protected void setSearchables(HashSet<String> searchables) {
        this.arcSearchables = searchables;
    }


    // all we have to do is override the files we want to search,
    // and the base class will handle the rest.
    @Override
    protected String[] getSearchableFiles() {
        return new String[] {
            "Hashes_FullPath.txt",
            "Hashes.txt"
        };
    }

}
