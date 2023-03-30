package com.example.tomgrocery.util.localstorage

import android.content.Context
import com.example.tomgrocery.constants.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MyContext(val applicationContext: Context) {
}

//class LocalStorage @Inject constructor(@ApplicationContext context: Context) { //case1
/*class LocalStorage @Inject constructor(private val myContext: MyContext) { //case2
    private val loginPrefs = myContext.applicationContext.getSharedPreferences(Constants.LOGIN_SHARED_PREF, Context.MODE_PRIVATE) */
class LocalStorage @Inject constructor(myContext: MyContext) {
    private val loginPrefs = myContext.applicationContext.getSharedPreferences(Constants.LOGIN_SHARED_PREF, Context.MODE_PRIVATE)
    var count = 0
    private val loginEditor = loginPrefs.edit()
    fun saveLoginInfo(
        firstName: String,
        email: String,
        phone: String,
        userId: String,
        token: String,
        firstTime: Boolean
    ) {
        loginEditor.putString(Constants.PREF_FIRST_NAME, firstName)
        loginEditor.putString(Constants.PREF_EMAIL, email)
        loginEditor.putString(Constants.PREF_PHONE, phone)
        loginEditor.putString(Constants.PREF_USER_ID, userId)
        loginEditor.putString(Constants.PREF_TOKEN, token)
        loginEditor.putBoolean(Constants.PREF_FIRST_TIME, firstTime)
        loginEditor.apply()
    }
    fun hasLoginInfo(): Boolean {
        val email = loginPrefs.getString(Constants.PREF_EMAIL, "") ?: ""
        return email.isNotEmpty()
    }

    fun logoutUser() {
        loginEditor.clear()
        loginEditor.apply()
    }

    fun getUserName(): String {
        return loginPrefs.getString(Constants.PREF_FIRST_NAME, "") ?: ""
    }

    fun getUserId(): String {
        return loginPrefs.getString(Constants.PREF_USER_ID, "") ?: ""
    }

    fun getUserEmail(): String {
        return loginPrefs.getString(Constants.PREF_EMAIL, "") ?: ""
    }

    fun getUserPhone(): String {
        return loginPrefs.getString(Constants.PREF_PHONE, "") ?: ""
    }
//    val KEY_USER = "User"
//    val KEY_USER_ADDRESS = "user_address"
//    private val KEY_FIREBASE_TOKEN = "firebaseToken"
//    private val IS_USER_LOGIN = "IsUserLoggedIn"
//    private val CART = "CART"
//    private val ORDER = "ORDER"
//
//
//    private var instance: LocalStorage? = null
//    var sharedPreferences: SharedPreferences? = null
//    var editor: SharedPreferences.Editor? = null
//    var PRIVATE_MODE = 0
//    var _context: Context? = null
//
//    fun LocalStorage(context: Context) {
//        sharedPreferences = context.getSharedPreferences("Preferences", 0)
//    }
//
//    fun getInstance(context: Context): LocalStorage? {
//        if (instance == null) {
//            synchronized(LocalStorage::class.java) {
//                if (instance == null) {
//                    instance = LocalStorage(context)
//                }
//            }
//        }
//        return instance
//    }
//
//    fun createUserLoginSession(user: String?) {
//        editor = sharedPreferences!!.edit()
//        editor.putBoolean(IS_USER_LOGIN, true)
//        editor.putString(KEY_USER, user)
//        editor.commit()
//    }
//
//    fun getUserLogin(): String? {
//        return sharedPreferences!!.getString(KEY_USER, "")
//    }
//
//
//    fun logoutUser() {
//        editor = sharedPreferences!!.edit()
//        editor.remove(KEY_USER)
//        editor.remove(KEY_USER_ADDRESS)
//        editor.remove(IS_USER_LOGIN)
//        editor.remove(CART)
//        editor.remove(ORDER)
//        editor.commit()
//    }
//
//    fun checkLogin(): Boolean {
//        // Check login status
//        return !isUserLoggedIn()
//    }
//
//
//    fun isUserLoggedIn(): Boolean {
//        return sharedPreferences!!.getBoolean(IS_USER_LOGIN, false)
//    }
//
//    fun getUserAddress(): String? {
//        return if (sharedPreferences!!.contains(KEY_USER_ADDRESS)) sharedPreferences!!.getString(
//            KEY_USER_ADDRESS,
//            null
//        ) else null
//    }
//
//
//    fun setUserAddress(user_address: String?) {
//        val editor = sharedPreferences!!.edit()
//        editor.putString(KEY_USER_ADDRESS, user_address)
//        editor.commit()
//    }
//
//    fun getCart(): String? {
//        return if (sharedPreferences!!.contains(CART)) sharedPreferences!!.getString(
//            CART,
//            null
//        ) else null
//    }
//
//
//    fun setCart(cart: String?) {
//        val editor = sharedPreferences!!.edit()
//        editor.putString(CART, cart)
//        editor.commit()
//    }
//
//    fun deleteCart() {
//        val editor = sharedPreferences!!.edit()
//        editor.remove(CART)
//        editor.commit()
//    }
//
//
//    fun getOrder(): String? {
//        return if (sharedPreferences!!.contains(ORDER)) sharedPreferences!!.getString(
//            ORDER,
//            null
//        ) else null
//    }
//
//
//    fun setOrder(order: String?) {
//        val editor = sharedPreferences!!.edit()
//        editor.putString(ORDER, order)
//        editor.commit()
//    }
//
//    fun deleteOrder() {
//        val editor = sharedPreferences!!.edit()
//        editor.remove(ORDER)
//        editor.commit()
//    }
//
//
//    fun getFirebaseToken(): String? {
//        return sharedPreferences!!.getString(KEY_FIREBASE_TOKEN, null)
//    }
//
//    fun setFirebaseToken(firebaseToken: String?) {
//        editor = sharedPreferences!!.edit()
//        editor.putString(KEY_FIREBASE_TOKEN, firebaseToken)
//        editor.commit()
//    }

}