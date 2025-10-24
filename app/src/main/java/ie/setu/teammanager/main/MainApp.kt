package ie.setu.teammanager.main

import android.app.Application
import ie.setu.teammanager.models.TeamManagerModel
import timber.log.Timber
import timber.log.Timber.i
import ie.setu.teammanager.tools.loadAllTeams
import ie.setu.teammanager.tools.saveAllTeams

//This is the main app class that runs when the app starts
class MainApp : Application() {

    //list stores all the teams in the app
    var teams = mutableListOf<TeamManagerModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("app started")

        //load saved teams from JSON
        teams = loadAllTeams(this)
        //save current list
        saveAllTeams(this,teams)

        //example teams to make app look full
        teams.add(
            TeamManagerModel(
                name = "Manchester City",
                manager = "Pep Guardiola",
                captain = "Kyle Walker",
                stadium = "Etihad Stadium",
                description = "Premier League champions known for fluid attacking football."
            )
        )

        teams.add(
            TeamManagerModel(
                name = "Liverpool",
                manager = "Arne Slot",
                captain = "Virgil van Dijk",
                stadium = "Anfield",
                description = "Historic club famous for passionate fans and attacking play."
            )
        )

        teams.add(
            TeamManagerModel(
                name = "Arsenal",
                manager = "Mikel Arteta",
                captain = "Martin Ødegaard",
                stadium = "Emirates Stadium",
                description = "Young, dynamic side pushing for the title with creative football."
            )
        )

        teams.add(
            TeamManagerModel(
                name = "Manchester United",
                manager = "Erik ten Hag",
                captain = "Bruno Fernandes",
                stadium = "Old Trafford",
                description = "One of England’s most successful clubs with a rich history."
            )
        )

        teams.add(
            TeamManagerModel(
                name = "Chelsea",
                manager = "Enzo Maresca",
                captain = "Reece James",
                stadium = "Stamford Bridge",
                description = "Talented squad rebuilding with a focus on youth and creativity."
            )
        )

        teams.add(
            TeamManagerModel(
                name = "Tottenham Hotspur",
                manager = "Ange Postecoglou",
                captain = "Son Heung-min",
                stadium = "Tottenham Hotspur Stadium",
                description = "Known for attacking flair and entertaining football."
            )
        )
    }

    //save all teams to json
    fun saveTeams() {
        saveAllTeams(this, teams)
    }
}