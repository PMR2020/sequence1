package com.example.pmr_tp1.utils

import android.content.Context
import android.util.Log
import com.example.pmr_tp1.lists.ItemToDo
import com.example.pmr_tp1.lists.ListeToDo
import com.example.pmr_tp1.lists.ProfilListeToDo
import org.json.JSONArray
import org.json.JSONObject
import java.io.*

class UserManager(val login: String, context: Context) : Serializable {

    val fileName: String = "list_data.json"
    val file = File(context.filesDir, fileName)

    val profile: ProfilListeToDo

    init {

        if (!file.exists()) {
            writeInData(DATA_INIT)
            Log.i("SNOW", "PATH : " + context.filesDir)
            Log.i("SNOW", "DATA IN DATA.JSON" + readData())
        }

        profile = manageUser()
    }

    // UTILS

    /**
     * Writes string into the file named fileName
     * @param string : The string that will be writed in the app data file
     */
    private fun writeInData(string: String) {
        val fileWriter =
            FileWriter(file) // need to open a BufferedWriter object to write inside the file everytime it's needed, since it will be closed after being used
        val bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.write(string)
        bufferedWriter.close()
    }

    /**
     * @return : content of the data file ( => fileName) as a string
     */
    private fun readData(): String {
        val fileReader = FileReader(file)
        val bufferedReader = BufferedReader(fileReader)
        val stringBuilder = StringBuilder()
        var line = bufferedReader.readLine()
        while (line != null) {
            stringBuilder.append(line).append("\n")
            line = bufferedReader.readLine()
        }
        bufferedReader.close()
        return stringBuilder.toString()
    }

    /**
     * @param string : string obtained from parsing a JSON file
     * @return : that string, converted into a JSON object
     */
    private fun convertStringToJSONObject(string: String): JSONObject {
        return JSONObject(string)
    }

    /**
     * @param jsonArray : JSONArray containing to-do lists
     * @return the same lists, as a MutableList of ListeToDo's
     */
    private fun convertListsFromJSON(jsonArray: JSONArray): MutableList<ListeToDo> {
        var mesListesToDo: MutableList<ListeToDo> = mutableListOf()
        for (i in 0..jsonArray.length() - 1) {
            val item = jsonArray.getJSONObject(i)
            mesListesToDo.add(convertListFromJSON(item))
        }
        return mesListesToDo
    }

    /**
     * @param jsonItem : JSONObject representing an item from a to-do list
     * @requires : the item has a string "description" and a boolean "fait"
     */
    private fun convertItemFromJSON(jsonItem: JSONObject): ItemToDo {
        // TODO : check if the item has the two arguments, and check if their type is valid
        return ItemToDo(jsonItem.get("description") as String, jsonItem.get("fait") as Boolean)
    }

    /**
     * @param jsonList : JSONObject representing a to-do list containing items
     * @requires : the list contains an array "items" of items that have a string "description" and a boolean "fait", and an attribute "titreListe"
     */
    private fun convertListFromJSON(jsonList: JSONObject): ListeToDo {
        val listTitle = jsonList.get("titreListe") as String
        val jsonItems = jsonList.get("items") as JSONArray
        var items = mutableListOf<ItemToDo>()

        for (i in 0..jsonItems.length() - 1) {
            items.add(convertItemFromJSON(jsonItems.get(i) as JSONObject))
        }

        return ListeToDo(listTitle, items)
    }

    // INITIALIZATIONS

    /**
     * Checks if a user named "login" exists in the JSON files. If they do not exist in the files, it means they are new.
     */
    private fun isNewUser(): Boolean {
        val dataString = readData()
        val dataJSON = convertStringToJSONObject(dataString)
        return !dataJSON.has(this.login)
    }

    /**
     * Adds a user named "login" to the JSON files.
     */
    private fun createNewUser() {
        val dataString = readData()
        val dataJSON = convertStringToJSONObject(dataString)

/*      var dummyItem : ItemToDo = ItemToDo("DummyItem", false)
        var dummyList : ListeToDo = ListeToDo("DummyList", mutableListOf<ItemToDo>(dummyItem))
        var dummyMesListes : MutableList<ListeToDo> = mutableListOf(dummyList)
        var dummyProfile : ProfilListeToDo = ProfilListeToDo(login, dummyMesListes)
        var mesListesJSON = dummyProfile.toJSONObject()

        dataJSON.put(login, mesListesJSON)*/

        dataJSON.put(login, JSONArray())
        writeInData(dataJSON.toString())
        Log.i("SNOW", readData())

    }

    fun createNewList(listTitle: String) {
        val dataString = readData()
        val dataJSON = convertStringToJSONObject(dataString)

        val newList = ListeToDo(listTitle)
        profile.ajouteListe(newList)

        val thisProfileJSON = profile.toJSONObject()
        dataJSON.put(login, thisProfileJSON)
        writeInData(dataJSON.toString())

    }

    fun changeItemState(indexOfList: Int, indexOfItem: Int, isChecked: Boolean) {
        val dataString = readData()
        val dataJSON = convertStringToJSONObject(dataString)

        profile.mesListesToDo[indexOfList].lesItems[indexOfItem].fait = isChecked

        dataJSON.put(login, profile.toJSONObject())
        writeInData(dataJSON.toString())

    }

    fun createNewItem(itemDescription: String, indexOfList: Int) {
        val dataString = readData()
        val dataJSON = convertStringToJSONObject(dataString)

        val newItem = ItemToDo(itemDescription)
        profile.mesListesToDo[indexOfList].lesItems.add(newItem)

        val thisProfileJSON = profile.toJSONObject()
        dataJSON.put(login, thisProfileJSON)
        writeInData(dataJSON.toString())

    }

    /**
     * Returns an object of type "ProfilListeToDo", constructed from the user in the JSON files who has the login "login".
     */
    private fun buildProfile(): ProfilListeToDo {

        val dataString = readData()
        var dataJSON = convertStringToJSONObject(dataString)
        val mesListesJSON = dataJSON.get(this.login) as JSONArray
        // a JSONArray of to-do lists is associated to the key USER_NAME contained in login

        val mesListesToDo = convertListsFromJSON(mesListesJSON)

        val builtProfile = ProfilListeToDo(login, mesListesToDo)

        Log.i("SNOW", "\nBuilt profile :\n" + builtProfile.toString())

        return builtProfile
    }

    fun manageUser(): ProfilListeToDo {
        /** GESTION DE L'UTILISATEUR
        Création d'un nouveau profil s'il n'existait pas, récupération du profil sinon.
         */

        Log.i("SNOW", "data.json absolute path : " + file.absolutePath)

        Log.i("SNOW", "Is the user new ? >> ${if (isNewUser()) "Yes" else "No"}")
        if (isNewUser()) {
            createNewUser()
        }
        return buildProfile()

    }

    /**
     * Returns all logins in the data file
     * Will not display the debug login
     */
    fun getAllUserNames(): Array<String> {
        val dataString = readData()
        val dataJSON = convertStringToJSONObject(dataString)

        val keyIterator = dataJSON.keys()
        var userNamesList = mutableListOf<String>()

        while (keyIterator.hasNext()) {

            val nextKey = keyIterator.next()

            if(nextKey!= DEBUG_LOGIN) userNamesList.add(nextKey)

/*            Log.i("SNOW", "Debug login : " + DEBUG_LOGIN)
            Log.i("SNOW", "Next login : " + keyIterator.next())
            Log.i("SNOW", "Debug login is next : " + DEBUG_LOGIN)*/
            //userNamesList.add(keyIterator.next())
        }

        val userNamesArray = userNamesList.toTypedArray()

        return userNamesArray
    }

    companion object DataInitalise {

        val DEBUG_LOGIN = "DEBUG_123456789"

        val DATA_INIT: String = "{\n" +
                "  \"${com.example.pmr_tp1.utils.UserManager.DataInitalise.DEBUG_LOGIN}\":[\n" +
                "    {\"titreListe\": \"Liste1\",\n" +
                "      \"items\": [\n" +
                "        {\"description\": \"item11\",\"fait\": false},\n" +
                "        {\"description\": \"item12\",\"fait\": false}\n" +
                "      ]\n" +
                "    },\n" +
                "    {\"titreListe\": \"Liste2\",\n" +
                "      \"items\": [\n" +
                "        {\"description\": \"item21\",\"fait\": false},\n" +
                "        {\"description\": \"item22\",\"fait\": false}\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}"
    }
}