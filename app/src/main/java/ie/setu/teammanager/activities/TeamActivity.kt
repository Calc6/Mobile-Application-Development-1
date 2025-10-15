package ie.setu.teammanager.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.setu.teammanager.databinding.ActivityTeamBinding
import ie.setu.teammanager.main.MainApp
import ie.setu.teammanager.models.TeamManagerModel
import timber.log.Timber
import timber.log.Timber.i

class TeamActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamBinding
    var team = TeamManagerModel()
    val teams = ArrayList<TeamManagerModel>()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app=application as MainApp
        i("Team Activity started...")

        binding.btnAdd.setOnClickListener() {
            team.name = binding.teamName.text.toString()
            team.description = binding.teamDescription.text.toString()

            if (team.name.isNotEmpty()) {
                app.teams.add(team.copy())
                Timber.i("Add Button Pressed: $team")

                for (i in app.teams.indices) {
                    Timber.i("Team[$i]: ${app.teams[i]}")
                }

                setResult(RESULT_OK)
                finish()
            } else {
                Snackbar
                    .make(it, "Please enter a team name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}
