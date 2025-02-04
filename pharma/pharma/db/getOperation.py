import sqlite3, json
from flask import jsonify
from constants import DBNAME

def getAllUsers():
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM Users")

    users = cursor.fetchall()
    conn.close()

    userJson = []

    if users:
        for user in users:
            tempUser = {
                "id": user[0],
                "user_id": user[1],
                "name": user[7],
                "address": user[10],
                "pin_code": user[11],
                "contact_person_name": "",
                "phone_number": user[9],
                "email": user[8],
                "password": user[2],                
                "is_approved": user[5],
                "is_blocked": user[6],
                "level": user[3],
                "date_of_account_creation": user[4]      
            }

            userJson.append(tempUser)

        return(json.dumps(userJson))
    else:
        return jsonify([{"status": 400, "message": "No records found"}])



def getUserInfo(userId):
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM Users WHERE user_id = ?", (userId,))
    userData = cursor.fetchone()
    conn.close()

    userJson = []

    if userData:
        tempUserData = {
            "id": userData[0],
            "user_id": userData[1],
            "name": userData[7],
            "address": userData[10],
            "pin_code": userData[11],
            "contact_person_name": "",
            "phone_number": userData[9],
            "email": userData[8],
            "password": userData[2],
            "is_approved": userData[5],
            "is_blocked": userData[6],
            "level": userData[3],
            "date_of_account_creation": userData[4]
        }

        userJson.append(tempUserData)
        return json.dumps(userJson)
    else:
        return jsonify([{"status": 400, "message": "No records found"}])



def getAllProducts_Old():
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM Products")

    products = cursor.fetchall()
    conn.close()

    productJson = []

    if products:
        for product in products:
            tempProduct = {
                "id": product[0],
                "product_id": product[1],
                "product_name": product[2],
                "product_category": product[3],
                "product_price": product[4],
                "product_stock": product[5],
                "product_expiry_date": product[6]
            }

            productJson.append(tempProduct)

        return(json.dumps(productJson))
    else:
        return jsonify([{"status": 400, "message": "No records found"}])



def getAllProducts():
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM Products LEFT JOIN ProductCategory ON Products.product_category = ProductCategory.product_category_id")

    products = cursor.fetchall()
    conn.close()

    # print(products)
    productJson = []

    if products:
        for product in products:
            #tempProduct = {
            productJson.append({
                "id": product[0],
                "product_id": product[1],
                "product_name": product[2],
                "product_category": product[3],
                "product_category_name": product[9],
                "product_price": product[4],
                "product_stock": product[5],
                "product_expiry_date": product[6]
            })

            #productJson.append(tempProduct)

        return(json.dumps(productJson))
    else:
        return jsonify([{"status": 400, "message": "No records found"}])



def getAllProductCategory():
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM ProductCategory")

    productcategory = cursor.fetchall()
    conn.close()

    productcategoryJson = []

    if productcategory:
        for category in productcategory:
            tempProductCategory = {
                "product_category_id": category[1],
                "product_category_name": category[2],
                "product_category_description": category[3]
            }

            productcategoryJson.append(tempProductCategory)

        return(json.dumps(productcategoryJson))
    else:
        return jsonify([{"status": 400, "message": "No records found"}])



def getAllOrders():
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()

    cursor.execute("SELECT Orders.id, Orders.order_id, Orders.user_id, Users.name, Orders.product_id, Products.product_name, Products.product_category, ProductCategory.product_category_name, Orders.product_price, Orders.order_quantity, Orders.order_date, Orders.is_approved, Orders.order_status_updated_date FROM Orders LEFT JOIN Users ON Orders.user_id = Users.user_id LEFT JOIN Products ON Orders.product_id = Products.product_id LEFT JOIN ProductCategory ON Products.product_category = ProductCategory.product_category_id;")

    orders = cursor.fetchall()
    conn.close()

    # print(products)
    ordersJson = []

    if orders:
        for order in orders:
            ordersJson.append({
                "id": order[0],
                "order_id": order[1],
                "user_id": order[2],
                "name": order[3],                
                "product_id": order[4],
                "product_name": order[5],
                "product_category": order[6],
                "product_category_name": order[7],
                "product_price": order[8],
                "order_quantity": order[9],
                "order_date": order[10],
                "is_approved": order[11],
                "order_status_updated_on": order[12]
            })

        return json.dumps(ordersJson)
    else:
        return jsonify([{"status": 400, "message": "No records found"}])



def getAllUsersStockProducts():
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()

    cursor.execute("SELECT UserStockProducts.id, UserStockProducts.user_id, Users.name, UserStockProducts.order_id, UserStockProducts.product_id, Products.product_name, Products.product_category, UserStockProducts.product_price, UserStockProducts.user_stock, UserStockProducts.user_stock_product_expiry_date FROM UserStockProducts LEFT JOIN Users ON UserStockProducts.user_id = Users.user_id LEFT JOIN Products ON UserStockProducts.product_id = Products.product_id;")

    usersstockproducts = cursor.fetchall()
    conn.close()

    #print(usersstockproducts)
    usersstockproductsJson = []

    if usersstockproducts:
        for userstock in usersstockproducts:
            tempData = {
                "user_stock_id": userstock[0],
                "user_id": userstock[1],
                "name": userstock[2],
                "order_id": userstock[3],
                "product_id": userstock[4],                
                "product_name": userstock[5],
                "product_category": userstock[6],
                "product_price": userstock[7],
                "user_stock": userstock[8],
                "user_stock_product_expiry_date": userstock[9]
            }

            usersstockproductsJson.append(tempData)

        return(json.dumps(usersstockproductsJson))
    else:
        return jsonify([{"status": 400, "message": "No records found"}])



def getStockProductsByUserID(userId):
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()

    cursor.execute("SELECT UserStockProducts.id, UserStockProducts.user_id, Users.name, UserStockProducts.product_id, Products.product_name, Products.product_category, UserStockProducts.product_price, UserStockProducts.user_stock, UserStockProducts.user_stock_product_expiry_date FROM UserStockProducts LEFT JOIN Users ON UserStockProducts.user_id = Users.user_id LEFT JOIN Products ON UserStockProducts.product_id = Products.product_id WHERE UserStockProducts.user_id = ?", (userId,))
    userstockproducts = cursor.fetchall()
    conn.close()

    userstockproductsJson = []

    if userstockproducts:
        for userstock in userstockproducts:
            tempData = {
                "user_stock_id": userstock[0],
                "user_id": userstock[1],
                "name": userstock[2],
                "product_id": userstock[3],                
                "product_name": userstock[4],
                "product_category": userstock[5],
                "product_price": userstock[6],
                "user_stock": userstock[7],
                "user_stock_product_expiry_date": userstock[8]
            }

            userstockproductsJson.append(tempData)

        return(json.dumps(userstockproductsJson))
    else:
        return jsonify([{"status": 400, "message": "No records found"}])