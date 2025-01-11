# Runescape Item Log
Console application which accesses the RuneLite's API for Old School Runescape to retrieve item information and prices.

## How to Use
Start the program by running the Application Class. 

The program will import all of the item information into a database to allow for easier access for item information. 

You can then select either Start or Exit. Once you select Start, you have the options to look up item information or price information.

## Searching Item Information
When searching for item information, you have the options to search by name, item id or buy limit.

All searching return the items name, buy limit and item id. 

If searching by name or buy limit, it will return a list of items. For example, "rune platebody" will also return "Rune platebody (h4)" and "Rune platebody (g)." "rune" will return everything that contains "rune" in it.

So for example, if we search for "rune platebody" here is the output:

```
What is the name of the item you are searching for?: rune platebody
------------------------------------------------------------
Item Details
------------------------------------------------------------
Item: Rune platebody
Buy Limit: 70
Item ID: 1127
------------------------------
------------------------------
Item: Rune platebody (g)
Buy Limit: 8
Item ID: 2615
------------------------------
------------------------------
Item: Rune platebody (t)
Buy Limit: 8
Item ID: 2623
------------------------------
------------------------------
Item: Rune platebody (h1)
Buy Limit: 8
Item ID: 23209
------------------------------
------------------------------
Item: Rune platebody (h2)
Buy Limit: 8
Item ID: 23212
------------------------------
------------------------------
Item: Rune platebody (h3)
Buy Limit: 8
Item ID: 23215
------------------------------
------------------------------
Item: Rune platebody (h4)
Buy Limit: 8
Item ID: 23218
------------------------------
------------------------------
Item: Rune platebody (h5)
Buy Limit: 8
Item ID: 23221
------------------------------
------------------------------
```

## Searching Price Information

When searching for Price Information, you will first be prompted for the item you are searching for. You will then be returned a list of items that match your search and you must select one.

For example, searching for "rune platebody" returns a prompt that looks like:

```
------------------------------
Please select an option: 2
What is the name of the item you would like to search for?: rune platebody
Search Results:
1. Rune platebody
2. Rune platebody (g)
3. Rune platebody (t)
4. Rune platebody (h1)
5. Rune platebody (h2)
6. Rune platebody (h3)
7. Rune platebody (h4)
8. Rune platebody (h5)
Enter the number of the item you wish to select: 2
------------------------------
```

You will then enter the number from the list and it will then ask if you would like the most recent price, or 24 hour price with volume.

If you select Most Recent Price, you will be returned the latest price for that item. So when we select "2" for "Rune platebody (g)" my search returned:

```
------------------------------
Item: Rune platebody (g)
High Price: 87,888
Low Price: 82,000
------------------------------
```

Searching for 24 Hour Price and Volume returns the highest & lowest price and volume information. So for this example it returns:

```
------------------------------
Item: Rune platebody (g)
High Price: 98,398
Low Price: 92,437
High Volume: 33
Low Volume: 91
------------------------------
```
