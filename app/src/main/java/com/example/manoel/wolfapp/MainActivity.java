package com.example.manoel.wolfapp;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.text.format.DateFormat;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.builders.Actions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseListAdapter;

import static com.google.firebase.database.FirebaseDatabase.*;


public class MainActivity extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<ChatMenssagem> adapter;
    private DatabaseReference ref;
    RelativeLayout activity_main;
    FloatingActionButton fab;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_sign_out)
        {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(activity_main," Sua sessão foi encerrada!", Snackbar.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_REQUEST_CODE)
        {
            Snackbar.make(activity_main,"Logado com Sucesso! Bem Vindo!!!", Snackbar.LENGTH_SHORT).show();
            displayChatMenssagem();
        }
        else
        {
            Snackbar.make(activity_main,"Erro ao logar! Tente novamente, Mais Tarde!!!", Snackbar.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity_main = (RelativeLayout)findViewById(R.id.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);
                getInstance().getReference().push().setValue(new ChatMenssagem(input.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                        input.setText("");
            }
        });

        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
        }
        else
        {
            Snackbar.make(activity_main,"Bem Vindo "+FirebaseAuth.getInstance().getCurrentUser().getEmail(),Snackbar.LENGTH_SHORT);
        }
        
        displayChatMenssagem();
    }


    private void displayChatMenssagem() {

        ref = FirebaseDatabase.getInstance().getReference();

        ListView listaDeMenssagens = (ListView) findViewById(R.id.list_of_messagem);
        FirebaseListAdapter<ChatMenssagem> adapter = new FirebaseListAdapter<ChatMenssagem>(this, ChatMenssagem.class,
                R.layout.list_item, ref)
        {
            @Override
            protected void populateView(View v, ChatMenssagem model, int position) {
                TextView menssagemText, menssagemUser, menssagemTime;
                menssagemText = (TextView) findViewById(R.id.menssagem_text);
                menssagemUser = (TextView) findViewById(R.id.menssagem_user);
                menssagemTime = (TextView) findViewById(R.id.menssagem_time);

                menssagemText.setText(model.getMenssagemText());
                menssagemUser.setText(model.getMenssagemUser());
                menssagemTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMenssagemTime()));

            }
        };
        listaDeMenssagens.setAdapter(adapter);

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        return Actions.newView("Main", "http://[ENTER-YOUR-URL-HERE]");
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().start(getIndexApiAction());
    }

    @Override
    public void onStop() {

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().end(getIndexApiAction());
        super.onStop();
    }
}
