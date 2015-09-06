CREATE TABLE IF NOT EXISTS outlet (
pkid INTEGER PRIMARY KEY AUTOINCREMENT,
company_pkid INTEGER NOT NULL,
outlet_name TEXT NOT NULL,
outlet_code TEXT NOT NULL,
outlet_address TEXT NOT NULL,
date_created TEXT NOT NULL
);




