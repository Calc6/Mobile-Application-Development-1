package ie.setu.teammanager.adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import ie.setu.teammanager.databinding.CardTeamBinding
import ie.setu.teammanager.main.MainApp
import ie.setu.teammanager.models.TeamManagerModel
import ie.setu.teammanager.R
import timber.log.Timber

//adapter for the recycler view
class TeamAdapter(
    private var teams: MutableList<TeamManagerModel>,
    private val app: MainApp
) : RecyclerView.Adapter<TeamAdapter.MainHolder>() {
    private var allTeams: MutableList<TeamManagerModel> = ArrayList(teams)

    init {
        allTeams.addAll(teams)
    }

    //a new view for each team card
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    //adds team data to each card
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val team = teams[holder.adapterPosition]
        holder.bind(team, app, this)
    }

    override fun getItemCount(): Int = teams.size

    //Search teams by name
    fun filter(query: String) {
        teams.clear()
        if (query.isEmpty()) {
            teams.addAll(allTeams)
        } else {
            val filteredTeams = allTeams.filter {
                it.name.contains(query, ignoreCase = true)
            }
            teams.addAll(filteredTeams)
        }
        notifyDataSetChanged()
    }

    class MainHolder(private val binding: CardTeamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(team: TeamManagerModel, app: MainApp, adapter: TeamAdapter) {
            binding.teamName.text = team.name
            binding.teamCaptain.text = team.captain
            binding.teamManager.text = team.manager
            binding .teamStadium.text = team.stadium
            binding.teamDescription.text = team.description


            //delete button is pressed
            binding.btnDelete.setOnClickListener {
                Timber.i("Deleting team: ${team.name}")
                app.teams.remove(team)//remove from list
                app.saveTeams() //save changes
                adapter.notifyDataSetChanged() // refresh
            }

            //edit button is pressed
            binding.btnEdit.setOnClickListener {
                Timber.i("Editing team: ${team.name}")
                val context = binding.root.context
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Edit Team")

                //load the edit view
                val inflater = LayoutInflater.from(context)
                val dialogView = inflater.inflate(R.layout.edit_team, null)
                builder.setView(dialogView)

                //get the input fields
                val nameInput = dialogView.findViewById<EditText>(R.id.editTeamName)
                val descInput = dialogView.findViewById<EditText>(R.id.editTeamDescription)

                //fill with current team info
                nameInput.setText(team.name)
                descInput.setText(team.description)

                //save the changes when button is pressed
                builder.setPositiveButton("Save") { _, _ ->
                    team.name = nameInput.text.toString()
                    team.description = descInput.text.toString()
                    app.teams[adapterPosition] = team
                    adapter.notifyItemChanged(adapterPosition)
                    app.saveTeams()
                }

                //close if cancel is pressed
                builder.setNegativeButton("Cancel", null)
                builder.show()
            }
        }
    }
}
