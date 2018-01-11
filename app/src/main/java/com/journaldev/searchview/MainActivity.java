package com.journaldev.searchview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CallBackk  {

    private static final String LAST_ID = "LAST_ID";
    private static final String Locale_KeyValue = "Saved Locale";
    private  final String Language = "ru";
    private  final long ID_Last_Animation = 0;
    private CircularProgressView progressView;


    public static  String ID = "id";
    public static  String TITLE = "title";
    public static  String TAG = "tag";
    public static  String TYPE = "type";
    public static  String LINK_ICON = "linkIcon";
    public static  String LINK_SOURCE = "linkSource";
    public static  String LINK_SOUND = "linkSound";
    public static  String PRICE = "price";
    public static  String DATE = "date";
    public static  String STATE_DOWNLOAD = "stateDownload";

    List<String> arrayList= new ArrayList<>();
    ListView listView;

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private Locale locale;
    private Dialog dialog;

    private ImageView imageViewArm;
    private ImageView imageViewRus;
    private ImageView imageViewEng;



    private ListAdapter listAdapter;
    private  List<StoreModel> items;
    private FirebaseFirestore firestore;
    private DocumentSnapshot documentSnapshot;
    private Query query;
    private DBHelper db;
    private SharedPreferences sPref;

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isFirstTime();

        progressView = (CircularProgressView) findViewById(R.id.progress_view);
        progressView.startAnimation();
        listView = (ListView) findViewById(R.id.list_view);
        items = new ArrayList();
        firestore = FirebaseFirestore.getInstance();
        db = new  DBHelper(this);

         firestore.collection("Animation")
                        .orderBy(ID)
                        .startAt(getLastID(LAST_ID)+1)
                        .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            long i=0;
                                for (DocumentSnapshot document : task.getResult()) {
                                    putDataInDB(document,0);
                                    if(i == task.getResult().size()-1){
                                        setLastID(LAST_ID,document.getLong(ID));
                                    }
                                    i++;
                                }
                                progressView.stopAnimation();
                                progressView.setVisibility(View.GONE);
                        }
                        RelativeLayout rl = (RelativeLayout)findViewById(R.id.rel_layout);
                        rl.setBackgroundColor(Color.WHITE);
                        Cursor c = db.query(MainActivity.ID);
                        listAdapter = new ListAdapter(MainActivity.this, putItemsInArrayList(c));
                        listView.setAdapter(listAdapter);
                    }
                });







        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        chooseLanguageDialog();
//        isFirstTime();

        /*imageViewArm =(ImageView) dialog.findViewById(R.id.img_arm);
        imageViewRus = (ImageView)dialog.findViewById(R.id.img_rus);
        imageViewEng =(ImageView) dialog.findViewById(R.id.img_eng);

        sharedPreferences = getSharedPreferences(Locale_Preference, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        imageViewArm.setOnClickListener(this);
        imageViewRus.setOnClickListener(this);
        imageViewEng.setOnClickListener(this);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_button).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    Cursor c = db.query(MainActivity.ID);
                    listAdapter = new ListAdapter(MainActivity.this, putItemsInArrayList(c));
                    listView.setAdapter(listAdapter);
                } else {
                  //  if (!newText.equals("~")) {
                        Cursor c = db.getAnimationListByKeyword(newText);
                        listAdapter = new ListAdapter(MainActivity.this, putItemsInArrayList(c));
                        listView.setAdapter(listAdapter);
                  //  }
                }
                return false;
            }


        });

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Toast.makeText(this, "This is Second item", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.share:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,"hashvenq es im play marketi linkn e");
                intent.setType("text/plane");
                startActivity(Intent.createChooser(intent,"Choose app to share link or any text"));
              //  Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.information:
                new MaterialDialog.Builder(this)
                        .title("Attention")
                        .titleColorRes(R.color.colorPrimary)
                        .canceledOnTouchOutside(false)
                        .buttonRippleColor(R.color.colorAttentionDialogBackground)
                        .content("Your Activities need to inherit the AppCompat themes in order to work correctly with this library.")
                        .positiveText("Agree")
                        .show();
                Toast.makeText(this, "This is Fored item", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
  /*  public void chooseLanguageDialog(){
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.language_dialog);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,700);
    }
    private void setLangRecreate(String langval) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        locale = new Locale(langval);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }

    public void changeLocale(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        locale = new Locale(lang);//Set Selected Locale
        saveLocale(lang);//Save the selected locale
        Locale.setDefault(locale);//set new locale as default
        Configuration config = new Configuration();//get Configuration
        config.locale = locale;//set config locale as selected locale
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());//Update the config
        // updateTexts();//Update texts according to locale
    }

    public void saveLocale(String lang) {
        editor.putString(Locale_KeyValue, lang);
        editor.commit();
    }

    //Get locale method in preferences
    public void loadLocale() {
        String language = sharedPreferences.getString(Locale_KeyValue, "");
        changeLocale(language);
    }


    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            //show dialog if app never launch
            dialog.show();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_rus:
                setLangRecreate("ru");
                changeLocale("ru");
                break;
            case R.id.img_arm:
                setLangRecreate("hy");
                changeLocale("hy");
                break;
            case R.id.img_eng:
                setLangRecreate("eng");
                changeLocale("eng");
                break;
        }

    }*/

    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            //show dialog if app never launch
            //dialog.show();
            setLastID(LAST_ID,0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }

    private void putItemsInArrayList (DocumentSnapshot documentSnapshot,int stateDownload){
        String title = documentSnapshot.getString(TITLE);
        String linkIcon = documentSnapshot.getString(LINK_ICON);
        String type = documentSnapshot.getString(TYPE);
        String price = documentSnapshot.getString(PRICE);
        items.add(new StoreModel(linkIcon,title,type,price,stateDownload));
    }


    private List<StoreModel> putItemsInArrayList (Cursor c){
        List<StoreModel> items = new ArrayList();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    int titleIndex = c.getColumnIndex(TITLE);
                    int linkIconIndex = c.getColumnIndex(LINK_ICON);
                    int typeIndex = c.getColumnIndex(TYPE);
                    int priceIndex = c.getColumnIndex(PRICE);
                    int stateDownloadIndex = c.getColumnIndex(STATE_DOWNLOAD);
                    String linkIcon = c.getString(linkIconIndex);
                    String type = c.getString(typeIndex);
                    String title = c.getString(titleIndex);
                    String price = c.getString(priceIndex);
                    int stateDownload = c.getInt(stateDownloadIndex);
                    items.add(new StoreModel(title, type, linkIcon, price, stateDownload));
                } while (c.moveToNext());
            }
            c.close();

        }
        return  items;
    }

//Firestore-ic stacac tvyalner@ texadrum enq local db-um
    private long putDataInDB (DocumentSnapshot documentSnapshot,int stateDownload) {
        long id = documentSnapshot.getLong(ID);
        Map<String,String> temp = (Map<String, String>) documentSnapshot.getData().get(TITLE);
        String title = temp.get(Language);
        String type = documentSnapshot.getString(TYPE);
        String tag = documentSnapshot.getString(TAG);
        String linkIcon = documentSnapshot.getString(LINK_ICON);
        String linkSource = documentSnapshot.getString(LINK_SOURCE);
        String linkSound = documentSnapshot.getString(LINK_SOUND);
        String price = documentSnapshot.getString(PRICE);
        String date = documentSnapshot.getString(DATE);
        return db.insert(new StoreModel(id, title, type, tag, linkIcon, linkSource, linkSound, price, date, stateDownload));
    }


    public String getDefaultLang(String paramString) {

        sPref = getSharedPreferences(paramString, 0);
        return sPref.getString(paramString, "");
    }

    public void setDefaultLang(String name, String data) {
        sPref = getSharedPreferences(name, MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(name, data);
        editor.commit();
    }

    public long getLastID(String paramString) {

        sPref = getSharedPreferences(paramString, 0);
        return sPref.getLong(paramString, 0);
    }

    public void setLastID(String name, long data) {
        sPref = getSharedPreferences(name, MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putLong(name, data);
        editor.commit();
    }

    @Override
    public void clickInItem(int position) {
        Log.i("sdsdsds",position+"");
    }
}
