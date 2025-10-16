package ie.setu.teammanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.teammanager.databinding.CardTeamBinding
import ie.setu.teammanager.models.TeamManagerModel

class TeamAdapter(private var teams: List<TeamManagerModel>) :
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
