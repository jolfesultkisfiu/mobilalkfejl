package com.codecademy.myapplication;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {
    private static final String LOG_TAG=DeleteActivity.class.getName();
    private RecyclerView mrecyclerView;
    private ArrayList<SoccerItem> itemList;
    private SoccerItemAdapter madapter;
    private boolean viewRow=true;
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mrecyclerView=findViewById(R.id.recyclerViewDelete);

        itemList=new ArrayList<>();
        mrecyclerView.setLayoutManager(new GridLayoutManager(this,1));
        madapter=new SoccerItemAdapter(this,"Delete",itemList);

        mrecyclerView.setAdapter(madapter);
        mFirestore= FirebaseFirestore.getInstance();
        mItems=mFirestore.collection("Items");


        queryData();
    }
    private void queryData(){
        itemList.clear();
        mItems.orderBy("soccerName").limit(10).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot document:queryDocumentSnapshots){
                SoccerItem item=document.toObject(SoccerItem.class);
                item.setId(document.getId());
                itemList.add(item);
            }
            /*if(itemList.size()==0){
                initializeData();
                queryData();
            }*/
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

    public void deleteItem(SoccerItem item){
        DocumentReference ref=mItems.document(item._getId());
        ref.delete().addOnSuccessListener(success->{
            Log.d(LOG_TAG,"Sikeres törlés!"+item._getId());
        })
                .addOnFailureListener(failure->{
                    Toast.makeText(this,"Bajnokságot"+item._getId()+"Nem lehet törölni.",Toast.LENGTH_LONG).show();
                });
        queryData();
    }
}