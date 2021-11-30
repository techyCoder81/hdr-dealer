package response;


public interface ResponseConsumer {
    /**
     * return a response object
     * @param response
     */
    void receiveResponse(CommandResponse response);
}
