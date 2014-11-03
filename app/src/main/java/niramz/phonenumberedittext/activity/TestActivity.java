package niramz.phonenumberedittext.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import niramz.phonenumberedittext.source.Country;
import niramz.phonenumberedittext.source.Operator;
import niramz.phonenumberedittext.source.PhoneNumberEditText;

import niramz.phonenumberedittext.R;
/**
 * Created by NiRamz on 26.10.2014.
 */
public class TestActivity extends Activity {

    private PhoneNumberEditText editText;
    private TextView textView;
    private TextView operatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_layout);
        editText = (PhoneNumberEditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.text);
        operatorView = (TextView) findViewById(R.id.operator);
        editText.setOnChangeCurrentCountryListener(onChangeCurrentCountry);
        editText.setChangeCurrentOperatorListener(onChangeCurrentOperatorListener);
        editText.setUseMask(false);
    }

    private PhoneNumberEditText.OnChangeCurrentCountryListener onChangeCurrentCountry = new PhoneNumberEditText.OnChangeCurrentCountryListener() {
        @Override
        public void onChange(Country country) {
            if(country != null) {
                textView.setText(country.getName());
            } else {
                textView.setText("");
            }
        }
    };

    private PhoneNumberEditText.OnChangeCurrentOperatorListener onChangeCurrentOperatorListener = new PhoneNumberEditText.OnChangeCurrentOperatorListener() {
        @Override
        public void onChange(Operator operator) {
            if(operator != null) {
                operatorView.setText(operator.getName());
            } else {
                operatorView.setText("");
            }
        }
    };

}
