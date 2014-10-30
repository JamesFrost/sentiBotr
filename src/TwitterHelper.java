import twitter4j.*;

/**
 * Handles all interactions with Twitter.
 *
 * Created by James Frost on 30/10/2014.
 */
public class TwitterHelper implements Constants {

    /**
     * Gets tweets for a specific trend.
     *
     * @param query The search term
     * @return Tweets found using the search term
     */
    public QueryResult getTweets(Query query) {
        Twitter twitter = new TwitterFactory().getInstance();
        query.count(NUMBER_OF_TWEETS);
        try {
            return twitter.search(query);
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the trends for a specific location.
     *
     * @return Trends
     */
    public Trends getTrends() {
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            Trends trends = twitter.getPlaceTrends(LOCATION_WOEID);
            System.out.println("Showing available trends");

            for (int i = 0; i < trends.getTrends().length; i++) {
                System.out.println(trends.getTrends()[i].getName());
            }

            return trends;
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get trends: " + te.getMessage());
            return null;
        }
    }

    /**
     * Sends a tweet.
     *
     * @param text The text to tweet
     * @throws TwitterException
     */
    public void tweet(String text) throws TwitterException {
        Twitter twitter = new TwitterFactory().getInstance();
        StatusUpdate stat = new StatusUpdate(text);
        twitter.updateStatus(stat);
    }
}
