package com.example.splashscreen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class AddEditNote extends AppCompatActivity {

    private EditText etTitle, etContent;
    private Button btnSave, btnBack;
    private DatabaseHelper0 dbHelper;
    private Note note;
    private boolean isEdit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        dbHelper = new DatabaseHelper0(this);
        if (getIntent().hasExtra("note_id")) {
            int noteId = getIntent().getIntExtra("note_id", -1);
            note = dbHelper.getNote(noteId);
            if (note != null) {
                etTitle.setText(note.getTitle());
                etContent.setText(note.getContent());
                isEdit = true;
            }
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString().trim();
                String content = etContent.getText().toString().trim();
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                        Locale.getDefault()).format(new Date());
                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(AddEditNote.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isEdit) {
                    note.setTitle(title);
                    note.setContent(content);
                    note.setDate(date);
                    dbHelper.updateNote(note);

                } else {
                    note = new Note();
                    note.setTitle(title);
                    note.setContent(content);
                    note.setDate(date);
                    dbHelper.addNote(note);

                }
                finish();
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

    }
}