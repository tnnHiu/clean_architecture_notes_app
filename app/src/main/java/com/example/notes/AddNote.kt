package com.example.notes

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notes.databinding.ActivityAddNoteBinding
import com.example.notes.models.Note
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date

class AddNote : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var note: Note
    private lateinit var oldNote: Note
    private var isUpdate = false


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            oldNote = intent.getSerializableExtra("currentNote") as Note
            binding.etTitle.setText(oldNote.title)
            binding.etNote.setText(oldNote.note)
            isUpdate = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.imgCheck.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val noteDesc = binding.etNote.text.toString()
            if (title.isNotEmpty() || noteDesc.isNotEmpty()) {
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")

                note = if (isUpdate) {
                    Note(
                        oldNote.id, title, noteDesc, formatter.format(Date())
                    )
                } else {
                    Note(null, title, noteDesc, formatter.format(Date()))
                }
                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this@AddNote, "Please enter some data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        binding.imgBackArrow.setOnClickListener {
            onBackPressed()
        }


    }
}