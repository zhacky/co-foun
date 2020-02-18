package co.cofoun.app.cofoun;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.Date;
import java.util.List;

import co.cofoun.app.cofoun.helpers.Utils;
import co.cofoun.app.cofoun.models.Profile;

public class MainActivity extends AppCompatActivity {

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    private int profileCount;
    private int profileCurrentNumber;
    private String counterTxt;
    private LinearLayout mButtonsContainer;
    private LinearLayout mCreateNewViewLikesContainer;
    private TextView timerTextView;
    // click listener for tab navigation
    private BottomNavigationView.OnNavigationItemSelectedListener mNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_account:
                    mSwipeView.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_ideas:
                    mSwipeView.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_pitches:
                    mSwipeView.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_messages:
                    mSwipeView.setVisibility(View.INVISIBLE);
                    return true;
            }
            return false;
        }
    };

        //-------------------TIMER-------------------//
    long startTime =  0;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = startTime - System.currentTimeMillis();
            int seconds = (int)(millis/1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;
            hours = hours % 24;
            minutes = minutes % 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format("%02d:%02d:%02d", hours, minutes,seconds));
            timerHandler.postDelayed(this, 500);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*--------------------------------------*
         *              Setup Timer             *
         *--------------------------------------*/
         timerTextView = (TextView) findViewById(R.id.timerTextView);
         startTime = DateUtils.DAY_IN_MILLIS + System.currentTimeMillis();
         timerHandler.postDelayed(timerRunnable, 2000);
        /*--------------------------------------*
         *              Setup Navigation        *
         *--------------------------------------*/

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_pitches); // default to pitches
        navigation.setOnNavigationItemSelectedListener(mNavigationItemSelectedListener);
        Utils.showBadge(this,navigation,R.id.navigation_ideas,"1");
        Utils.showBadge(this,navigation,R.id.navigation_messages,"1");


        /*--------------------------------------*
         *            Setup SwipeView           *
         *--------------------------------------*/

        mSwipeView = (SwipePlaceHolderView) findViewById(R.id.swipeView);
        mContext = getApplicationContext();
        mButtonsContainer = (LinearLayout) findViewById(R.id.buttonsContainer);
        mCreateNewViewLikesContainer = (LinearLayout) findViewById(R.id.createNewViewLikesBtn);

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(-20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.pitch_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.pitch_swipe_out_msg_view));
        List<Profile> profiles = Utils.loadProfiles(this.getApplicationContext());
        profileCount = profiles.size();
        for (Profile profile :
                profiles) {
            profileCurrentNumber = profiles.indexOf(profile) + 1;
            counterTxt = profileCurrentNumber + "/" + profileCount;
            PitchCard card = new PitchCard(mContext, profile, mSwipeView, counterTxt, mButtonsContainer, mCreateNewViewLikesContainer );
            mSwipeView.addView(card);
        } // each profile

        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });
    }
}
