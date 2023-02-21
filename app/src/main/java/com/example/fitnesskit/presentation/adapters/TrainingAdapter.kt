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
import com.example.fitnesskit.domain.models.Lesson
import javax.inject.Inject

class TrainingAdapter @Inject constructor (private val context: Context) :
    ListAdapter<Lesson, TrainingAdapter.TrainingViewHolder>(TrainingDiffCallback()) {

    var onItemClickListener: ((Lesson) -> Unit)? = null

    inner class TrainingViewHolder(private val binding: RvItemTrainingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: Lesson, dateVisibility: Boolean) {

            if (item.available_slots > 1) {
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

            //TODO("date")
            binding.tvDate.text = item.date
            binding.tvDate.visibility = when (dateVisibility) {
                true -> View.VISIBLE
                false -> View.GONE
            }


            binding.tvTimeStart.text = item.startTime
            binding.tvTimeEnd.text = item.endTime
            binding.tvTitle.text = item.name
            //TODO("name")
            if (item.available_slots > 1) {
                binding.icCountUser.setImageResource(R.drawable.ic_users)
                binding.tvVisitors.text = "Записано ${item.available_slots}"
            } else {
                binding.icCountUser.setImageResource(R.drawable.ic_user)
                binding.tvVisitors.text = item.coach_id
            }

            binding.tvPlace.text = item.place

            binding.tvDuration.text = item.endTime
            //TODO("time")
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

class TrainingDiffCallback : DiffUtil.ItemCallback<Lesson>() {
    override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson) =
        oldItem.tab_id == newItem.tab_id

    override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson) =
        oldItem == newItem

}
