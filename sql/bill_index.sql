CREATE TABLE IF NOT EXISTS bill_index (
pkid INTEGER PRIMARY KEY AUTOINCREMENT,
company_pkid INTEGER NOT NULL,
outlet_pkid INTEGER NOT NULL,
bill_amount DECIMAL NOT NULL,
gst_amount DECIMAL NOT NULL,
total_amount DECIMAL NOT NULL,
date_created TEXT NOT NULL
);