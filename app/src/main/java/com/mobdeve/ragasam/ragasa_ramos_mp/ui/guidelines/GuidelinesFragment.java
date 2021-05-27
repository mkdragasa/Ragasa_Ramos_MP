package com.mobdeve.ragasam.ragasa_ramos_mp.ui.guidelines;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.mobdeve.ragasam.ragasa_ramos_mp.R;
import com.mobdeve.ragasam.ragasa_ramos_mp.SafetyGuidelines;

public class GuidelinesFragment extends Fragment {

    Activity context;
    ImageButton fireBtn, earthquakeBtn, floodBtn;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        context = getActivity();

        View root = inflater.inflate(R.layout.fragment_guidelines, container, false);

        return root;
    }

    public void onStart() {
        super.onStart();

        fireBtn =  (ImageButton) context.findViewById(R.id.ib_fire);
        fireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SafetyGuidelines.class);
                intent.putExtra("DisasterType", "Fire");
                intent.putExtra("Tip1",
                        "Install smoke alarms on every level of your home, inside bedrooms and outside sleeping areas.");
                intent.putExtra("Tip2",
                        "Test smoke alarms every month. If they’re not working, change the batteries.");
                intent.putExtra("Tip3",
                        "Talk with all family members about a fire escape plan and practice the plan twice a year.");
                intent.putExtra("Tip4",
                                "If a fire occurs in your home, GET OUT, STAY OUT and CALL FOR HELP.");

                intent.putExtra("BeforeImage",  R.drawable.before_fire);
                intent.putExtra("BeforeInfo",  "Install the right number of smoke alarms. Test them once a month and replace the batteries at least once a year. Ensure that all household members know two ways to escape from every room of your home.");
                intent.putExtra("DuringImage",  R.drawable.during_fire);
                intent.putExtra("DuringInfo",  "Know how to safely operate a fire extinguisher Remember to GET OUT, STAY OUT and CALL 9-1-1 or your local emergency phone number. Leave all your things where they are and save yourself.");
                intent.putExtra("AfterImage",  R.drawable.after_fire);
                intent.putExtra("AfterInfo",  "Let friends and family know you’re safe. People and animals that are seriously injured or burned should be transported to professional medical or veterinary help immediately. Stay out of fire-damaged homes.");

                startActivity(intent);
            }
        });

        earthquakeBtn =  (ImageButton) context.findViewById(R.id.ib_earthquake);
        earthquakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SafetyGuidelines.class);
                intent.putExtra("DisasterType", "Earthquake");
                intent.putExtra("Tip1",
                        "Practice Drop, Cover, and Hold On with family and coworkers.");
                intent.putExtra("Tip2",
                        "Make an Emergency Plan");
                intent.putExtra("Tip3",
                        "Prepare an Emergency Kit");
                intent.putExtra("Tip4",
                        "If an earthquake occurs, Do not panic.");

                intent.putExtra("BeforeImage",  R.drawable.before_earthquake);
                intent.putExtra("BeforeInfo",  "Prepare your Emergency Bag with flashlights, battery, food, and first aid. Don't leave heavy objects on shelves (they'll fall during a quake).Study your surroundings and Learn the earthquake plan at your school or workplace.");
                intent.putExtra("DuringImage",  R.drawable.during_earthquake);
                intent.putExtra("DuringInfo",  "Stay calm! If you're indoors, stay inside. If you're outside, stay outside. Don't use matches, candles, or any flame. Broken gas lines and fire don't mix. Don't use elevators. ");
                intent.putExtra("AfterImage",  R.drawable.after_earthquake);
                intent.putExtra("AfterInfo",  "Check yourself and others for injuries. Provide first aid for anyone who needs it. Turn on the radio. Don't use the phone unless it's an emergency. Stay out of damaged buildings. Be careful around broken glass and debris.");

                startActivity(intent);
            }
        });

        floodBtn =  (ImageButton) context.findViewById(R.id.ib_flood);
        floodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SafetyGuidelines.class);
                intent.putExtra("DisasterType", "Flood");
                intent.putExtra("Tip1",
                        "Find safe shelter right away.");
                intent.putExtra("Tip2",
                        "Do not walk, swim or drive through flood waters. Turn Around, Don’t Drown!");
                intent.putExtra("Tip3",
                        "Listen to the radio or television for any updates.");
                intent.putExtra("Tip4",
                        "Stay off bridges over fast-moving water.");

                intent.putExtra("BeforeImage",  R.drawable.before_flood);
                intent.putExtra("BeforeInfo",  "Make a plan for your household, including your pets, so that you and your family know what to do, where to go, and what you will need to protect yourselves from flooding. Gather supplies for several days.");
                intent.putExtra("DuringImage",  R.drawable.during_flood);
                intent.putExtra("DuringInfo",  "Evacuate immediately, if told to evacuate. Never drive around barricades. Local responders use them to safely direct traffic out of flooded areas. Get to the highest level if trapped in a building.");
                intent.putExtra("AfterImage",  R.drawable.after_flood);
                intent.putExtra("AfterInfo",  "Pay attention to authorities for information and instructions. Return home only when authorities say it is safe. Be aware of the risk of electrocution. Avoid wading in floodwater, which can be contaminated.");

                startActivity(intent);
            }
        });










    }
}