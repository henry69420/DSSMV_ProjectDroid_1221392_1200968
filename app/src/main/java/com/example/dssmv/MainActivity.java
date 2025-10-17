package com.example.dssmv;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.activity.compose.setContent;
import androidx.activity.enableEdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.foundation.layout.fillMaxSize;
import androidx.compose.foundation.layout.padding;
import androidx.compose.material3.Scaffold;
import androidx.compose.material3.Text;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.tooling.preview.Preview;
//import com.example.dssmv.databinding.ActivityMainBinding;
//import com.example.dssmv.ui.BookSearchActivity;
//import com.example.dssmv.ui.CreateLibraryActivity;
import com.example.dssmv.ui.*;
import com.example.dssmv.ui.LibraryListActivity;
//import com.example.dssmv.ui.DeleteLibraryActivity;

import static androidx.core.content.ContextCompat.startActivity;

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up button click listeners
     //   binding.btnSearchBooks.setOnClickListener { openBookSearch() }
        binding.btnLibraries.setOnClickListener { openLibraries();}
        //binding.btnCheckouts.setOnClickListener { openCheckouts() }
        //binding.btnAddLibrary.setOnClickListener { openAddLibrary() }
       // binding.btnDeleteLibrary.setOnClickListener { openDeleteLibrary() }
    }


    private fun openLibraries() {
        startActivity(Intent(this, LibraryListActivity::class.java))
    }

    private fun openAddLibrary() {
        startActivity(Intent(this, CreateLibraryActivity::class.java))
    }


/* private fun openCheckouts() {
     startActivity(Intent(this, CheckoutActivity::class.java))
 }


    private fun openDeleteLibrary() {
        startActivity(Intent(this, DeleteLibraryActivity::class.java))
    }
*/
}
