package com.example.notes.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.models.Note
import kotlin.random.Random

class NotesAdapter(
    private val context: Context, private val listener: NotesClickListener
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val notesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount() = notesList.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notesList[position]
        holder.titleTV.text = currentNote.title
        holder.titleTV.isSelected = true
        holder.noteTV.text = currentNote.note
        holder.dateTV.text = currentNote.date
        holder.dateTV.isSelected = true
        holder.notesLayout.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                randomColor(), null
            )
        )
        holder.notesLayout.setOnClickListener {
            listener.onItemClicked(notesList[holder.adapterPosition])
        }
        holder.notesLayout.setOnLongClickListener {
            listener.onLongItemClicked(notesList[holder.adapterPosition], holder.notesLayout)
            true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Note>) {
        fullList.clear()
        fullList.addAll(newList)
        notesList.clear()
        notesList.addAll(fullList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(search: String) {
        notesList.clear()
        for (item in fullList) {
            if (item.title?.lowercase()
                    ?.contains(search.lowercase()) == true || item.note?.lowercase()
                    ?.contains(search.lowercase()) == true
            ) {
                notesList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    private fun randomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.NoteColor1)
        list.add(R.color.NoteColor2)
        list.add(R.color.NoteColor3)
        list.add(R.color.NoteColor4)
        list.add(R.color.NoteColor5)
        list.add(R.color.NoteColor6)
        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notesLayout: CardView = itemView.findViewById(R.id.card_layout)
        val titleTV: TextView = itemView.findViewById(R.id.tv_title)
        val noteTV: TextView = itemView.findViewById(R.id.tv_note)
        val dateTV: TextView = itemView.findViewById(R.id.tv_date)

    }

    interface NotesClickListener {
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }
}