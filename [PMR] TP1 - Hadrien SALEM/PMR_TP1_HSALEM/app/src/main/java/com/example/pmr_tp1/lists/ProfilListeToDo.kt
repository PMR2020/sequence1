package com.example.pmr_tp1.lists

import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable

class ProfilListeToDo(var login : String, var mesListesToDo : MutableList<ListeToDo>) : Serializable {
    fun ajouteListe(uneListe: ListeToDo) {
        mesListesToDo.add(uneListe)
        //TODO : ajout au JSON
    }


    override fun toString(): String {
        var stringListes=""
            for (liste in mesListesToDo) {
                stringListes += (liste.toString())
            }
        val string = "Utilisateur : $login\n"+
                "Listes : \n"+
                stringListes +
                "\n"
        return string
    }

    /**
     * @return A java JSONObject representing this item
     * !!! The JSONObject does not contain the login, since the login is the key to the value mesListesToDo !!!
     */
    fun toJSONObject() : JSONArray {
        var mesListesJSON = JSONArray()
        for(list in mesListesToDo){
            mesListesJSON.put(list.toJSONObject())
        }

        return mesListesJSON
    }

    /**
     * @return A string containing JSON text corresponding to this item
     */
    fun toJSONString() : String{
        val profileJSON = toJSONObject()
        return(profileJSON.toString())
    }
}