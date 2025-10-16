package ie.setu.teammanager.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.setu.teammanager.R
import ie.setu.teammanager.databinding.ActivityTeamBinding
import ie.setu.teammanager.main.MainApp
import ie.setu.teammanager.models.TeamManagerModel

class TeamActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamBinding
    var team = TeamManagerModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        binding.btnAdd.setOnClickListener {
            team.name = binding.teamName.text.toString()
            team.description = binding.teamDescription.text.toString()

            if (team.name.isNotEmpty()) {
                app.teams.add(team.copy())
                setResult(RESULT_OK)
                finish()
            } else {
                Snackbar.make(it, "Please enter a team name", Snackbar.LENGTH_LONG).show()
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_team, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
