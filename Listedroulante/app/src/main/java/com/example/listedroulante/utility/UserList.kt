package com.example.listedroulante.utility

import com.example.listedroulante.utility.User
import java.io.Serializable

object UserList {

    var listOfUser : MutableList<User> = mutableListOf()

    //fun toJsonString() {
    //    var userList : String = "{["
    //    val i = this.listOfUser.size
    //    for (x in 0..i) {
    //       userList += "Username : ${this.listOfUser[x].userName}"
    //    }
    //}
}