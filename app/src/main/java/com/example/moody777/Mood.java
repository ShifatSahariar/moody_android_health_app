package com.example.moody777;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.daprlabs.cardstack.SwipeDeck;
import com.example.moody777.R;
import com.example.moody777.SelfTest.Question;
import com.example.moody777.SelfTest.QuestionTableInfo;
import com.example.moody777.SelfTest.analysePage;
import com.example.moody777.SleepTableInfo;
import com.example.moody777.sqlite_layer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Mood extends AppCompatActivity {
    private static final String TAG = "Mood";
    private SwipeDeck cardStack;
    private Context context = this;
    Question questions;
    private SwipeDeckAdapter adapter;
    private ArrayList<Question> testData;
    //private ArrayList<Integer> finalData;
    private ArrayList<Integer> firstThreeQuestions;
    private ArrayList<Integer> secondSixQuestions;
    private ArrayList<Integer> lastOneQuestion;
    private ArrayList<String> totalQuestionsForDB;
    private String finalMessegeToShow;
    private String messegeHeadline;
    private int imageId;
    sqlite_layer msqliteLayer;
    SQLiteDatabase questionDatabse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_deck);
        this.setTitle("Self Test");
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        cardStack.setHardwareAccelerationEnabled(true);
        //finalData =new ArrayList<Integer>();
        firstThreeQuestions =new ArrayList<Integer>();
        secondSixQuestions =new ArrayList<Integer>();
        lastOneQuestion =new ArrayList<Integer>();
        totalQuestionsForDB = new ArrayList<String>();
        msqliteLayer = new sqlite_layer(this);


        testData = new ArrayList<>();
        testData.add(new Question(0,"Have you been feeling depressed more often in the past 2 weeks?",R.drawable.depressed));
        testData.add(new Question(1,"Do you suffer from lack of interest and / or joylessness?",R.drawable.offmood));
        testData.add(new Question(2,"Do you suffer from tiredness and / or restlessness?",R.drawable.down2));

        testData.add(new Question(3,"Do you suffer from a lack of or reduced self-esteem and / or self-confidence?",R.drawable.lowconfidence));
        testData.add(new Question(4,"Has your ability to concentrate decreased and/or are you unsure when making decisions and pondering a lot?",R.drawable.confused));
        testData.add(new Question(5,"Are you dealing with strong feelings of guilt and / or increased self-criticism?",R.drawable.selfcriticism));
        testData.add(new Question(6,"Do you see the future pessimistic and / or are you hopeless?",R.drawable.sad));
        testData.add(new Question(7,"Do you suffer from persistent problems falling asleep or staying asleep?",R.drawable.sleepdisorder));
        testData.add(new Question(8,"Is your appetite decreased?",R.drawable.appetitedecreased));

        testData.add(new Question(9,"Do you have thoughts of death and / or are you in deep despair?",R.drawable.suicide));


        adapter = new SwipeDeckAdapter(testData, this);
        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
               /* Toast myToast = Toast.makeText(context, "I'm a toast!"+ position, Toast.LENGTH_LONG);
                myToast.show();*/
            }

            @Override
            public void cardSwipedRight(int position) {
                //finalData.add(testData.get(position));
                if (position == 0 || position == 1  || position == 2) {
                    firstThreeQuestions.add(testData.get(position).getQuestionID());

                }

                else if (position == 3 || position == 4  || position == 5 || position == 6  || position == 7 || position == 8 ){
                    secondSixQuestions.add(testData.get(position).getQuestionID());

                }

                else {
                    lastOneQuestion.add(testData.get(position).getQuestionID());
                }
                totalQuestionsForDB.add(testData.get(position).getQuestion());




                //finalData.add(testData.get(position).getQuestionID());

                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);

                /*Toast myToast = Toast.makeText(context, "Position "+ position, Toast.LENGTH_LONG);
                myToast.show();*/
            }


            @Override
            public void cardsDepleted() {


                Log.i("MainActivity", "no more cards");
               /* Toast myToast = Toast.makeText(context, "no more cards", Toast.LENGTH_LONG);
                myToast.show();*/
                Intent intent = new Intent(context, analysePage.class);

                //for (int i= 0 ; i< finalData.size();i++){

                String messegeNormalConditoion = "Your Provided information does not indicate the presence of a depressive illness. " +
                        "If you still feel depressed, please make an appointment with your personal doctor and share your symptoms. ";
                String messegeMildCondition = "You may have a mild depressive disorder. " +
                        "It is recommended that you speak openly about your symptoms to a doctor or psychotherapist.";
                //messege = "FirstArray " + firstThreeQuestions.size() + "second " + secondSixQuestions.size() + "Third " + lastOneQuestion.size();
                //}
                String messegeDipressiveCondition = "Your Information indicates the existence of a depressive illness. " +
                        "We would like to ask you to contact a doctor of your choice as soon as possible and discuss with him how to proceed.";
                String messegeSeriousCondition="Please see a doctor immediately. " +
                        "Your result suggest that you suffer from a severe depressive disorder. ";
                String messegeSuicideCondition = "You also indicated that you had thoughts of death If your thoughts increase, please seek professional help immediately!";

                /* // NORMAL CONDITION // */
                if(firstThreeQuestions.size() == 0 && secondSixQuestions.size() <= 2){
                    finalMessegeToShow = messegeNormalConditoion ;
                    messegeHeadline = "No Depression";
                    imageId = R.drawable.happydoctor;
                }
                else if (firstThreeQuestions.size() == 1 && secondSixQuestions.size() <= 1){
                    finalMessegeToShow = messegeNormalConditoion ;
                    messegeHeadline = "No Depression";
                    imageId = R.drawable.happydoctor;
                }
                /* // MILD CONDITION // */
                else if (firstThreeQuestions.size() == 0
                        &&
                        (secondSixQuestions.size() >= 3 && secondSixQuestions.size() <= 4)){
                    finalMessegeToShow = messegeMildCondition ;
                    messegeHeadline = "Slight Depression";
                    imageId = R.drawable.milddoctor;
                }
                else if (firstThreeQuestions.size() == 1
                        &&
                        (secondSixQuestions.size() >= 2 && secondSixQuestions.size() <= 3)){
                    finalMessegeToShow = messegeMildCondition ;
                    messegeHeadline = "Slight Depression";
                    imageId = R.drawable.milddoctor;
                }
                else if (firstThreeQuestions.size() == 2 && secondSixQuestions.size() <= 1){
                    finalMessegeToShow = messegeMildCondition ;
                    messegeHeadline = "Slight Depression";
                    imageId = R.drawable.milddoctor;
                }
                /* // MILD2 CONDITION // */

                else if (firstThreeQuestions.size() == 0
                        &&
                        (secondSixQuestions.size() >= 5 && secondSixQuestions.size() <= 6)){
                    finalMessegeToShow = messegeDipressiveCondition ;
                    messegeHeadline = "Depression";
                    imageId = R.drawable.milddoctor;
                }
                else if (firstThreeQuestions.size() == 1
                        &&
                        (secondSixQuestions.size() >= 4 && secondSixQuestions.size() <= 6)){
                    finalMessegeToShow = messegeDipressiveCondition ;
                    messegeHeadline = "Depression";
                    imageId = R.drawable.milddoctor;
                }
                else if (firstThreeQuestions.size() == 2 && secondSixQuestions.size() <= 2){
                    finalMessegeToShow = messegeDipressiveCondition ;
                    messegeHeadline = "Depression";
                    imageId = R.drawable.milddoctor;
                }
                else if (firstThreeQuestions.size() == 3 && secondSixQuestions.size() <= 1){
                    finalMessegeToShow = messegeDipressiveCondition ;
                    messegeHeadline = "Depression";
                    imageId = R.drawable.milddoctor;
                }

                /* // MILD3 CONDITION // */

                else if (firstThreeQuestions.size() == 2
                        &&
                        (secondSixQuestions.size() >= 3 && secondSixQuestions.size() <= 6)){
                    finalMessegeToShow = messegeSeriousCondition ;
                    messegeHeadline = "Serious Depression";
                    imageId = R.drawable.milddoctor;
                }

                else if (firstThreeQuestions.size() == 3
                        &&
                        (secondSixQuestions.size() >= 2 && secondSixQuestions.size() <= 6)){
                    finalMessegeToShow = messegeSeriousCondition ;
                    messegeHeadline = "Serious Depression";
                    imageId = R.drawable.milddoctor;

                }

                if (lastOneQuestion.size() == 1){
                    messegeHeadline = "Thoughts of death";
                    finalMessegeToShow = finalMessegeToShow + messegeSuicideCondition ;

                    imageId = R.drawable.milddoctor;

                }


                        ArrayList<String> testFields = new ArrayList<String>();
                        testFields.add(QuestionTableInfo.InsertQuestionTable.SYMOTOM1);
                        testFields.add(QuestionTableInfo.InsertQuestionTable.SYMOTOM2);
                        testFields.add(QuestionTableInfo.InsertQuestionTable.SYMOTOM3);
                        testFields.add(QuestionTableInfo.InsertQuestionTable.SYMOTOM4);
                        testFields.add(QuestionTableInfo.InsertQuestionTable.SYMOTOM5);
                        testFields.add(QuestionTableInfo.InsertQuestionTable.SYMOTOM6);
                        testFields.add(QuestionTableInfo.InsertQuestionTable.SYMOTOM7);
                        testFields.add(QuestionTableInfo.InsertQuestionTable.SYMOTOM8);
                        testFields.add(QuestionTableInfo.InsertQuestionTable.SYMOTOM9);
                        testFields.add(QuestionTableInfo.InsertQuestionTable.SYMOTOM10);


                //msqliteLayer = new sqlite_layer(this);
                questionDatabse = msqliteLayer.getWritableDatabase();
                // Create a ContentValues object where column names are the keys,
                // and Toto's pet attributes are the values.

                ContentValues values = new ContentValues();
                for (int i = 0; i<totalQuestionsForDB.size(); i++){
                    values.put(testFields.get(i), totalQuestionsForDB.get(i));
                }



                long newRowId = questionDatabse.insert(QuestionTableInfo.InsertQuestionTable.TABLE_NAME, null, values);
                if (newRowId == -1) {
                    // If the row ID is -1, then there was an error with insertion.
                    //Toast.makeText( analysePage.class, "Check Information !", Toast.LENGTH_SHORT).show();
                }


                else {

                    // Otherwise, the insertion was successful and we can display a toast with the row ID.

                    //Toast.makeText(this, "Data Successfully Saved" + tempDate+ newRowId, Toast.LENGTH_SHORT).show();
                    //Toast toast = Toast.makeText(this, "I am custom Toast!", Toast.LENGTH_LONG);


/* And now you can get the TextView of the default View of the Toast. */

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast,
                            (ViewGroup) findViewById(R.id.toast_layout_root));

                    ImageView image = (ImageView) layout.findViewById(R.id.image);
                    image.setImageResource(R.drawable.checkmark);
                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setText("Data Successfully Saved");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();



                }


                //int symptomPercentage =0 ;
                //for (int i=0 ; i< finalData.size(); i++){
                //   symptomPercentage += 10;

                //}
                //String perCent = "You have " + symptomPercentage + "%" + " symptoms" ;
                /*if (finalData.contains(0) && finalData.contains(2) && finalData.contains(3) && finalData.contains(4) && finalData.contains(5)){
                    messege = "You have Mania Problem.";
                }
                if (finalData.contains(1) && finalData.contains(2) && finalData.contains(3) && finalData.contains(4)){
                    messege = "You have Mania Problem.";
                }
                else if (finalData.contains(6)&& finalData.contains(7)&& finalData.contains(8)&& finalData.contains(9)&& finalData.contains(10) ){
                    messege = "You have depression";
                }
                else messege = "You Have major Symptoms of Mania ";*/
                //intent.putExtra("Key",finalData);

                intent.putExtra("messege",finalMessegeToShow);
                intent.putExtra("headline",messegeHeadline);
                intent.putExtra("photo",imageId);

                startActivity(intent);
            }

            @Override
            public void cardActionDown() {
                Log.i(TAG, "cardActionDown");
            }

            @Override
            public void cardActionUp() {
                Log.i(TAG, "cardActionUp");
            }

        });
        cardStack.setLeftImage( R.id.left_image);
        cardStack.setRightImage(R.id.right_image);

        FloatingActionButton rejected = (FloatingActionButton) findViewById(R.id.rejected);
        rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardLeft(360);

            }
        });
        FloatingActionButton accepted = (FloatingActionButton) findViewById(R.id.accepted);
        accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardRight(360);
            }
        });

       /* Button btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testData.add("a sample string.");
//                ArrayList<String> newData = new ArrayList<>();
//                newData.add("some new data");
//                newData.add("some new data");
//                newData.add("some new data");
//                newData.add("some new data");
//
//                SwipeDeckAdapter adapter = new SwipeDeckAdapter(newData, context);
//                cardStack.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_swipe_deck, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent newIntent = new Intent(this, com.example.moody777.Menu.class);
            startActivity(newIntent);
        }

        return super.onOptionsItemSelected(item);
    }
    /* Adapter Code Section *////////////////////////////////


    public class SwipeDeckAdapter extends BaseAdapter {

        private List<Question> data;
        private Context context;
        Question question;

        public SwipeDeckAdapter(List<Question> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = getLayoutInflater();
                // normally use a viewholder
                v = inflater.inflate(R.layout.test_card2, parent, false);
            }
            question = data.get(position);
            //((TextView) v.findViewById(R.id.textView2)).setText(data.get(position));
            ImageView imageView = (ImageView) v.findViewById(R.id.offer_image);
            imageView.setImageResource(question.getImagePath());
            // Picasso.with(context).load(question.getImagePath()).fit().centerCrop().into(imageView);
            TextView textView = (TextView) v.findViewById(R.id.sample_text);
            //TextView description = (TextView) v.findViewById(R.id.description);
            String ques = question.getQuestion();
            // String des = question.getDescription();

            textView.setText(ques);
            //description.setText(des);
            textView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.animation));


           /* v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Layer type: ", Integer.toString(v.getLayerType()));
                    Log.i("Hwardware Accel type:", Integer.toString(View.LAYER_TYPE_HARDWARE));
                    Intent i = new Intent(v.getContext(), BlankActivity.class);
                    v.getContext().startActivity(i);
                }
            });*/
            return v;
        }

    }
}
