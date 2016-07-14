package miteat.miteat.Model;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by oris1991 on 08/05/2016.
 */
public class TimeEditText extends EditText {

    int hour;
    int minutes;

    private void init() {
        Calendar calendar = new GregorianCalendar();
        hour = calendar.get(Calendar.HOUR);
        minutes = calendar.get(Calendar.MINUTE);
        if (minutes / 10 == 0)
            setText("" + hour + ":" + "0" + minutes);
        else
            setText("" + hour + ":" + minutes);

    }

    public interface OnTimeSetListener {
        public void timeSet(int hour, int minutes);
    }

    OnTimeSetListener listener;

    public void setOnDateSetListener(OnTimeSetListener listener) {
        this.listener = listener;
    }

    public int getHour() {
        return hour;
    }

    public int getMinutes() {
        return minutes;
    }


    public TimeEditText(Context context) {
        super(context);
        init();
    }

    public TimeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void set(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            InnerTimePicker timePicker = new InnerTimePicker();
            timePicker.init(hour, minutes);
            timePicker.setListener(new InnerTimePicker.Listener() {
                @Override
                public void done(int hour, int minutes) {
                    TimeEditText.this.hour = hour;
                    TimeEditText.this.minutes = minutes;
                    String minutes_text = null;
                    if (minutes / 10 == 0)
                        minutes_text = "0" + minutes;
                    else
                        minutes_text = String.valueOf(minutes);
                    setText("" + hour + ":" + minutes_text);
                    if (listener != null) {
                        listener.timeSet(hour, minutes);
                    }
                }
            });
            timePicker.show(((Activity) getContext()).getFragmentManager(), "GGG");
        }
        return super.onTouchEvent(event);
    }

    public static class InnerTimePicker extends DialogFragment {
        int hour;
        int minutes;

        public void init(int hour, int minutes) {
            this.hour = hour;
            this.minutes = minutes;
        }

        interface Listener {
            public void done(int hour, int minutes);
        }

        Listener listener;

        public void setListener(Listener listener) {
            this.listener = listener;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    listener.done(hourOfDay, minute);
                    Log.d("TA", "date set" + hourOfDay + ":" + minute);
                }
            }, hour, minutes, false);

            return dialog;
        }
    }
}
