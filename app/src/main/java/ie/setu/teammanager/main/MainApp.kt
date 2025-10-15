package ie.setu.teammanager.main

import android.app.Application
import ie.setu.teammanager.models.TeamManagerModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val teams = ArrayList<TeamManagerModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("TeamManager started")

        //teams.add(TeamManagerModel("Pep City", "PLeague Champions"))
        //teams.add(TeamManagerModel("Carlo Madrid", " Royalty"))
       // teams.add(TeamManagerModel("Xavi Barcelona", "Tiki-Taka "))
    }
}
