import twitter4j.Status;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Handles all API interaction.
 *
 * Created by James Frost on 30/10/2014.
 */
public class ApiHelper implements Constants {

    /**
     * Builds a JSON that can be sent to the API.
     *
     * @param statuses The tweets to put into a JSON
     * @param query    The query (trend) that returned these tweets
     * @return JSON with tweet data
     */
    private String generateJsonData(List<Status> statuses, String query) {
        String data = "{\"data\": [";

        for (Status status : statuses) {
            data += "{\"text\": \"" + status.getText() + "\", \"query\": \"" + query + "\"}, \n";
        }

        data += "]}";
        return data;
    }

    /**
     * Sends a HTTP POST request to the sentiment140 API and gets the result.
     *
     * @return Unprocessed string from the API
     * @throws java.io.IOException
     */
    public String makeApiRequest(List<Status> statuses, String query) throws IOException {
        String apiReturn;
        String jsonData = generateJsonData(statuses, query);
        byte[] queryData = jsonData.getBytes("UTF-8");
        HttpURLConnection con = (HttpURLConnection) new URL(BASE_API_URL + APP_ID).openConnection();
        con.setConnectTimeout(60000);
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(queryData);
        os.close();
        StringBuilder out = new StringBuilder();
        InputStream instr = con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(instr));
        String lin;
        while ((lin = br.readLine()) != null) {
            System.out.println("[Debug]" + lin);
            out.append(lin);
        }
        apiReturn = out.toString();
        return apiReturn;
    }
}
