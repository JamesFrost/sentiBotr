/**
 * Holds all constant variables.
 *
 * Created by James Frost on 30/10/2014.
 */
public interface Constants {
    public static final String BASE_API_URL = "http://www.sentiment140.com/api/bulkClassifyJson"; //sentiment140 api
    public static final String APP_ID = ""; //your app id here
    public static final int NUMBER_OF_TWEETS = 50; //200 maximum (Twitter API limit)
    public static final int LOCATION_WOEID = 23424975; //woeid of location to get trends for - currently UK
    public static final int LOG_FILE_SIZE_LIMIT = 1000000; // 1 Mb size limit on log file - when reached will flush and start again
    public static final String PATH_TO_LOG_FILE = "/storage/twitterBot/log.txt"; //path to the log file
}
