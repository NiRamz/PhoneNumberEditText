package niramz.phonenumberedittext.source;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import niramz.phonenumberedittext.R;

/**
 * Created by NiRamz on 01.11.2014.
 */

public class PhoneNumberEditText extends EditText{

    private static int DEFAULT_COUNTRY_DRAWABLE = R.drawable.default_flag;

    public interface OnChangeCurrentCountryListener {
        void onChange(Country country);
    }

    public interface OnChangeCurrentOperatorListener {
        void onChange(Operator operator);
    }

    public void setOnChangeCurrentCountryListener(OnChangeCurrentCountryListener listener) {
        this.onChangeCurrentCountryListener = listener;
    }

    public void setChangeCurrentOperatorListener(OnChangeCurrentOperatorListener listener) {
        this.onChangeCurrentOperatorListener = listener;
    }

    private Drawable defaultDrawable = null;
    private OnChangeCurrentCountryListener onChangeCurrentCountryListener;
    private OnChangeCurrentOperatorListener onChangeCurrentOperatorListener;
    private List<Country> countryList;
    private Country currentCountry = null;
    private Operator currentOperator = null;
    private boolean useMask = true;

    public PhoneNumberEditText(Context context) {
        super(context);
        init(context, null, 0);
    }

    public PhoneNumberEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PhoneNumberEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        countryList = Country.getCountryList(context);
        customTextWatcher = new CustomTextWatcher();
        addTextChangedListener(customTextWatcher);
        defaultDrawable = context.getResources().getDrawable(R.drawable.default_flag);
        defaultDrawable.setBounds(0, 0, defaultDrawable.getIntrinsicWidth(), defaultDrawable.getIntrinsicHeight());
        showLeftDrawable(null);
    }

    public void setUseMask(boolean flag) {
        this.useMask = flag;
    }

    private CustomTextWatcher customTextWatcher;
    private class CustomTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String clearText = clearText(s.toString());
            findCountry(clearText);
            formatText(clearText);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }

    private void formatText(String str) {
        if(!useMask) {
            return;
        }

        if(currentOperator == null) {
            setText(str, str.length());
            return;
        }

        StringBuilder stringBuilder = new StringBuilder("+ " + currentCountry.getCode().replaceAll("[0-9]", "x") + " " + currentOperator.getMask());
        for(int i = 0; i < str.length(); i++) {
            int pos = stringBuilder.indexOf("x");
            if(pos != -1) {
                stringBuilder.replace(pos, pos + 1, String.valueOf(str.charAt(i)));
            }
        }
        int selectedPosition = stringBuilder.lastIndexOf(String.valueOf(str.charAt(str.length() - 1)));
        setText(stringBuilder.toString(), selectedPosition + 1);
    }

    private void setText(String text, int selectedPosition) {
        removeTextChangedListener(customTextWatcher);
        setText(text);
        setSelection(selectedPosition);
        addTextChangedListener(customTextWatcher);
    }

    private void findCountry(String str) {
       if(currentCountry != null) {
            if(str.startsWith(currentCountry.getCode())) {
                findOperator(currentCountry, str.substring(currentCountry.getCode().length()));
                return;
            } else {
                currentCountry = null;
                showLeftDrawable(null);
                if(onChangeCurrentCountryListener != null) {
                    onChangeCurrentCountryListener.onChange(null);
                }
            }
        }

        List<Country> tempCountryList = new ArrayList<Country>();

        for(Country item : countryList) {
            if(str.startsWith(item.getCode())) {
                if(!tempCountryList.contains(item)) {
                    tempCountryList.add(item);
                }
            }
        }

        if(tempCountryList.size() > 1) {
            currentCountry = findCurrentCountryByOperator(tempCountryList, str);
        } else if(tempCountryList.size() == 1) {
            currentCountry = tempCountryList.get(0);
            findOperator(currentCountry, str.substring(currentCountry.getCode().length()));
        }

        if(currentCountry != null) {
            showLeftDrawable(currentCountry.getDrawable());
            if(onChangeCurrentCountryListener != null) {
                onChangeCurrentCountryListener.onChange(currentCountry);
            }
        }
    }

    private Country findCurrentCountryByOperator(List<Country> list, String str) {
        for(Country country : list) {
            for(Operator operator : country.getOperators()) {
                String number = str.substring(country.getCode().length());
                if(number.matches(operator.getCode())) {
                    currentOperator = operator;
                    if(onChangeCurrentOperatorListener != null) {
                        onChangeCurrentOperatorListener.onChange(currentOperator);
                    }
                    return country;
                }
            }
        }
        return null;
    }

    private void findOperator(Country country, String str) {
        if(currentOperator != null) {
            if(str.toString().matches(currentOperator.getCode())) {
                return;
            } else {
                currentOperator = null;
                if(onChangeCurrentOperatorListener != null) {
                    onChangeCurrentOperatorListener.onChange(null);
                }
            }
        }

        for(Operator item : country.getOperators()) {
            if(str.matches(item.getCode())) {
                currentOperator = item;
                if(onChangeCurrentOperatorListener != null) {
                    onChangeCurrentOperatorListener.onChange(item);
                }
                return;
            }
        }
    }

    private String clearText(String text) {
        if(TextUtils.isEmpty(text)) {
            return text;
        }

        return text.replaceAll("[^0-9]", "");
    }

    private void showLeftDrawable(Drawable drawable) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            setCompoundDrawables(drawable, getCompoundDrawables()[1], getCompoundDrawables()[2], getCompoundDrawables()[3]);
        } else {
            setCompoundDrawables(defaultDrawable, getCompoundDrawables()[1], getCompoundDrawables()[2], getCompoundDrawables()[3]);
        }
    }

}
