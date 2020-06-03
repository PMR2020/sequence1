package com.example.listedroulante.utility

import android.content.Context
import com.google.gson.GsonBuilder
import java.io.*

class jsonManager {

    companion object {
        //    fun toPrettyPrint (jsonStr : String): String? {
        //        var jsonObj : JSONObject? = null
        //        try {
        //            jsonObj = JSONObject(jsonStr)
        //            var gson : Gson = GsonBuilder()
        //                .serializeNulls()
        //                .disableHtmlEscaping()
        //                .setPrettyPrinting()
        //                .create()
        //            return gson.toJson(jsonObj)
        //        } catch (e : JSONException) {
        //            e.printStackTrace()
        //            return "prettyprint error"
        //        }
        //    }
//
        //    fun listToJSString (userList: UserList?) {
        //        var gson = Gson()
        //        try {
        //            gson.toJson(userList, FileWriter("C:\\Users\\augus\\AndroidStudioProjects\\Listedroulante\\app\\src\\main\\res\\userlist.json"))
        //        } catch (e : IOException) {
        //            e.printStackTrace()
        //        }
        //    }
//
        //    fun JSStringToList (jstring: String?): UserList? {
        //        var gson = Gson()
        //            return gson.fromJson(jstring , UserList::class.java)
        //    }
//
        //    fun readJSString (context : Context): String? {
        //        val jsstring : String
        //        try {
        //            jsstring = context.assets
        //                .open("C:\\Users\\augus\\AndroidStudioProjects\\Listedroulante\\app\\src\\main\\res\\userlist.json")
        //                .bufferedReader().use { it.readText()}
        //        } catch (e : IOException) {
        //            e.printStackTrace()
        //            return null
        //        }
        //        return  jsstring
        //    }
//
        //    fun writeJSString (jsstring : String) {
        //        val file = File("C:\\Users\\augus\\AndroidStudioProjects\\Listedroulante\\app\\src\\main\\res\\userlist.json")
        //        file.writeText(jsstring)
        //    }


        private var gson = GsonBuilder().setPrettyPrinting().create()

        fun fromFileToUserList(file: File?): UserList {
            val inputStream: FileInputStream? = file?.inputStream()
            val inputString = inputStream?.bufferedReader().use { it?.readText() }
            inputStream?.close()
            return gson.fromJson(inputString, UserList::class.java)
        }

        fun fromUserListToFile(userList: UserList, file: File?, context: Context) {
            val fileContent = gson.toJson(userList)
            context.openFileOutput(file?.name, Context.MODE_PRIVATE)
                .use { it.write(fileContent.toByteArray()) }
        }
    }
}
