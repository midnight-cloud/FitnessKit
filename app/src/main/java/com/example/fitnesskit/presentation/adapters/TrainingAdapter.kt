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
import com.example.fitnesskit.domain.models.Tab
import com.example.fitnesskit.domain.models.Trainer
import com.example.fitnesskit.presentation.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TrainingAdapter @Inject constructor(
    private val context: Context,
    private val viewModel: MainViewModel
) :
    ListAdapter<Lesson, TrainingAdapter.TrainingViewHolder>(TrainingDiffCallback()) {

    var onItemClickListener: ((Lesson) -> Unit)? = null
    private lateinit var tabList: List<Tab>
    private lateinit var trainerList: List<Trainer>

    init {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.tab.collectLatest {
                tabList = it
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.trainer.collectLatest {
                trainerList = it
            }
        }
    }

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

            binding.tvDate.text = getFormatedDate(item.date)
            binding.tvDate.visibility = when (dateVisibility) {
                true -> View.VISIBLE
                false -> View.GONE
            }

            binding.tvTimeStart.text = item.startTime
            binding.tvTimeEnd.text = item.endTime
            binding.tvTitle.text = getTabName(item.tab_id)

            if (item.available_slots > 1) {
                binding.icCountUser.setImageResource(R.drawable.ic_users)
                binding.tvVisitors.text = "Записано ${item.available_slots}"
            } else {
                binding.icCountUser.setImageResource(R.drawable.ic_user)
                binding.tvVisitors.text = getTrainerName(item.coach_id)
            }
            binding.tvPlace.text = item.place
            binding.tvDuration.text = getTimeDifference(item.startTime, item.endTime)
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
            onItemClickListener?.invoke(currentList[0])
        }
    }

    private fun getTabName(item: Int): String {
        var res = ""
        tabList.forEach {
            if (item == it.id) {
                res = it.name
            }
        }
        return res
    }

    private fun getTrainerName(item: String): String {
        var res = ""
        trainerList.forEach {
            if (it.id == item)
                res = it.name
        }
        return res
    }

    private fun getTimeDifference(t1: String, t2: String): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val time1 = dateFormat.parse(t1)
        val time2 = dateFormat.parse(t2)
        val diff = (time2!!.time - time1!!.time) / 1000
        return ("${diff / 3600}ч. ${(diff - (3600 * (diff / 3600))) / 60}мин.")
    }

    private fun getFormatedDate(date: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val res = dateFormat.parse(date)
        val newDateFormat = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault())
        val newRes = newDateFormat.format(res!!)
        return newRes
    }
}

class TrainingDiffCallback : DiffUtil.ItemCallback<Lesson>() {
    override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson) =
        oldItem.tab_id == newItem.tab_id

    override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson) =
        oldItem == newItem
}
