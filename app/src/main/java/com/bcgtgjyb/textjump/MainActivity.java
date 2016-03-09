package com.bcgtgjyb.textjump;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private String text = "";
    private SpannableString spannableString;
    private List<String> list = new ArrayList<>();
    private CommentTextView comment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        comment = (CommentTextView) findViewById(R.id.comment);
        initList();
        showName(list);
        initComment();
    }

    private void initComment() {
        comment.setCommentText("guo","huan","bigwen");
    }


    private void initList() {
        for (int i = 0; i < 10; i++) {
            list.add("guo" + i * 123);
        }
    }



    private void showName(List<String> nameList) {
        StringBuffer nameText = new StringBuffer();
        for (int i = 0; i < nameList.size(); i++) {
            String name = nameList.get(i);
            nameText.append(name);
            if (i != nameList.size() - 1) {
                nameText.append("、");
            }
        }
        text = nameText.toString();
        spannableString = new SpannableString(text);
        nameText = new StringBuffer();
        for (int i = 0; i < nameList.size(); i++) {
            int start = nameText.length();
            String name = nameList.get(i);
            nameText.append(name);
            int end = nameText.length();
            setSpan(start, end, i);
            if (i != nameList.size() - 1) {
                nameText.append("、");
            }
        }
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.BLUE);
    }

    private void setSpan(int start, int end, int uid) {
        spannableString.setSpan(new MyTextSpan(uid),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private class MyTextSpan extends ClickableSpan {

        private int uid;

        public MyTextSpan(int uid) {
            this.uid = uid;
        }

        @Override
        public void onClick(View widget) {
            Toast.makeText(MainActivity.this, "uid=" + uid, Toast.LENGTH_SHORT).show();
            widget.invalidate();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.BLACK);
            ds.setUnderlineText(false);
        }
    }
}
