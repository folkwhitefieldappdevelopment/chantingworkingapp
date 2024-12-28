package com.iskcon.folk.app.chantandhear.constant;

public enum HareKrishnaMantraConstant {

    HKM1(HKMConstant.HARE_MANTRA_STRING),
    HKM2(HKMConstant.KRISHNA_MANTRA_STRING),
    HKM3(HKMConstant.HARE_MANTRA_STRING),
    HKM4(HKMConstant.KRISHNA_MANTRA_STRING),
    HKM5(HKMConstant.KRISHNA_MANTRA_STRING),
    HKM6(HKMConstant.KRISHNA_MANTRA_STRING),
    HKM7(HKMConstant.HARE_MANTRA_STRING),
    HKM8(HKMConstant.HARE_MANTRA_STRING),
    HKM9(HKMConstant.HARE_MANTRA_STRING),
    HKM10(HKMConstant.RAMA_MANTRA_STRING),
    HKM11(HKMConstant.HARE_MANTRA_STRING),
    HKM12(HKMConstant.RAMA_MANTRA_STRING),
    HKM13(HKMConstant.RAMA_MANTRA_STRING),
    HKM14(HKMConstant.RAMA_MANTRA_STRING),
    HKM15(HKMConstant.HARE_MANTRA_STRING),
    HKM16(HKMConstant.HARE_MANTRA_STRING);

    private String mantraText;
    private int endPosition;

    HareKrishnaMantraConstant(String mantraText) {
        this.mantraText = mantraText;
        this.endPosition = mantraText.length();
    }

    public String getMantraText() {
        return mantraText;
    }

    public int getEndPosition() {
        return endPosition;
    }
}

class HKMConstant {
    public static final String HARE_MANTRA_STRING = "Hare";
    public static final String KRISHNA_MANTRA_STRING = "Krishna";
    public static final String RAMA_MANTRA_STRING = "Rama";
}