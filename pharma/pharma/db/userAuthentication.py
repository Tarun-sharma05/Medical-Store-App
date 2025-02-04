import sqlite3
from constants import DBNAME

def userAuth(email, password):
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM Users WHERE email = ? AND password = ?", (email, password))

    userData = cursor.fetchone()
    conn.close()

    return userData
