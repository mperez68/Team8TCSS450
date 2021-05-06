package edu.uw.tcss450.team8tcss450.ui.weather.forecast.days;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherDayPredictionCardBinding;

/**
 * The class that creates weather day prediction
 * card fragments in a consecutive list
 *
 * @author Brandon Kennedy
 * @version 30 April 2021
 */
public class WeatherDayRecyclerViewAdapter extends
        RecyclerView.Adapter<WeatherDayRecyclerViewAdapter.WeatherDayViewHolder> {

    // the list of weather day prediction posts
    private final List<WeatherDayPostInfo> mInfoDayList;

    /**
     * Construct a new WeatherDayRecyclerViewAdapter object
     *
     * @param items the list of weather day prediction posts to be used in this recycler view class
     */
    public WeatherDayRecyclerViewAdapter(List<WeatherDayPostInfo> items) {
        mInfoDayList = items;
    }

    @NonNull
    @Override
    public WeatherDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherDayViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_weather_day_prediction_card,
                        parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDayViewHolder holder, int position) {
        holder.setInfoList(mInfoDayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mInfoDayList.size();
    }

    /**
     * Helper class that holds the meta data and other view necessities
     * for this recycler view adapter class
     *
     * @author Brandon Kennedy
     * @version 30 April 2021
     */
    public class WeatherDayViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public FragmentWeatherDayPredictionCardBinding binding;
        private WeatherDayPostInfo mInfoDayList;

        /**
         * Construct a new view holder for the WeatherDayRecyclerViewAdapter
         *
         * @param itemView the view that this view holder will be applied to
         */
        public WeatherDayViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentWeatherDayPredictionCardBinding.bind(mView);

        }

        /**
         * Set a weather day post card with the needed readings,
         * for date, outlook, and low and high temperatures.
         *
         * @param infoList weather day info that will be applied to and displayed on a weather post
         */
        void setInfoList(final WeatherDayPostInfo infoList) {
            mInfoDayList = infoList;
            binding.dayPredictionCardSpecificDay.setText(infoList.getDate());
            binding.dayPredictionCardOutlook.outlookGraphic
                    .setImageResource(R.drawable.ic_weather_outlook_grey_60dp);
            binding.dayPredictionCardTemperature.lowTemperatureReading
                    .setText(infoList.getLowTemperature());
            binding.dayPredictionCardTemperature.highTemperatureReading
                    .setText(infoList.getHighTemperature());
        }
    }



}
