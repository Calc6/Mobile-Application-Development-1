package ie.setu.teammanager.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ie.setu.teammanager.R
import ie.setu.teammanager.adapters.TeamAdapter
import ie.setu.teammanager.databinding.ActivityTeamListBinding
import ie.setu.teammanager.main.MainApp

class TeamListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityTeamListBinding
    private lateinit var adapter: TeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabSearch.setOnClickListener {
            searchTeam()
        }

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        adapter = TeamAdapter(app.teams, app)
        binding.recyclerView.adapter = adapter

        binding.fabAdd.setOnClickListener {
            val launcherIntent = Intent(this, TeamActivity::class.java)
            getResult.launch(launcherIntent)
        }

        binding.fabDeleteAll.setOnClickListener {
            if (app.teams.isNotEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Delete All Teams?")
                    .setMessage("this will delete all teams.")
                    .setPositiveButton("Delete") { _, _ ->
                        app.teams.clear()
                        adapter.notifyDataSetChanged()
                        Snackbar.make(binding.root, "all teams deleted", Snackbar.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            } else {
                Snackbar.make(binding.root, "No teams to delete", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                adapter.notifyItemRangeChanged(0, app.teams.size)
            }
        }

    private fun searchTeam(){
        val searchView = androidx.appcompat.widget.SearchView(this)
        searchView.queryHint = "search team"

        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("search Team")
            .setView(searchView)
            .setNegativeButton("Close", null)
            .create()

        val adapter = binding.recyclerView.adapter as ie.setu.teammanager.adapters.TeamAdapter

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { adapter.filter(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")
                return true
            }
        })

        dialog.show()
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}
