package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import
        com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import android.content.DialogInterface;
import android.app.AlertDialog;

public class HomeActivity extends AppCompatActivity {
    private ListView listView;
    private FloatingActionButton fab;
    private EditText etSearch;
    private DatabaseHelper0 dbHelper;
    private List<Note> notesList;
    private ArrayAdapter<String> adapter;
    private List<String> titlesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listView = findViewById(R.id.listView);
        fab = findViewById(R.id.fab);
        etSearch = findViewById(R.id.etSearch);
        dbHelper = new DatabaseHelper0(this);
        notesList = new ArrayList<>();
        titlesList = new ArrayList<>();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,
                        AddEditNote.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new
                                                AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> adapterView, View view,
                                                                            int position, long id) {
                                                        Intent intent = new Intent(HomeActivity.this,
                                                                AddEditNote.class);
                                                        intent.putExtra("note_id", notesList.get(position).getId());
                                                        startActivity(intent);
                                                    }
                                                });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Mendapatkan item yang akan dihapus
                final Note noteToDelete = notesList.get(position);

                // Membuat AlertDialog untuk konfirmasi penghapusan
                new AlertDialog.Builder(HomeActivity.this) // Menggunakan 'HomeActivity.this' sebagai konteks
                        .setTitle("Konfirmasi Penghapusan")
                        .setMessage("Apakah Anda yakin ingin menghapus catatan ini?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Menghapus catatan dari database
                                dbHelper.deleteNote(noteToDelete);
                                // Memuat ulang catatan
                                loadNotes();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Tidak melakukan apa-apa jika pengguna memilih "No"
                                dialog.dismiss();
                            }
                        })
                        .show();

                return true; // Mengembalikan true untuk menandakan bahwa klik panjang telah ditangani
            }
        });


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i,
                                          int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int
                    i1, int i2) {
                searchNotes(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }
    private void loadNotes() {
        notesList = dbHelper.getAllNotes();
        titlesList.clear();
        for (Note note : notesList) {
            titlesList.add(note.getTitle()+ "\n" + note.getDate());
        }
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, titlesList);
        listView.setAdapter(adapter);
    }

    private void searchNotes(String keyword) {
        notesList = dbHelper.searchNotes(keyword);
        titlesList.clear();
        for (Note note : notesList) {
            titlesList.add(note.getTitle() + "\n" + note.getDate()); // Menambahkan tanggal ke dalam list
        }
        adapter.notifyDataSetChanged();
    }

}