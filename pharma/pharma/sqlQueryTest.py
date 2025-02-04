import sqlite3, json
from flask import jsonify
from constants import DBNAME

def runQuery():
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()
    result = jsonify([{"result": "Done"}])

    
    #updateValue ="National Chemist"
    #updateValue =7200
    productid = "08382583-372c-4d0b-929d-c10a8b1234d5"
    expdate = "2027-12-30"
    #cursor.execute("DELETE FROM ProductCategory WHERE id=5")
    #cursor.execute("UPDATE Users SET name= ? WHERE id = ?", (updateValue, 6))
    #cursor.execute("UPDATE Products SET product_stock= ? WHERE id = ?", (updateValue, 4))
    #cursor.execute("DROP TABLE UserStockProducts;")
    #cursor.execute("UPDATE Orders SET is_approved = 1 WHERE order_id = ?", (orderid,))
    #cursor.execute("SELECT Orders.order_id, Orders.user_id, Orders.product_id, Orders.product_price, Orders.order_quantity, Products.product_expiry_date FROM Orders LEFT JOIN Users ON Orders.user_id = Users.user_id LEFT JOIN Products ON Orders.product_id = Products.product_id WHERE order_id = ?", (orderid,))
    #cursor.execute("ALTER TABLE Orders ADD product_expiry_date DATE;")
    #cursor.execute("UPDATE Orders SET product_expiry_date = ? WHERE product_id = ?", (expdate, productid,))
    #conn.commit()


    #cursor.execute("SELECT Orders.id, Orders.order_id, Orders.user_id, Users.name, Orders.product_id, Products.product_name, Products.product_category, ProductCategory.product_category_name, Orders.product_price, Orders.order_quantity, Orders.order_date, Orders.is_approved, Orders.order_status_updated_date FROM Orders LEFT JOIN Users ON Orders.user_id = Users.user_id LEFT JOIN Products ON Orders.product_id = Products.product_id LEFT JOIN ProductCategory ON Products.product_category = ProductCategory.product_category_id;")
    #cursor.execute("SELECT UserStockProducts.id, UserStockProducts.user_id, Users.name, UserStockProducts.order_id, UserStockProducts.product_id, Products.product_name, Products.product_category, UserStockProducts.product_price, UserStockProducts.user_stock, UserStockProducts.user_stock_product_expiry_date FROM UserStockProducts LEFT JOIN Users ON UserStockProducts.user_id = Users.user_id LEFT JOIN Products ON UserStockProducts.product_id = Products.product_id;")
    #result = cursor.fetchall()


    conn.close()    
    return result

