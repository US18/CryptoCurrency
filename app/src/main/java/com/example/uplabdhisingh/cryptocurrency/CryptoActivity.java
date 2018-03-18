package com.example.uplabdhisingh.cryptocurrency;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uplabdhisingh.cryptocurrency.adapter.CryptoAdapter;
import com.example.uplabdhisingh.cryptocurrency.model.CryptoDetails;
import com.example.uplabdhisingh.cryptocurrency.rest.ApiClient;
import com.example.uplabdhisingh.cryptocurrency.rest.ApiInterface;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CryptoActivity extends AppCompatActivity
{

    public static RecyclerView cryptoRecyclerView;
    public static TextView failedConnTextView;
    LinearLayoutManager layoutManager;
    private final String TAG = CryptoActivity.class.getSimpleName();
    List<CryptoDetails> details;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;

    public static final String ANONYMOUS = "anonymous";
    private static final int RC_SIGN_IN = 1;
    private String mUsername;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    CryptoAdapter cryptoAdapter;

    public static void showErrorMessageView()
    {
        cryptoRecyclerView.setVisibility(View.INVISIBLE);
        failedConnTextView.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mMessageDatabaseReference=mFirebaseDatabase.getReference().child("users");

        mFirebaseAuth=FirebaseAuth.getInstance();

        mUsername = ANONYMOUS;

        cryptoRecyclerView=(RecyclerView) findViewById(R.id.rv_currencies);
        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        cryptoRecyclerView.setHasFixedSize(true);
        cryptoRecyclerView.setLayoutManager(layoutManager);
        cryptoAdapter = new CryptoAdapter(getApplicationContext(),details,R.layout.currencies_list);
        cryptoRecyclerView.setAdapter(cryptoAdapter);

        failedConnTextView = (TextView) findViewById(R.id.tv_error_message);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<CryptoDetails>> call = apiService.getCryptoDetails();
        call.enqueue(new Callback<List<CryptoDetails>>()
        {
            @Override
            public void onResponse(Call<List<CryptoDetails>> call, Response<List<CryptoDetails>> response)
            {
                    details = response.body();
                    cryptoAdapter.setData(details);
                    cryptoAdapter.notifyDataSetChanged();

                    Log.d(TAG,"No. of data received  :  " + details.get(1).getName());
            }
            @Override
            public void onFailure(Call<List<CryptoDetails>> call, Throwable t)
            {
                showErrorMessageView();
            }
        });

        mAuthStateListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if( user != null )
                {
                    // Toast.makeText(MainActivity.this, "Welcome to Friendly Chat ! You are now Signed In.", Toast.LENGTH_LONG).show();

                    //onSignedInInitialize(user.getDisplayName());

                } else
                {
                   // onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build()
                                    ))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN)
        {
            if(resultCode == RESULT_OK)
            {
                Toast.makeText(this, "Signed In!", Toast.LENGTH_SHORT).show();
            } else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "Sign In Cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.item_sign_out:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sign_out,menu);
        return true;
    }
}
