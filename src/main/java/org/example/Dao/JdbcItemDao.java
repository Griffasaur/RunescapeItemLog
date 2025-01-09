package org.example.Dao;

import org.example.Model.Item;
import org.example.exception.DaoException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcItemDao implements ItemDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcItemDao (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Item item = mapRowToItem(results);
                items.add(item);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database.", e);
        }
        return items;
    }

    @Override
    public void insertItems(List<Item> items) {
        String sql = "INSERT INTO items (item_id, item_name, buy_limit) VALUES (?, ?, ?) ON CONFLICT (item_id) DO NOTHING;";

        jdbcTemplate.batchUpdate(sql, items, items.size(), (ps, item) -> {
            ps.setInt(1, item.getItemId());
            ps.setString(2, item.getItemName());
            ps.setInt(3, item.getBuyLimit());
        });
    }

    @Override
    public Item getItemById(int itemId) {
        String sql = "SELECT * FROM items WHERE item_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, itemId);
            if (results.next()) {
                return mapRowToItem(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database.", e);
        }
        return null;
    }

    @Override
    public List<Item> getItemsByName(String itemName) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE LOWER(item_name) LIKE LOWER(?) ORDER BY item_id;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, new Object[]{"%" + itemName + "%"});
            while (results.next()) {
                Item item = mapRowToItem(results);
                if (item != null) {
                    items.add(item);
                }
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database.", e);
        }
        return items;
    }

    @Override
    public Item getItemByName (String itemName) {
        String sql = "SELECT * FROM items WHERE item_name = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, itemName);
            if (results.next()) {
                return mapRowToItem(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database.", e);
        }
        return null;
    }

    @Override
    public List<Item> getItemsByBuyLimit(int buyLimit) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE buy_limit = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, buyLimit);
            while (results.next()) {
                Item item = mapRowToItem(results);
                if (item != null) {
                    items.add(item);
                }

            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database.", e);
        }
        return items;
    }

    private Item mapRowToItem(SqlRowSet rowSet) {
        Item item = new Item();
        item.setItemId(rowSet.getInt("item_id"));
        item.setItemName(rowSet.getString("item_name"));
        item.setBuyLimit(rowSet.getInt("buy_limit"));

        return item;
    }
}
