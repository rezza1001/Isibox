package com.rzc.isibox.presentation.request.vm;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rzc.isibox.master.MyViewModel;
import com.rzc.isibox.tools.Utility;

import java.io.File;
import java.util.ArrayList;

public class ImageViewModel extends MyViewModel {
    public ImageViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<String>> buildBase64(ArrayList<String> files) {
        ArrayList<String> list = new ArrayList<>();
        MutableLiveData<ArrayList<String>> liveData = new MutableLiveData<>();
        for (String sPath : files){
            Bitmap bitmap = BitmapFactory.decodeFile(sPath);
            String value = Utility.getBase64StringJPEG(bitmap);
            list.add(value);
        }
        liveData.postValue(list);
        return liveData;
    }
}
