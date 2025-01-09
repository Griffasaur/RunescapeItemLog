BEGIN TRANSACTION;

DROP TABLE IF EXISTS item_prices;
DROP TABLE IF EXISTS items;

CREATE TABLE items (
	item_id INT PRIMARY KEY,
	item_name varchar NOT NULL,
	buy_limit int NOT NULL
);

CREATE TABLE item_prices (
	search_id SERIAL PRIMARY KEY,
	item_id INT NOT NULL,
	high_price INT,
	low_price INT,
	high_volume INT,
	low_volume INT,
	FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE
);

CREATE INDEX idx_item_prices_item_id ON item_prices(item_id);

COMMIT TRANSACTION;