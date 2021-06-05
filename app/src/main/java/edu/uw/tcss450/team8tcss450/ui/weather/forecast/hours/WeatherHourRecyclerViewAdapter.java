package edu.uw.tcss450.team8tcss450.ui.weather.forecast.hours;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherHourPredictionCardBinding;

/**
 * The class that creates weather hour prediction
 * card fragments in a consecutive list
 *
 * @author Brandon Kennedy
 * @version 4 June 2021
 */
public class WeatherHourRecyclerViewAdapter extends
        RecyclerView.Adapter<WeatherHourRecyclerViewAdapter.WeatherHourViewHolder> {

    // the list of weather hour prediction posts
    private final List<WeatherHourPostInfo> mInfoHourList;

    /**
     * Construct a new WeatherHourRecyclerViewAdapter object
     *
     * @param items the list of weather hour prediction posts to be used in this recycler view class
     */
    public WeatherHourRecyclerViewAdapter(List<WeatherHourPostInfo> items) {
        mInfoHourList = items;
    }

    @NonNull
    @Override
    public WeatherHourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherHourViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_weather_hour_prediction_card,
                        parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHourViewHolder holder, int position) {
        holder.setInfoList(mInfoHourList.get(position));
    }

    @Override
    public int getItemCount() {
        return mInfoHourList.size();
    }

    /**
     * Helper class that holds the meta data and other view necessities
     * for this recycler view adapter class
     *
     * @author Brandon Kennedy
     * @version 4 June 2021
     */
    public class WeatherHourViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public FragmentWeatherHourPredictionCardBinding binding;
        private WeatherHourPostInfo mInfoHourList;

        /**
         * Construct a new view holder for the WeatherHourRecyclerViewAdapter
         *
         * @param itemView the view that this view holder will be applied to
         */
        public WeatherHourViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentWeatherHourPredictionCardBinding.bind(mView);
        }

        /**
         * Set a weather hour post card with the needed readings,
         * for date, outlook, and temperature.
         *
         * @param infoList weather hour info that will be applied to and displayed on a weather post
         */
        void setInfoList(final WeatherHourPostInfo infoList) {
            mInfoHourList = infoList;
            binding.hourPredictionCardHour.setText(infoList.getTime() + " " + infoList.getDate());

            binding.hourPredictionCardOutlook.outlookOutlookIcon.setImageResource(infoList.getOutlookIconResId());

            binding.hourPredictionCardOutlook.outlookOutlookReading.setText(infoList.getOutlook());

            binding.hourPredictionCardTemperatureReading.setText(infoList.getTemperature() + "\u00B0F");
        }
    }

}
