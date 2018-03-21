package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String INGREDIENTS_DELIMITER = "\n";
    private static final String AKA_DELIMITER = ", ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if(intent == null) {
            closeOnError();
        }

        int position = intent != null ? intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION) : DEFAULT_POSITION;
        if(position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if(sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView tvAlsoKnown = findViewById(R.id.also_known_tv);
        TextView tvIngredients = findViewById(R.id.ingredients_tv);
        TextView tvOrigTextView = findViewById(R.id.origin_tv);
        TextView tvDescription = findViewById(R.id.description_tv);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvAlsoKnown.setText(String.join(AKA_DELIMITER, sandwich.getAlsoKnownAs()));
        } else {
            StringBuilder alsoKnownString = new StringBuilder();
            for(String ak : sandwich.getAlsoKnownAs()) {
                alsoKnownString.append(AKA_DELIMITER).append(ak);
            }
            tvAlsoKnown.setText(alsoKnownString.replace(0, AKA_DELIMITER.length(), "").toString());
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvIngredients.setText(String.join(INGREDIENTS_DELIMITER, sandwich.getIngredients()));
        } else {
            StringBuilder ingredientsString = new StringBuilder();
            for(String i : sandwich.getIngredients()) {
                ingredientsString.append(INGREDIENTS_DELIMITER).append(i);
            }
            tvIngredients.setText(ingredientsString.replace(0, INGREDIENTS_DELIMITER.length(), "").toString());
        }

        tvOrigTextView.setText(sandwich.getPlaceOfOrigin());
        tvDescription.setText(sandwich.getDescription());
    }
}
