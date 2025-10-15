package ie.setu.teammanager.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.setu.teammanager.R
import ie.setu.teammanager.databinding.ActivityTeamListBinding
import ie.setu.teammanager.databinding.CardTeamBinding
import ie.setu.teammanager.main.MainApp
import ie.setu.teammanager.models.TeamManagerModel

class TeamListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityTeamListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = TeamAdapter(app.teams)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, TeamActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.teams.size)
            }
        }
}

class TeamAdapter constructor(private var teams: List<TeamManagerModel>) :
    RecyclerView.Adapter<TeamAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val team = teams[holder.adapterPosition]
        holder.bind(team)
    }

    override fun getItemCount(): Int = teams.size

    class MainHolder(private val binding: CardTeamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(team: TeamManagerModel) {
            binding.teamName.text = team.name
            binding.teamDescription.text = team.description
        }
    }
}
