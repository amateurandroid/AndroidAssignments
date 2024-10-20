package com.example.androidassignments;
import static android.widget.Toast.LENGTH_SHORT;

import com.example.androidassignments.R;
import android.content.DialogInterface; // Import DialogInterface for the dialog
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog; // Import AlertDialog
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.androidassignments.databinding.ActivityTestToolbarBinding;

public class TestToolbar extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
Log.d("asd", String.valueOf(R.id.action_one));
Log.d("asd", String.valueOf(R.id.action_two));
Log.d("asd",String.valueOf(R.id.about));
        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();

        switch (id) {
            case 2131230792:
                Log.d("Toolbar", "Choice 1 selected");
                Snackbar.make(findViewById(R.id.action_one),"Selected Item 1", LENGTH_SHORT).show();
                break;

            case 2131230795:
                Log.d("Toolbar", "Choice 2 selected");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TestToolbar.this);

                dialogBuilder.setTitle("Send a Message");

                        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(TestToolbar.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
                break;

            case 2131230794:
                showCustomDialog();
                Log.d("Toolbar", "Choice 3 selected");
                break;

                case 2131230736:
                    Toast.makeText(this, "Version 1.0, by Ishaan Dhillon", LENGTH_SHORT).show();
                break;

            default:
                return super.onOptionsItemSelected(mi);
        }

        return true;
    }



    private void showCustomDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom, null);
        dialogBuilder.setView(dialogView);

        EditText editTextMessage = dialogView.findViewById(R.id.editTextMessage);
        ImageView dialogIcon = dialogView.findViewById(R.id.dialog_icon);

        dialogBuilder.setTitle("Send a Message")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String message = editTextMessage.getText().toString();
                        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

}