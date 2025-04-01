package com.lsdapps.uni.bookmoth_library.library.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.lsdapps.uni.bookmoth_library.library.core.AppConst;
import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.GetWorkStatsUseCase;

import okhttp3.ResponseBody;

public class WorkDashboardViewModel extends ViewModel {
    private final GetWorkStatsUseCase getWorkStats = new GetWorkStatsUseCase(new LibApiRepository());

    private final MutableLiveData<WorkStats> workStats = new MutableLiveData<>();
    public LiveData<WorkStats> getWorkStats() {return workStats;}
    private void setWorkStats(WorkStats stats) {workStats.setValue(stats);}
    public void fetchWorkStats(int work_id) {
        getWorkStats.run(AppConst.TEST_TOKEN, work_id, new InnerCallback<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody body) {
                try {
                    String json = body.string();
                    Gson gson = new Gson();
                    WorkStats stats = gson.fromJson(json, WorkStats.class);
                    setWorkStats(stats);
                } catch (Exception ignored) {}
            }

            @Override
            public void onError(String errorMessage) {}
        });
    }

    public static class WorkStats {
        private int work_id;
        private int view_count;
        private int follow_count;
        private String last_update;
        private float price;

        public int getView_count() {
            return view_count;
        }

        public int getFollow_count() {
            return follow_count;
        }

        public String getLast_update() {
            return last_update;
        }

        public float getPrice() {
            return price;
        }
    }
}
