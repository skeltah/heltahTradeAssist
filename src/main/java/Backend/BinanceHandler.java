
package Backend;


import Telegram.BotSettings;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;

import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;

public class BinanceHandler {


        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(BotSettings.currentUser.getApiKey(), BotSettings.currentUser.getSecretKey());
        BinanceApiRestClient client = factory.newRestClient();

    public int getPrice(String pair)
    {
        return (int) Double.parseDouble(client.getPrice(pair).getPrice());
    }
    public String getReport(String pair)
    {

        DecimalFormat percentage = new DecimalFormat("#.##");

        DecimalFormat smallNumber = new DecimalFormat("#.####");

        DecimalFormat bigNumber = new DecimalFormat("#.##");


        Double Dhigh = Double.parseDouble(client.get24HrPriceStatistics(pair).getHighPrice());
        Double Dlow = Double.parseDouble(client.get24HrPriceStatistics(pair).getLowPrice());
        Double DpriceChange = Double.parseDouble(client.get24HrPriceStatistics(pair).getPriceChange());
        Double DavgPrice = Double.parseDouble(client.get24HrPriceStatistics(pair).getWeightedAvgPrice());

        String volume = "" + (int) Double.parseDouble(client.get24HrPriceStatistics(pair).getVolume());
        String priceChangePercent = percentage.format(Double.parseDouble(client.get24HrPriceStatistics(pair).getPriceChangePercent()));
        String pairCutted = "";
        String[] pairTemp = pair.split("");
        for(int i = 0; i < 3; i++)
        {
            pairCutted = pairCutted + pairTemp[i];
        }


        String high; String low; String priceChange; String avgPrice;


        if(DavgPrice<1) {
            high = String.format("%.4f", Dhigh);
            low = String.format("%.4f", Dlow);
            priceChange = String.format("%.4f", DpriceChange);
            avgPrice = String.format("%.4f", DavgPrice);
        }
        else
        {
            high = String.format("%.2f", Dhigh);
            low = String.format("%.2f", Dlow);
            priceChange = String.format("%.2f", DpriceChange);
            avgPrice = String.format("%.2f", DavgPrice);
        }


        return "Отчет \uD83D\uDCDD за 24 часа по паре " + pair + "\n\n Средняя цена: " + avgPrice + "$" + "\n Промежуток цен " + low + "/" + high + " $" + "\n Изменение 24hrs: " + priceChange + " ("+priceChangePercent+"%)" + "\n" + " Обьем торгов: " +  volume +" "+ pairCutted;
    }
    public boolean CheckConnection(String ApiKey, String SecretKey)
    {

        boolean providedData = true;

        try {

            BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(ApiKey,SecretKey);
            BinanceApiRestClient localClient = factory.newRestClient();

            localClient.getAccount().getBalances();
        }
        catch (Exception E)
        {
            providedData = false;
        }

        return providedData;

    }
    public boolean isThatPairExists(String pair)
    {

        boolean result = true;

        try
        {
            client.getPrice(pair);
        }
        catch(Exception PairNotExist)
        {
            result = false;
        }

        return result;

    }
}
