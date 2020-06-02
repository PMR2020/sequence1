package com.example.pmr_tp1.lists

import org.json.JSONObject
import java.io.Serializable

class ItemToDo (
    var description : String = "",
    var fait : Boolean = false) : Serializable{

    override fun toString(): String {
        return "<Item : $description || Fait: ${if (fait) "oui" else "non"}>\n "
    }

    /**
     * @return A java JSONObject representing this item
     */
    fun toJSONObject() : JSONObject{
        var itemJSON = JSONObject()
        itemJSON.put("description", description)
        itemJSON.put("fait", fait)
        return itemJSON
    }

    /**
     * @return A string containing JSON text corresponding to this item
     */
    fun toJSONString() : String{
        val itemJSON = toJSONObject()
        return itemJSON.toString()
    }
}

// 