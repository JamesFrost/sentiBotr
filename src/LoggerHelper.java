import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

/**
 * Handles all logging activity.
 * <p/>
 * Created by James on 30/10/2014.
 */
public class LoggerHelper implements Constants {

    private java.util.logging.Logger logger;
    private FileHandler fh;

    public LoggerHelper() {
        logger = java.util.logging.Logger.getLogger("MyLog");
    }

    /**
     * Writes log file when tweets sent.
     *
     * @param numberOfTweets Number of tweets sent
     * @param tweetedTrends  Trends tweeted about
     */
    public void log(int numberOfTweets, String[] tweetedTrends) {

        String trends = "";

        for (String tweetedTrend : tweetedTrends) {
            if (tweetedTrend.equals("")) break;
            else trends += tweetedTrend + ", ";
        }

        if (!(trends.equals(""))) trends = trends.substring(0, trends.length() - 2) + ".";

        try {
            fh = new FileHandler(PATH_TO_LOG_FILE, LOG_FILE_SIZE_LIMIT, 1, true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            if (numberOfTweets == 0)
                logger.warning("No tweets sent.");
            else
                logger.info("Tweets sent: " + numberOfTweets + ". Trends tweeted about: " + trends);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
