import sqlite3
import uuid
import json
from datetime import date
from flask import jsonify
from constants import DBNAME

def createUser(name, password, phonenumber, email, address, pincode):
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()

    user_id = str(uuid.uuid4())
    date_of_account_creation = date.today()

    cursor.execute("""
                    INSERT INTO Users (user_id, password, level, date_of_account_creation, 
                    is_approved, is_blocked, name, email, phone_number, address, pin_code)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """, (user_id, password, 1, date_of_account_creation, 0, 0, name, email, phonenumber, address, pincode)
                    )
    
    conn.commit()
    conn.close()
    
    temp_userid = {
            "user_id": user_id,
            "message": "Added User Successfully"
            }
    user_id_Json = []
    user_id_Json.append(temp_userid)

    return(json.dumps(user_id_Json))



def createProduct(productname, productcategory, productprice, productstock, productexpirydate):
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()

    product_id = str(uuid.uuid4())

    cursor.execute("""
                    INSERT INTO Products (product_id, product_name, product_category, product_price, 
                    product_stock, product_expiry_date)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """, (product_id, productname, productcategory, productprice, productstock, productexpirydate)
                    )
    
    conn.commit()
    conn.close()
    
    temp_product_id = {
            "product_id": product_id,
            "message": "Added Product Successfully"
            }
    product_id_Json = []
    product_id_Json.append(temp_product_id)

    return(json.dumps(product_id_Json))



def createProductCategory(productcategoryid, productcategoryname, productcategorydescription):
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()

    cursor.execute("""
                    INSERT INTO ProductCategory (product_category_id, product_category_name, product_category_description)
                    VALUES (?, ?, ?)
                    """, (productcategoryid, productcategoryname, productcategorydescription)
                    )
    
    conn.commit()
    conn.close()
    
    temp_data = {
            "message": "Added Product Category Successfully"
            }
    data_Json = []
    data_Json.append(temp_data)

    return(json.dumps(temp_data))



def createOrder(userid, productid, productprice, orderquantity):
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()

    orderid = str(uuid.uuid4())
    orderdate = date.today()
    orderstatusupdateddate = date.today()

    cursor.execute("UPDATE Products SET product_stock = product_stock - ? WHERE product_id = ?", (orderquantity, productid))

    cursor.execute("""
                    INSERT INTO Orders (user_id, order_id, product_id, product_price, order_quantity, 
                    order_date, is_approved, order_status_updated_date)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                    """, (userid, orderid, productid, productprice, orderquantity, orderdate, 0, orderstatusupdateddate)
                    )
    
    conn.commit()
    conn.close()
    
    temp_orderid = {
            "order_id": orderid,
            "message": "Placed Order Successfully"
            }
    orderid_Json = []
    orderid_Json.append(temp_orderid)

    return(json.dumps(orderid_Json))



def createOrderApprovedUserStock(userid, orderid, productid, productprice, userstock, userstockproductexpirydate):
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()

    cursor.execute("UPDATE Orders SET is_approved = 1 WHERE order_id = ?", (orderid,))

    cursor.execute("""
                    INSERT INTO UserStockProducts (user_id, order_id, product_id, product_price, user_stock, 
                    user_stock_product_expiry_date)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """, (userid, orderid, productid, productprice, userstock, userstockproductexpirydate)
                    )
    
    conn.commit()
    conn.close()
    
    temp_data = {
            "message": "Order Approved and Created User Stock"
            }
    data_Json = []
    data_Json.append(temp_data)

    return(json.dumps(data_Json))



def approveOrder(orderid):
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()

    cursor.execute("SELECT Orders.order_id, Orders.user_id, Orders.product_id, Orders.product_price, Orders.order_quantity, Orders.product_expiry_date FROM Orders LEFT JOIN Users ON Orders.user_id = Users.user_id LEFT JOIN Products ON Orders.product_id = Products.product_id WHERE Orders.order_id = ? AND Orders.is_approved = 0;", (orderid,))

    order = cursor.fetchone()

    if order:
        userid = order[1]
        productid = order[2]
        productprice = order[3]
        userstock = order[4]
        userstockproductexpirydate = order[5]
        orderstatusupdateddate = date.today()

        cursor.execute("UPDATE Orders SET is_approved = 1, order_status_updated_date = ? WHERE order_id = ?", (orderstatusupdateddate, orderid))

        cursor.execute("""
                    INSERT INTO UserStockProducts (user_id, order_id, product_id, product_price, user_stock, 
                    user_stock_product_expiry_date)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """, (userid, orderid, productid, productprice, userstock, userstockproductexpirydate)
                    )
    
        conn.commit()
        conn.close()

        temp_data = {
        "message": "Order Approved and Created User Stock"
        }
        data_Json = []
        data_Json.append(temp_data)

        return(json.dumps(data_Json))
    else:
        conn.commit()
        conn.close()
        return jsonify([{"status": 400, "message": "No records found"}])

