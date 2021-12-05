package command.handlers;


public class ArcNameHandler extends SearchHandler {

    // all we have to do is override the files we want to search,
    // and the base class will handle the rest.
    @Override
    protected String[] getSearchableFiles() {
        return new String[] {
            "Hashes_FullPath.txt"
        };
    }

}
