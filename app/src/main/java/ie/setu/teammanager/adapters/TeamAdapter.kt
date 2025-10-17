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

class TeamAdapter(
    private var teams: MutableList<TeamManagerModel>,
    private val app: MainApp
) : RecyclerView.Adapter<TeamAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val team = teams[holder.adapterPosition]
        holder.bind(team, app, this)
    }

    override fun getItemCount(): Int = teams.size

    class MainHolder(private val binding: CardTeamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(team: TeamManagerModel, app: MainApp, adapter: TeamAdapter) {
            binding.teamName.text = team.name
            binding.teamDescription.text = team.description

            binding.btnDelete.setOnClickListener {
                Timber.i("Deleting team: ${team.name}")
                app.teams.remove(team)
                adapter.notifyDataSetChanged()
            }

            binding.btnEdit.setOnClickListener {
                Timber.i("Editing team: ${team.name}")
                val context = binding.root.context
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Edit Team")

                val inflater = LayoutInflater.from(context)
                val dialogView = inflater.inflate(R.layout.edit_team, null)
                builder.setView(dialogView)

                val nameInput = dialogView.findViewById<EditText>(R.id.editTeamName)
                val descInput = dialogView.findViewById<EditText>(R.id.editTeamDescription)

                nameInput.setText(team.name)
                descInput.setText(team.description)

                builder.setPositiveButton("Save") { _, _ ->
                    team.name = nameInput.text.toString()
                    team.description = descInput.text.toString()
                    app.teams[adapterPosition] = team
                    adapter.notifyItemChanged(adapterPosition)
                }

                builder.setNegativeButton("Cancel", null)
                builder.show()
            }
        }
    }
}
