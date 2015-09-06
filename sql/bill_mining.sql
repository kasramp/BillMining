CREATE TABLE IF NOT EXISTS bill_mining (
guid TEXT NOT NULL, 
item_id INTEGER NOT NULL,
price DECIMAL NOT NULL,
outlet_pkid INTEGER NOT NULL,
date_of_suggestion TEXT NOT NULL
);