package niramz.phonenumberedittext.source;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import niramz.phonenumberedittext.R;
/**
 * Created by NiRamz on 01.11.2014.
 */

public class Country {

    private String name;
    private String code;
    private Operator[] operators;
    private Drawable drawable;

    private Country(String name, String code, Operator[] operators, Drawable drawable) {
        this.name = name;
        this.code = code;
        this.operators = operators;
        this.drawable = drawable;
    }

    public static List<Country> getCountryList(Context context) {
        List<Country> list = new ArrayList<Country>();
        list.add(new Country(context.getString(R.string.ru), "7", new Operator[]{Operator.RU_VimpelCom, Operator.RU_MTC}, context.getResources().getDrawable(R.drawable.ru)));
        list.add(new Country(context.getString(R.string.kz), "7", new Operator[]{Operator.KZ_KAR_TEL, Operator.KZ_KCELL}, context.getResources().getDrawable(R.drawable.kz)));
        list.add(new Country(context.getString(R.string.kz), "996", new Operator[]{Operator.KR_BI_MO_COM, Operator.KR_BITEL, Operator.KR_BITEL1}, context.getResources().getDrawable(R.drawable.kr)));
        list.add(new Country(context.getString(R.string.be), "375", new Operator[]{}, context.getResources().getDrawable(R.drawable.be)));
        list.add(new Country(context.getString(R.string.ua), "38", new Operator[]{}, context.getResources().getDrawable(R.drawable.ua)));
        list.add(new Country(context.getString(R.string.en), "44", new Operator[]{}, context.getResources().getDrawable(R.drawable.en)));
        list.add(new Country(context.getString(R.string.tz), "992", new Operator[]{Operator.TZ_BABILON_MOBILE, Operator.TZ_INDIGO, Operator.TZ_TT_MOBILE}, context.getResources().getDrawable(R.drawable.tz)));
        return list;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Operator[] getOperators() {
        return operators;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        if (!code.equals(country.code)) return false;
        if (!drawable.equals(country.drawable)) return false;
        if (!name.equals(country.name)) return false;
        if (!Arrays.equals(operators, country.operators)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result + Arrays.hashCode(operators);
        result = 31 * result + drawable.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", operators=" + Arrays.toString(operators) +
                ", drawable=" + drawable +
                '}';
    }
}
