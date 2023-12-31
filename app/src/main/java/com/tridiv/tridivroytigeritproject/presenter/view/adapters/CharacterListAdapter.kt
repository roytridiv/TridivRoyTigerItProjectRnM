package com.tridiv.tridivroytigeritproject.presenter.view.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.tridivtigritproject.data.model.CharacterDaoItem
import com.squareup.picasso.Picasso
import com.tridiv.tridivroytigeritproject.databinding.CharacterListItemBinding

class CharacterListAdapter(
    private var charactersList: MutableList<CharacterDaoItem>,
    private val listener: OnItemClickListener,
) : RecyclerView.Adapter<CharacterListAdapter.CharactersListViewHolder>() {

    inner class CharactersListViewHolder(val binding: CharacterListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersListViewHolder {
        val binding = CharacterListItemBinding.inflate(LayoutInflater.from(parent.context))
        val lp = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(10, 10, 10, 10)
        binding.root.layoutParams = lp
        return CharactersListViewHolder(binding)
    }

    override fun getItemCount() = charactersList.size

    override fun onBindViewHolder(holder: CharactersListViewHolder, position: Int) {
        with(holder) {
            val listItem = charactersList[position]
            with(binding) {
                if (listItem.image.isNotEmpty()) Picasso.get().load(listItem.image)
                    .into(charactersIv)
                characterTitleTv.text = "Name: ".plus(listItem.name)
                statusValTv.text = listItem.status
                when (listItem.status) {
                    "Alive" -> binding.statusValTv.setTextColor(Color.parseColor("#38AF6D"))
                    "Dead" -> binding.statusValTv.setTextColor(Color.parseColor("#E9533A"))
                    else -> binding.statusValTv.setTextColor(Color.parseColor("#FF8C00"))
                }
            }

            itemView.setOnClickListener {
                listener.onItemClick(position, listItem)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, characterData: CharacterDaoItem)
    }
}