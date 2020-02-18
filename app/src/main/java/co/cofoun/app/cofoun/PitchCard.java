package co.cofoun.app.cofoun;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.shape.RoundedCornerTreatment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;

import co.cofoun.app.cofoun.models.Profile;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

@Layout(R.layout.pitch_card_view)
public class PitchCard {

    @View(R.id.mainPitchCardView)
    private CardView pitchCardView;
    @View(R.id.profileNameTxt)
    private TextView profileNameText;
    @View(R.id.profileDescriptionTxt)
    private TextView profileDescriptionText;
    @View(R.id.pitchTitleTxt)
    private TextView pitchTitleText;
    @View(R.id.pitchContentTxt)
    private TextView pitchContentText;
    @View(R.id.profileImageView)
    private ImageView profileImageView;
    @View(R.id.pitchNumberTxt)
    private TextView pitchNumberTxt;

    private Profile mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;
    private String mCounter;
    private LinearLayout mButtonsContainer;
    private LinearLayout mCreateNewViewLikesContainer;

    public PitchCard(Context context, Profile profile, SwipePlaceHolderView swipeView, String counterTxt, LinearLayout buttonsContainer, LinearLayout createNewViewLikesContainer){
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
        mCounter = counterTxt;
        mButtonsContainer = buttonsContainer;
        mCreateNewViewLikesContainer = createNewViewLikesContainer;

        /*--------------------------------------*
         *              SETUP LISTENER          *
         *--------------------------------------*/

        mSwipeView.addItemRemoveListener(new ItemRemovedListener() {
            @Override
            public void onItemRemoved(int count) {
                Log.d("EVENT","onItemRemoved - item count: " + count);
                if(count==0){
                    // hide all
                    mButtonsContainer.setVisibility(android.view.View.INVISIBLE);
                    mCreateNewViewLikesContainer.setVisibility(android.view.View.VISIBLE);
                }
            }
        });
    }

    @Resolve
    private void  onResolved(){
        mContext.getResources().getDimensionPixelSize(R.dimen.border_radius);
        Glide.with(mContext)
                .load(mProfile.getAvatar())
                .apply(RequestOptions.circleCropTransform())
                .into(profileImageView);
        profileNameText.setText(mProfile.getName());
        profileDescriptionText.setText(mProfile.getDescription());
        pitchTitleText.setText(mProfile.getTitle());
        pitchContentText.setText(mProfile.getContent());
        pitchNumberTxt.setText(mCounter);
    }

    @SwipeOut
    private void onSwipeOut(){
        Log.d("EVENT", "onSwipeOut");
        pitchCardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorTextLight));
        mSwipeView.addView(this);
    }
    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
        pitchCardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorTextLight));
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
        pitchCardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorTextLight));

    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
        pitchCardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorTextLight));

    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT","onSwipeOutState");
        pitchCardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorTextGray));
    }
}
