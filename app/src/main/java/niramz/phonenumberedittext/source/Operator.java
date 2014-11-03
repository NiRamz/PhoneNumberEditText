package niramz.phonenumberedittext.source;

/**
 * Created by NiRamz on 01.11.2014.
 */

public enum  Operator {
    RU_VimpelCom("VimpelCom(Билайн)", "^(90(3|5|6|9)|96[0-5])[0-9]{0,7}", Mask.MASK_1),
    RU_MTC("MTC", "915", Mask.MASK_1),
    TZ_INDIGO("INDIGO", "^(93|92)[0-9]{0,7}", Mask.MASK_2),
    TZ_TT_MOBILE("TT_MOBILE", "^(917)[0-9]{0,6}", Mask.MASK_3),
    TZ_BABILON_MOBILE("BABILON_MOBILE", "^(918)[0-9]{0,6}", Mask.MASK_3),
    KZ_KAR_TEL("Kar Tel", "^(705|777)[0-9]{0,7}", Mask.MASK_1),
    KZ_KCELL("K'CELL", "^(70(1|2))[0-9]{0,7}", Mask.MASK_1),
    KR_BITEL("Bitel", "^(312)[0-9]{0,7}", Mask.MASK_1),
    KR_BITEL1("Bitel", "^(50)[0-9]{0,8}", Mask.MASK_4),
    KR_BI_MO_COM("BiMoCom", "^(55)[0-9]{0,8}", Mask.MASK_4),
    ;

    private String name;
    private String code;
    private String mask;

    Operator(String name, String code, String mask) {
        this.name = name;
        this.code = code;
        this.mask = mask;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getMask() {
        return mask;
    }
}
