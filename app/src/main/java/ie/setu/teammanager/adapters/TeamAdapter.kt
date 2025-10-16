package ie.setu.teammanager.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.teammanager.databinding.CardTeamBinding
import ie.setu.teammanager.main.MainApp
import ie.setu.teammanager.models.TeamManagerModel
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
                val editIntent = Intent(context, ie.setu.teammanager.activities.TeamActivity::class.java)
                editIntent.putExtra("Manager Name",team.name)
                editIntent.putExtra("description",team.description)
                context.startActivity(editIntent)
            }
        }
    }
}
