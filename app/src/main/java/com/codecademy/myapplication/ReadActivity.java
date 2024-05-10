package com.codecademy.myapplication;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity {
    private RecyclerView mrecyclerView;
    private ArrayList<SoccerItem> itemList;
    private SoccerItemAdapter madapter;
    private boolean viewRow=true;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;


    private int gridNumber=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_read);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mrecyclerView=findViewById(R.id.recyclerView);
        mrecyclerView.setLayoutManager(new GridLayoutManager(this,gridNumber));
        itemList=new ArrayList<>();

        madapter=new SoccerItemAdapter(this,itemList);

        mrecyclerView.setAdapter(madapter);

        mFirestore=FirebaseFirestore.getInstance();
        mItems=mFirestore.collection("Items");


        queryData();

    }
    private void queryData(){
        itemList.clear();
        mItems.orderBy("soccerName").limit(10).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot document:queryDocumentSnapshots){
                SoccerItem item=document.toObject(SoccerItem.class);
                itemList.add(item);
            }
            if(itemList.size()==0){
                initializeData();
                queryData();
            }
            madapter.notifyDataSetChanged();
        });
    }
    private void initializeData(){
        String [] itemsNames=getResources().getStringArray(R.array.soccer_tournament_names);
        String [] itemdetails=getResources().getStringArray(R.array.soccer_item_desc);
        String [] itemDates=getResources().getStringArray(R.array.soccer_tournament_names);
        String [] itemLocation=getResources().getStringArray(R.array.soccer_item_location);
        int []totalTeams={8,16,32};
        TypedArray itemImageResoruce=getResources().obtainTypedArray(R.array.soccer_item_img);



        for (int i = 0; i < itemsNames.length; i++) {

            int totoal=totalTeams[(int) Math.floor(Math.random()*3)];
            mItems.add(new SoccerItem(
                    itemsNames[i],
                    itemdetails[i],
                    itemLocation[i],
                    totoal, (int) Math.floor(Math.random()*totoal),
                    itemDates[i],
                    itemImageResoruce.getResourceId(i,0)));
        }
        itemImageResoruce.recycle();
        //madapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.soccer_list_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.search_bar);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                madapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        if(item.getItemId()==R.id.Logout){
            FirebaseAuth.getInstance().signOut();
            finish();
            return true;
        }else{
            if(viewRow){
                changeSpanCount(item,R.drawable.grid,1);
            }else{
                changeSpanCount(item,R.drawable.view_row,2);
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void changeSpanCount(MenuItem item,int drawable,int spanCOunt){
        viewRow=!viewRow;
        item.setIcon(drawable);
        GridLayoutManager layoutManager=(GridLayoutManager) mrecyclerView.getLayoutManager();
        layoutManager.setSpanCount(spanCOunt);
    }
}