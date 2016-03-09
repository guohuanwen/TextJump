package com.bcgtgjyb.textjump;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by bigwen on 2016/3/9.
 */
public class CommentTextView extends TextView {

    private static Context mContext;
    private SpannableString spannableString;

    public CommentTextView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CommentTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        setMovementMethod(MovementMethod.getInstance());
        setHighlightColor(Color.RED);
    }

    public void setCommentText(String sendName,String receive,String text){
        StringBuffer stringBuffer = new StringBuffer();
        int start1 = stringBuffer.length();
        stringBuffer.append(sendName);
        int end1 = stringBuffer.length();
        stringBuffer.append("回复");
        int start2 = stringBuffer.length();
        stringBuffer.append(receive);
        int end2 = stringBuffer.length();
        stringBuffer.append(":");
        int start3 = stringBuffer.length();
        stringBuffer.append(text);
        int end3 = stringBuffer.length();
        spannableString = new SpannableString(stringBuffer);
        setSpan(start1,end1,sendName,0);
        setSpan(start2,end2,receive,0);
        setSpan(start3,end3,text,0);
        setText(spannableString);
    }

    private void setSpan(int start, int end, String text,int backType) {
        spannableString.setSpan(new MyTextSpan(text,backType),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private class MyTextSpan extends ClickableSpan {

        private String text;
        private int back;

        public MyTextSpan(String uid,int back) {
            this.text = uid;
            this.back = back;
        }

        @Override
        public void onClick(final View widget) {
            if (back == 0) {
                Toast.makeText(mContext, "uid=" + text, Toast.LENGTH_SHORT).show();
                //点击后颜色恢复正常
                widget.invalidate();
            }else if (back == 1){
                Toast.makeText(mContext, "输入", Toast.LENGTH_SHORT).show();
                widget.setBackgroundColor(Color.parseColor("#333333"));
                widget.invalidate();
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        widget.setBackgroundColor(Color.parseColor("#00000000"));
                        widget.invalidate();
                    }
                }, 500);
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.BLACK);
            ds.setUnderlineText(false);
        }
    }

    public static class MovementMethod extends LinkMovementMethod {

        private static MovementMethod movementMethod;

        private MovementMethod(){

        }

        public static MovementMethod getInstance(){
            if (movementMethod == null){
                movementMethod = new MovementMethod();
            }
            return movementMethod;
        }

        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            int action = event.getAction();

            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                if (off >= widget.getText().length()) {
                    // Return true so click won't be triggered in the leftover empty space
                    Toast.makeText(mContext,"输入",Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
            return super.onTouchEvent(widget, buffer, event);
        }
    }

}
