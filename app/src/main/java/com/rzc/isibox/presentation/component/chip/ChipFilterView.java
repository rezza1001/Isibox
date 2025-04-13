package com.rzc.isibox.presentation.component.chip;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.core.view.ViewCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.rzc.isibox.R;
import com.rzc.isibox.master.MyView;
import com.rzc.isibox.tools.Utility;

import java.util.ArrayList;

/**
 * Created by Mochamad Rezza Gumilang on 08/Oct/2021.
 * Class Info :
 */

public class ChipFilterView extends MyView {

    private ChipGroup chgroup_choice;
    private ArrayList<ChoiceModel> mData;
    public ChipFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int setLayout() {
        return R.layout.component_choice_chips;
    }

    @Override
    protected void initLayout() {
        chgroup_choice = findViewById(R.id.chgroup_choice);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void create() {
        super.create();
        mData = new ArrayList<>();
        chgroup_choice.removeAllViews();
    }

    public void create(ArrayList<ChoiceModel> data){
        create();
        mData = data;
        for (int i=0; i<data.size(); i++){
            chgroup_choice.addView(generateChip(data.get(i).getName(),data.get(i).isSelected(),data.get(i).getKey()));
        }
    }
    public void addChip(ChoiceModel data){
        mData.add(data);
        Integer key = mData.size();
        String Key = key.toString();
        Chip chip = generateChipDelAction(data.getName(),data.isSelected(),Key);
        chip.setShapeAppearanceModel(setConfigRadius(2));
        chip.setChipStrokeWidth(1);
        chip.setChipStrokeColorResource(R.color.grey3);
        chip.setChipBackgroundColorResource(R.color.white);
        chip.setTextColor(Color.parseColor("#000000"));
        chgroup_choice.addView(chip);

    }

    public void create(ArrayList<ChoiceModel> data, float radius, float stroke, int strokeColor, int background, int textColor){
        create();
        mData = data;
        for (int i=0; i<data.size(); i++){
            Chip chip = generateChip(data.get(i).getName(),data.get(i).isSelected(),data.get(i).getKey());
            chip.setShapeAppearanceModel(setConfigRadius(radius));
            chip.setChipStrokeWidth(stroke);
            chip.setChipStrokeColorResource(strokeColor);
            chip.setChipBackgroundColorResource(background);
            chip.setTextColor(textColor);
            chgroup_choice.addView(chip);
        }
    }

    private ShapeAppearanceModel setConfigRadius(float radius){
        return new ShapeAppearanceModel.Builder()
                .setAllCorners(CornerFamily.ROUNDED, radius)
                .build();
    }

    public void addFilter(ChoiceModel choiceModel){
        chgroup_choice.addView(generateChip(choiceModel.getName(),choiceModel.isSelected(),choiceModel.getKey()));
    }

    public void clear(){
        chgroup_choice.clearCheck();
        for (int i=0; i<chgroup_choice.getChildCount(); i++){
            Chip chip = (Chip)chgroup_choice.getChildAt(i);
            chip.setChecked(false);
        }
    }

    public void remove(String key){
        ChoiceModel data = null;
        for (int i=0; i<chgroup_choice.getChildCount(); i++){
            Chip chip = (Chip)chgroup_choice.getChildAt(i);
            if (chip.getTag().toString().equals(key)){
                chgroup_choice.removeViewAt(i);
                data = getChips(i);
            }
            if (data != null){
                mData.remove(data);
            }
        }
    }

    public void removeAll(){
        chgroup_choice.removeAllViews();
    }

    public Chip generateChipDelAction(String value, boolean isChecked, String tag){
        Chip chip = (Chip) mActivity.getLayoutInflater().inflate(R.layout.component_chips_item, null, false);
        chip.setText(value);
        chip.setTag(tag);
        chip.setChipBackgroundColorResource(R.color.primary);
        chip.setChecked(isChecked);
        chip.setId(ViewCompat.generateViewId());
        chip.setTextColor(textColorStateList);
        chip.setOnClickListener(v -> remove(tag));

        return chip;
    }
    public Chip generateChip(String value, boolean isChecked, String tag){
        Chip chip = (Chip) mActivity.getLayoutInflater().inflate(R.layout.component_chips_item, null, false);
        chip.setText(value);
        chip.setTag(tag);
        chip.setChipBackgroundColorResource(R.color.primary);
        chip.setChecked(isChecked);
        chip.setId(ViewCompat.generateViewId());
        chip.setTextColor(textColorStateList);
        chip.setOnClickListener(v -> {

            if (onSelectedListener != null){
                onSelectedListener.onSelected(value,tag);
            }
        });

        chip.setOnCheckedChangeListener((buttonView, isChecked1) -> {
            if (isChecked1){
                chip.setTypeface(Utility.getFontFamily(mActivity, R.font.roboto_bold));
                chip.setTextSize(16);
            }
            else {
                chip.setTypeface(Utility.getFontFamily(mActivity, R.font.roboto_regular));
                chip.setTextSize(14);
            }
        });
        return chip;
    }

    public void reset(){
        chgroup_choice.clearCheck();
    }

    public String getChoice(){
        for (int i=0; i<chgroup_choice.getChildCount(); i++){
            Chip chip = (Chip)chgroup_choice.getChildAt(i);
            if (chip.isChecked()){
                return chip.getText().toString();
            }
        }
        return "";
    }

    public void setSelected(String value){
        if (!value.isEmpty()){
            for (int i=0; i<chgroup_choice.getChildCount(); i++){
                Chip chip = (Chip)chgroup_choice.getChildAt(i);
                if (chip.getText().toString().equals(value)){
                    chip.setChecked(true);
                }
            }
        }
    }

    public void setSelected(int index){
        if (chgroup_choice.getChildCount() == 0){
            return;
        }
        Chip chip = (Chip)chgroup_choice.getChildAt(index);
        chip.setChecked(true);
        if (onSelectedListener != null){
            onSelectedListener.onSelected(chip.getText().toString(),chip.getTag().toString());
        }
    }

    private ColorStateList createTextColor(int colorDefault, int colorSelect){
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{}
                },
                new int[]{
                        colorSelect,  // Color when selected
                        colorDefault // Color when not selected
                }
        );
    }

    ColorStateList textColorStateList = new ColorStateList(
            new int[][]{
                    new int[]{android.R.attr.state_checked},
                    new int[]{}
            },
            new int[]{
                    Color.parseColor("#FFFFFF"),  // Color when selected
                    Color.parseColor("#80FFFFFF") // Color when not selected
            }
    );

    public ChoiceModel getChips(int index){
       return mData.get(index);
    }
    public int getChipsSize(){
       return chgroup_choice.getChildCount();
    }

    private OnSelectedListener onSelectedListener;
    public void setOnSelectedListener(OnSelectedListener onSelectedListener){
        this.onSelectedListener = onSelectedListener;
    }
    public interface OnSelectedListener{
        void onSelected(String data, String key);
    }

    public String getDataBySeparator(String separator){
        StringBuilder data = new StringBuilder();
        for (int i=0; i<chgroup_choice.getChildCount(); i++){
            ChoiceModel chip = getChips(i);
            if (i ==0 ){
                data = new StringBuilder(chip.getKey());
            }
            else {
                data.append(separator).append(chip.getKey());
            }
        }
        return data.toString();
    }

    public ArrayList<String> getDataArrayList(){
        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<chgroup_choice.getChildCount(); i++){
            ChoiceModel chip = getChips(i);
            list.add(chip.getKey());
        }
        return list;
    }

}
