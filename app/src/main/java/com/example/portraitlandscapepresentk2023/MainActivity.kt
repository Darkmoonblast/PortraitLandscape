package com.example.portraitlandscapepresentk2023

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val pictures = intArrayOf(R.drawable.car1, R.drawable.car2, R.drawable.car3)

    private lateinit var picture: ImageView
    private lateinit var spinner: Spinner
    private lateinit var prefs: SharedPreferences

    private var current = -1

    private var spinnerReady = false

    companion object {
        const val KEY = "picture"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getPreferences(Context.MODE_PRIVATE)
        picture = findViewById(R.id.picture)
        spinner = findViewById(R.id.pictures_list)

        val adapter = ArrayAdapter.createFromResource(
            this, R.array.pictures, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val saved = prefs.getInt(KEY, -1)
        if (saved in pictures.indices) {
            current = saved
            picture.setImageResource(pictures[saved])
            spinner.setSelection(saved)
        }

        spinner.onItemSelectedListener = this
    }

    fun onChangePictureClick(v: View) {
        showPicture((current + 1) % pictures.size)
    }

    private fun showPicture(index: Int) {
        current = index
        picture.setImageResource(pictures[index])
        prefs.edit().putInt(KEY, index).apply()
        if (spinner.selectedItemPosition != index) {
            spinner.setSelection(index)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (!spinnerReady) {
            spinnerReady = true
            return
        }
        if (position == current) return
        showPicture(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}