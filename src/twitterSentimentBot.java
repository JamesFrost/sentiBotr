import twitter4j.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Twitter bot that analyses the sentiment of UK trends.
 * Uses the sentiment140 API.
 * <p/>
 * Created by James Frost on 25/10/2014.
 */
public class twitterSentimentBot implements Constants {

    //number of tweets with sentiment for a particular trend.
    private int negative;
    private int neutral;
    private int positive;

    public twitterSentimentBot() {

        TwitterHelper twitterHelper = new TwitterHelper();
        ApiHelper apiHelper = new ApiHelper();
        LoggerHelper logger = new LoggerHelper();

        int tweetCount = 0;
        String[] tweetedTrends = new String[10];
        Arrays.fill(tweetedTrends, "");
        Trends trends = twitterHelper.getTrends();

        for (int i = 0; i < trends.getTrends().length; i++) {
            QueryResult query = twitterHelper.getTweets(new Query(trends.getTrends()[i].getName()));

            List<Status> statuses = query.getTweets();

            System.out.println("Sending API request...");
            try {
                String apiReturn = apiHelper.makeApiRequest(statuses, query.getQuery());
                String generatedTweet = generateTweet(apiReturn, query.getQuery());

                //only tweet if API returned a result
                if (!(negative == 0 && neutral == 0 && positive == 0)) {
                    twitterHelper.tweet(generatedTweet);
                    tweetedTrends[tweetCount] = query.getQuery();
                    tweetCount++;
                } else System.out.println("No response from API.");

                if (i != trends.getTrends().length - 1)
                    Thread.sleep(10000);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
        logger.log(tweetCount, tweetedTrends);
    }

    /**
     * Processes JSON returned from API into a tweet.
     *
     * @param apiReturn The JSON returned by the API
     * @param query     The query (trend) that is being analysed
     * @return The generated tweet
     */
    private String generateTweet(String apiReturn, String query) {

        String tweet = "Currently, tweets ";
        if (query.startsWith("#")) tweet += "using ";
        else tweet += "about ";

        tweet += query + " are ";

        String[] split = apiReturn.split("\"polarity\":");

        negative = 0;
        neutral = 0;
        positive = 0;

        for (int i = 1; i < split.length; i++) {
            if (split[i].startsWith("0")) negative++;
            else if (split[i].startsWith("2")) neutral++;
            else if (split[i].startsWith("4")) positive++;
        }

        tweet += (int) (((float) positive * 100.0f) / NUMBER_OF_TWEETS) + "% positive, " + (int) (((float) neutral * 100.0f) / NUMBER_OF_TWEETS) + "% neutral, and " + (int) (((float) negative * 100.0f) / NUMBER_OF_TWEETS) + "% negative.";
        return tweet;
    }

    public static void main(String[] args) throws TwitterException {
        new twitterSentimentBot();
    }
}
