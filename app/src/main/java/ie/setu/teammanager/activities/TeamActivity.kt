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
    lateinit var app: MainApp
    var team = TeamManagerModel()
    var editMode = false
    var editPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        if (intent.hasExtra("team_name")) {
            editMode = true
            binding.teamName.setText(intent.getStringExtra("team_name"))
            binding.teamDescription.setText(intent.getStringExtra("team_description"))
            binding.managerName.setText(intent.getStringExtra("team_manager"))
            binding.captainName.setText(intent.getStringExtra("team_captain"))
            binding.stadiumName.setText(intent.getStringExtra("team_stadium"))

            editPosition = intent.getIntExtra("team_position", -1)
        }

        binding.btnAdd.setOnClickListener {
            team.name = binding.teamName.text.toString()
            team.manager = binding.managerName.text.toString()
            team.captain = binding.captainName.text.toString()
            team.stadium = binding.stadiumName.text.toString()
            team.description = binding.teamDescription.text.toString()

            if (team.name.isNotEmpty()) {
                if (editMode && editPosition >= 0) {
                    app.teams[editPosition] = team.copy()
                } else {
                    app.teams.add(team.copy())
                }
                app.saveTeams()
                setResult(RESULT_OK)
                finish()
            } else {
                Snackbar.make(it, "enter a team name", Snackbar.LENGTH_LONG).show()
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

