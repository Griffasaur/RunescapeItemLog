package org.example.Api;

import org.example.Dao.JdbcItemDao;
import org.example.Model.Item;
import org.example.Model.ItemPrice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class ApiClient {

    private final RestTemplate restTemplate;
    private final JdbcItemDao jdbcItemDao;

    private final String apiBaseUrl = "https://prices.runescape.wiki/api/v1/osrs";

    public ApiClient(RestTemplate restTemplate, JdbcItemDao jdbcItemDao) {
        this.restTemplate = restTemplate;
        this.jdbcItemDao = jdbcItemDao;
    }

    public List<Item> getAllItems() {
        String url = apiBaseUrl + "/mapping";
        ResponseEntity<Item[]> response = restTemplate.getForEntity(url, Item[].class);

        List<Item> items = new ArrayList<>();
        for (Item item : response.getBody()) {
            if (item.getItemName() != null && item.getItemId() > 0) {
                items.add(item);
            } else {

                System.out.println("Skipping invalid item: " + item.getItemName());
            }
        }

        return items;
    }

    public ItemPrice getLatestPricesById(int id) {
        String url = apiBaseUrl + "/latest?id={id}";
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class, id);

        Map<String, Map<String, Map<String, Integer>>> responseBody = response.getBody();

        if (responseBody == null || !responseBody.containsKey("data")) {
            throw new IllegalStateException("Invalid response from the API.");
        }

        Map<String, Map<String, Integer>> data = responseBody.get("data");
        Map<String, Integer> itemData = data.get(String.valueOf(id));

        if (itemData == null) {
            throw new IllegalArgumentException("Item data found for item with ID: " + id);
        }

        Item item = jdbcItemDao.getItemById(id);

        if(item == null) {
            throw new IllegalArgumentException("Item data not found for item with ID: " + id);
        }

        ItemPrice itemPrice = new ItemPrice();
        itemPrice.setItem(item);
        itemPrice.setLowPrice(itemData.getOrDefault("low", -1));
        itemPrice.setHighPrice(itemData.getOrDefault("high", -1));
        itemPrice.setLowVolume(-1);
        itemPrice.setHighVolume(-1);

        return itemPrice;
    }

    public ItemPrice get24HourPricesById(int id) {
        String url = apiBaseUrl + "/timeseries?timestep=24h&id={id}";
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class, id);

        Map<String, Object> responseBody = response.getBody();

        if (responseBody == null || !responseBody.containsKey("data")) {
            throw new IllegalStateException("Invalid response from the API: 'data' key not found");
        }

        // Extract data as a List
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) responseBody.get("data");

        if (dataList == null || dataList.isEmpty()) {
            throw new IllegalArgumentException("No price data available for item with ID: " + id);
        }

        // Use the first data point (or iterate if needed)
        Map<String, Object> latestData = dataList.get(0);

        // Fetch item from the database
        Item item = jdbcItemDao.getItemById(id);
        if (item == null) {
            throw new IllegalArgumentException("Item data not found for item with ID: " + id);
        }

        ItemPrice itemPrice = new ItemPrice();
        itemPrice.setItem(item);
        itemPrice.setLowPrice((Integer) latestData.getOrDefault("avgLowPrice", -1));
        itemPrice.setHighPrice((Integer) latestData.getOrDefault("avgHighPrice", -1));
        itemPrice.setLowVolume((Integer) latestData.getOrDefault("lowPriceVolume", -1));
        itemPrice.setHighVolume((Integer) latestData.getOrDefault("highPriceVolume", -1));

        return itemPrice;
    }
}
