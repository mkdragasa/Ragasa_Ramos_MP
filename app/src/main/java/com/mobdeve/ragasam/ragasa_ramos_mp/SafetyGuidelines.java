package com.mobdeve.ragasam.ragasa_ramos_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SafetyGuidelines extends AppCompatActivity {

    private String disasterType;
    private TextView Title, Subtitle;
    private TextView TipOne, TipTwo, TipThree, TipFour;
    private TextView beforeText_tv, beforeInfo_tv, duringText_tv, duringInfo_tv,afterText_tv, afterInfo_tv;
    private ImageView beforePic, duringPic,afterPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_guidelines);

        Bundle bundle = getIntent().getExtras();

        //instantiate the fields from bundle (diff name from key)
        disasterType = bundle.getString("DisasterType");
        String tip1 = bundle.getString("Tip1");
        String tip2 = bundle.getString("Tip2");
        String tip3 = bundle.getString("Tip3");
        String tip4= bundle.getString("Tip4");
        String beforeInfo= bundle.getString("BeforeInfo");
        int beforeImg= bundle.getInt("BeforeImage");
        String duringInfo= bundle.getString("DuringInfo");
        int duringImg= bundle.getInt("DuringImage");
        String afterInfo= bundle.getString("AfterInfo");
        int afterImg= bundle.getInt("AfterImage");




        Title = (TextView)findViewById(R.id.tv_title);
        Subtitle = (TextView)findViewById(R.id.tv_subtitle);
        TipOne = (TextView)findViewById(R.id.tv_FirstTip);
        TipTwo = (TextView)findViewById(R.id.tv_SecondTip);
        TipThree = (TextView)findViewById(R.id.tv_ThirdTip);
        TipFour = (TextView)findViewById(R.id.tv_FourthTip);
        beforeText_tv = (TextView)findViewById(R.id.tv_Before);
        beforeInfo_tv = (TextView)findViewById(R.id.tv_BeforeInfo);
        beforePic = (ImageView)findViewById(R.id.iv_Before);
        duringText_tv = (TextView)findViewById(R.id.tv_During);
        duringInfo_tv = (TextView)findViewById(R.id.tv_DuringInfo);
        duringPic = (ImageView)findViewById(R.id.iv_During);
        afterText_tv = (TextView)findViewById(R.id.tv_After);
        afterInfo_tv = (TextView)findViewById(R.id.tv_AfterInfo);
        afterPic = (ImageView)findViewById(R.id.iv_After);




        Title.setText(disasterType + " Safety Guidelines");
        Subtitle.setText(disasterType + " Tips");
        TipOne.setText(tip1);
        TipTwo.setText(tip2);
        TipThree.setText(tip3);
        TipFour.setText(tip4);

        beforeInfo_tv.setText(beforeInfo);
        beforePic.setImageResource(beforeImg);

        duringInfo_tv.setText(duringInfo);
        duringPic.setImageResource(duringImg);

        afterInfo_tv.setText(afterInfo);
        afterPic.setImageResource(afterImg);

        if (disasterType.equals("Earthquake")){
            beforeText_tv.setText("What to do Before an " + disasterType );
            duringText_tv.setText("What to do During an " + disasterType );
            afterText_tv.setText("What to do After an " + disasterType );
        }else {
            beforeText_tv.setText("What to do Before a " + disasterType );
            duringText_tv.setText("What to do During a " + disasterType );
            afterText_tv.setText("What to do After a " + disasterType );
        }


    }
}