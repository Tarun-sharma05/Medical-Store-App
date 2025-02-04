# Python | Flask | API
# Project: Pharma Management
# Created by: Abin P
# Mentor: Mohd. Aakib
# Created on: 26.09.2024
# Last Modified on: 02.10.2024

from flask import Flask, jsonify, request

from db.createTableOperation import createTable
from sqlQueryTest import runQuery
from db.userAuthentication import userAuth
from db.addOperation import createUser, createProduct, createProductCategory, createOrder, createOrderApprovedUserStock, approveOrder
from db.getOperation import getAllUsers, getUserInfo, getAllProducts, getAllProductCategory, getAllOrders, getAllUsersStockProducts, getStockProductsByUserID, getAllProducts_Old
from db.updateOperation import updatePassword, updateUserProfileFields

app = Flask(__name__)


@app.route('/', methods=['GET'])
def homeRoute():
    return "Pharma Management: API is Active"



@app.route('/signup', methods=['POST'])
def signupRoute():
    try:
        name = request.form['Name']
        password = request.form['Password']
        email = request.form['Email']
        phonenumber = request.form['PhoneNumber']
        address = request.form['Address']
        pincode = request.form['PinCode']

        userdata = createUser(name=name, password=password, phonenumber=phonenumber, email=email, address=address, pincode=pincode)
        return userdata
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])



@app.route('/login', methods=['POST'])
def loginRoute():
    try:
        email = request.form['Email']
        password = request.form['Password']

        userData = userAuth(email=email, password=password)

        if userData:
            return jsonify([{"status": 200, "user_id": userData[1],
                            "message": "Logged in Successfully"}])
        else:
            return jsonify([{"status": 400, "message": "Invalid Login Credentials"}])
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])



@app.route('/allusers', methods=['GET'])
def allUsersRoute():
    try:
        return getAllUsers()
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])



@app.route('/userinfo', methods=['POST'])
def userInfoRoute():
    try:
        userID = request.form['UserID']

        userData = getUserInfo(userId=userID)
        return userData
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])


@app.route('/addproduct', methods=['POST'])
def addProductRoute():
    try:
        productname = request.form['ProductName']
        productcategory = request.form['ProductCategory']
        productprice = request.form['ProductPrice']
        productstock = request.form['ProductStock']
        productexpirydate = request.form['ProductExpiryDate']

        productdata = createProduct(productname=productname, productcategory=productcategory, productprice=productprice, productstock=productstock, productexpirydate=productexpirydate)
        return productdata
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])



@app.route('/allproducts', methods=['GET'])
def allProductsRoute():
    try:
        return getAllProducts()
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])



@app.route('/addproductcategory', methods=['POST'])
def addProductCategoryRoute():
    try:
        productcategoryid = request.form['ProductCategoryId']
        productcategoryname = request.form['ProductCategoryName']
        productcategorydescription = request.form['ProductCategoryDescription']

        productcategorydata = createProductCategory(productcategoryid=productcategoryid, productcategoryname=productcategoryname, productcategorydescription=productcategorydescription)
        return productcategorydata
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])



@app.route('/allproductcategory', methods=['GET'])
def allProductCategoryRoute():
    try:
        return getAllProductCategory()
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])



@app.route('/addorder', methods=['POST'])
def addOrderRoute():
    try:
        userid = request.form['UserID']
        productid = request.form['ProductID']
        productprice = request.form['ProductPrice']
        orderquantity = request.form['Quantity']

        orderdata = createOrder(userid=userid, productid=productid, productprice=productprice, orderquantity=orderquantity)
        return orderdata
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])



@app.route('/allorders', methods=['GET'])
def allOrdersRoute():
    try:
        return getAllOrders()
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])


#### replaced this with new route /approveorder ####
@app.route('/orderapprovedcreateuserstock', methods=['POST'])
def orderApprovedCreateUserStockRoute():
    try:
        #userid = request.form['UserID']
        #orderid = request.form['OrderID']
        #productid = request.form['ProductID']
        #productprice = request.form['ProductPrice']
        #userstock = request.form['UserStock']
        #userstockproductexpirydate = request.form['ProductExpiryDate']

        #userstockdata = createOrderApprovedUserStock(userid=userid, orderid=orderid, productid=productid, productprice=productprice, userstock=userstock, userstockproductexpirydate=userstockproductexpirydate)
        return jsonify([{"message": "route /orderapprovedcreateuserstock is Deprecated: use new route /approveorder"}])
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])



@app.route('/approveorder', methods=['POST'])
def approveOrderRoute():
    try:
        orderid = request.form['OrderID']

        userstockdata = approveOrder(orderid=orderid)
        return userstockdata
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])



@app.route('/allusersstockproducts', methods=['GET'])
def allUsersStockProductsRoute():
    try:
        return getAllUsersStockProducts()
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])



@app.route('/userstock', methods=['POST'])
def userStockProductsRoute():
    try:
        userID = request.form['UserID']

        userstockbyuserid = getStockProductsByUserID(userId=userID)
        return userstockbyuserid
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])


@app.route('/updatepassword', methods=['PATCH'])
def updatePasswordRoute():
    try:
        password = request.form['Password']
        userID = request.form['UserID']

        updatedata = updatePassword(userid=userID, password=password)
        return jsonify([{"status": 200, "message": "Password Changed Successfully", "data": updatedata}])
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])



@app.route('/updateuserprofile', methods=['PATCH'])
def updateUserProfileRoute():
    try:
        userID = request.form['UserID']
        allfields = request.form.items()
        updateuserdata = {}

        for key, value in allfields:
            if key != 'UserID':
                updateuserdata[key] = value

        # Python Keyword Arguments **kwargs
        updateUserProfileFields(userid=userID, **updateuserdata)

        return jsonify([{"status": 200, "message": "Updated Data"}])
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])



@app.route('/testquery', methods=['GET'])
def queryRoute():
    try:
        return runQuery()
    except Exception as errorMessage:
        return jsonify([{"status": 400, "message": str(errorMessage)}])



if __name__ == "__main__":
    createTable()
    app.run(debug=True)
