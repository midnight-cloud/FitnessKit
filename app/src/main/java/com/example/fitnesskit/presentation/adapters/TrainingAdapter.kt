package com.example.fitnesskit.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesskit.R
import com.example.fitnesskit.databinding.RvItemTrainingBinding
import com.example.fitnesskit.domain.models.TrainingModel
import javax.inject.Inject

class TrainingAdapter @Inject constructor (private val context: Context) :
    ListAdapter<TrainingModel, TrainingAdapter.TrainingViewHolder>(TrainingDiffCallback()) {

    var onItemClickListener: ((TrainingModel) -> Unit)? = null

    inner class TrainingViewHolder(private val binding: RvItemTrainingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: TrainingModel, dateVisibility: Boolean) {

            if (item.countVisitors > 1) {
                binding.view.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.some_training
                    )
                )
            } else {
                binding.view.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.personal_training
                    )
                )
            }

            binding.tvDate.text = item.date
            binding.tvDate.visibility = when (dateVisibility) {
                true -> View.VISIBLE
                false -> View.GONE
            }


            binding.tvTimeStart.text = item.timeStart
            binding.tvTimeEnd.text = item.timeEnd
            binding.tvTitle.text = item.title
            if (item.countVisitors > 1) {
                binding.icCountUser.setImageResource(R.drawable.ic_users)
                binding.tvVisitors.text = "Записано ${item.countVisitors}"
            } else {
                binding.icCountUser.setImageResource(R.drawable.ic_user)
                binding.tvVisitors.text = item.trainer
            }

            binding.tvPlace.text = item.place
            binding.tvDuration.text = item.duration
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val binding =
            RvItemTrainingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrainingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        val item = currentList[position]

        if (position != 0) {
            val prevItem = currentList[position - 1]
            if (item.date == prevItem.date) {
                holder.bindItem(item, false)
            } else {
                holder.bindItem(item, true)
            }
        } else {
            holder.bindItem(item, true)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(item)
        }
    }

}

class TrainingDiffCallback : DiffUtil.ItemCallback<TrainingModel>() {
    override fun areItemsTheSame(oldItem: TrainingModel, newItem: TrainingModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TrainingModel, newItem: TrainingModel) =
        oldItem == newItem

}
