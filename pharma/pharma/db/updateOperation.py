import sqlite3
from constants import DBNAME

def updatePassword(userid, password):
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()

    cursor.execute("UPDATE Users SET password = ? WHERE user_id = ?", (userid, password))
    # add a logic here later to verify if its updated in the record
    
    conn.commit()
    conn.close()



def updateUserProfileFields(userid, **updateuserdata):
    conn = sqlite3.connect(DBNAME)
    cursor = conn.cursor()

    for key, value in updateuserdata.items():
        if key == "Name":
            cursor.execute("UPDATE Users SET name = ? WHERE user_id = ?", (value, userid))
        elif key == "Password":
            cursor.execute("UPDATE Users SET password = ? WHERE user_id = ?", (value, userid))
        elif key == "email":
            cursor.execute("UPDATE Users SET email = ? WHERE user_id = ?", (value, userid))
    
    conn.commit()
    conn.close()
