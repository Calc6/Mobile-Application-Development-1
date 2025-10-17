package ie.setu.teammanager.tools

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ie.setu.teammanager.models.TeamManagerModel
import java.io.File


// File where all the team data will be stored
private const val TEAM_FILE = "teams.json"

fun saveAllTeams(context: Context, teamList: List<TeamManagerModel>) {
    val gson = Gson()
    val jsonString = gson.toJson(teamList)
    val file = File(context.filesDir, TEAM_FILE)
    file.writeText(jsonString)
}

fun loadAllTeams(context: Context): MutableList<TeamManagerModel> {
    val file = File(context.filesDir, TEAM_FILE)
    if (!file.exists()) {
        return mutableListOf()
    }

    val jsonString = file.readText()
    val gson = Gson()
    val listType = object : TypeToken<MutableList<TeamManagerModel>>() {}.type
    return gson.fromJson(jsonString, listType)
}
