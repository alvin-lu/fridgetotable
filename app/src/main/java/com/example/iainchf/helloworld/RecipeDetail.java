package com.example.iainchf.helloworld;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RecipeDetail extends AppCompatActivity {

    private ImageView iv;
    private Bitmap bitmap;
    private TextView recipeName;
    private ListView lv;

    private String name;
    private String description;
    private String instructions;
    private String videoURL;
    private Boolean dietFood;
    private Boolean hasCaffeine;
    private Boolean glutenFree;
    private int calories;
    private String nameOfAPI;
    private ArrayList<String> ingredientList;
    private String idFromAPI;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Bundle b = getIntent().getExtras();
        name = b.getString("name");
        description = b.getString("description");
        instructions = b.getString("instructions");
        videoURL = b.getString("videoURL");
        dietFood = b.getBoolean("dietFood");
        hasCaffeine = b.getBoolean("hasCaffeine");
        glutenFree = b.getBoolean("glutenFree");
        calories = b.getInt("calories");
        nameOfAPI = b.getString("nameOfAPI");
        ingredientList = b.getStringArrayList("ingredients");
        idFromAPI = b.getString("idFromAPI");
        imageUrl = b.getString("imageUrl");

        recipeName = (TextView) findViewById(R.id.recipeName);
        recipeName.setText(name);

        lv = (ListView) findViewById(R.id.listIngredientsView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientList);
        lv.setAdapter(arrayAdapter);
        setHeightOfListView(lv);

        iv = (ImageView) findViewById(R.id.bcgImage);
        try {
            bitmap = new GetBitmapFromURL().execute(imageUrl).get();
            iv.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void goToInstructions (View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.instructions));
        startActivity(browserIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.get_recipes:
                startActivity(new Intent(RecipeDetail.this, RecipePage.class));
                break;
            case R.id.filter_recipes:
                startActivity(new Intent(RecipeDetail.this, Get_Recipes.class));
                break;
            case R.id.fridge:
                startActivity(new Intent(RecipeDetail.this, Refrigerator.class));
                break;
            case R.id.find_ingredients:
                startActivity(new Intent(RecipeDetail.this, Find_Ingredients.class));
                break;
            case R.id.cookbook:
                startActivity(new Intent(RecipeDetail.this, Cookbook.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void setHeightOfListView(ListView listView) {

        ListAdapter mAdapter = listView.getAdapter();
        int totalHeight = 0;

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += mView.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

}
