import sqlite3
from flask import jsonify
from constants import DBNAME

def createTable():
    try:
      conn = sqlite3.connect(DBNAME)
      cursor = conn.cursor()

      cursor.execute('''
                  CREATE TABLE IF NOT EXISTS Users(
                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                     user_id VARCHAR(255),
                     password VARCHAR(255),
                     level INT,
                     date_of_account_creation DATE,
                     is_approved BOOLEAN,
                     is_blocked BOOLEAN,
                     name VARCHAR(255),
                     email VARCHAR(255),
                     phone_number VARCHAR(255),
                     address VARCHAR(255),
                     pin_code VARCHAR(6)
                     );
                     ''')
      

      
      cursor.execute('''
                  CREATE TABLE IF NOT EXISTS Products(
                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                     product_id VARCHAR(255),
                     product_name VARCHAR(255),
                     product_category INT,
                     product_price INT,
                     product_stock INT,
                     product_expiry_date DATE
                     );
                     ''')
      


      cursor.execute('''
                  CREATE TABLE IF NOT EXISTS ProductCategory(
                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                     product_category_id VARCHAR(255),
                     product_category_name VARCHAR(255),
                     product_category_description VARCHAR(255)
                     );
                     ''')



      cursor.execute('''
                  CREATE TABLE IF NOT EXISTS Orders(
                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                     user_id VARCHAR(255),
                     order_id VARCHAR(255),
                     product_id VARCHAR(255),
                     product_price INT,
                     order_quantity INT,
                     order_date DATE,
                     is_approved BOOLEAN,
                     order_status_updated_date DATE,
                     product_expiry_date DATE
                     );
                     ''')
      


      cursor.execute('''
                  CREATE TABLE IF NOT EXISTS UserStockProducts(
                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                     user_id VARCHAR(255),
                     order_id VARCHAR(255),
                     product_id VARCHAR(255),
                     product_price INT,
                     user_stock INT,
                     user_stock_product_expiry_date DATE
                     );
                     ''')

      conn.commit()
      conn.close()
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])
