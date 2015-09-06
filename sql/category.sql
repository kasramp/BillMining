CREATE TABLE IF NOT EXISTS category (
pkid INTEGER PRIMARY KEY AUTOINCREMENT,
category_code TEXT NOT NULL,
category_name TEXT NOT NULL,
category_description TEXT NOT NULL
);