package com.tsdreamdeveloper.places.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tsdreamdeveloper.places.R;
import com.tsdreamdeveloper.places.mvp.model.Data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Timur Seisembayev
 * @since 28.09.2019
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    /**
     * The interface that receives onClick item.
     */
    public interface OnItemClickListener {
        void onItemClick(Data item);
    }

    private boolean isLoadingAdded = false;
    private List<Data> list;
    private final OnItemClickListener listener;
    private Context context;
    private AsyncTask<Void, Void, Bitmap> mTask;

    public PlaceAdapter(OnItemClickListener listener) {
        this.list = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        int resId;
        if (viewType == LOADING) {
            resId = R.layout.list_item_loading;
        } else {
            resId = R.layout.list_item_place;
        }

        View view = LayoutInflater.from(context).inflate(resId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == list.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaceAdapter.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM) {
            Data item = list.get(position);
            mTask = new AsyncTask<Void, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(Void... params) {
                    URL url = null;
                    try {
                        url = new URL("https://www.freepngimg.com/thumb/facebook/65289-lawton-computer-facebook-place-icons-free-hq-image.png");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    try {
                        return BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(Bitmap result) {
                    super.onPostExecute(result);
                    if (result != null) {
                        holder.mLogo.setImageBitmap(result);
                    } else {
                        holder.mLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher_background));
                    }

                }
            };

            mTask.execute();

            holder.mTitle.setText(item.getName());

            holder.bind(item, listener);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mTitle;
        final ImageView mLogo;

        ViewHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.tv_title);
            mLogo = view.findViewById(R.id.iv_logo);
        }

        void bind(final Data item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public void setItems(List<Data> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
        notifyDataSetChanged();
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        list.add(new Data());
        notifyItemInserted(list.size() - 1);
    }

    public void removeLoadingFooter() {
        if (isLoadingAdded) {
            isLoadingAdded = false;

            int position = list.size() - 1;
            Data item = list.get(position);

            if (item != null) {
                list.remove(position);
                notifyItemRemoved(position);
            }
        }
    }
}
