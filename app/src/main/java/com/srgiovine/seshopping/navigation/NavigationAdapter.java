package com.srgiovine.seshopping.navigation;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import srgiovine.com.seshopping.R;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_FILTER = 0;
    private static final int ITEM_VIEW_TYPE_SETTINGS = 1;

    private final EventListener eventListener;

    private final Map<NavigationItem, Boolean> checkedItems = new HashMap<>();

    private List<NavigationItem> items;

    public NavigationAdapter(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void setItems(NavigationItem[] items) {
        this.items = Arrays.asList(items);
    }

    public void checkAllItems() {
        for (NavigationItem navigationItem : items) {
            checkedItems.put(navigationItem, true);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM_VIEW_TYPE_FILTER:
                viewHolder = new FilterItemViewHolder(inflater.inflate(R.layout.navigation_item_filter, parent, false));
                break;
            case ITEM_VIEW_TYPE_SETTINGS:
                viewHolder = new SettingsItemViewHolder(inflater.inflate(R.layout.navigation_item_settings, parent, false));
                break;
            default:
                throw new IllegalArgumentException("Unhandled item view type " + viewType);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NavigationItem item = items.get(position);

        if (item instanceof FilterNavigationItem) {
            ((FilterItemViewHolder) holder).bind((FilterNavigationItem) item);
        } else if (item instanceof SettingsNavigationItem) {
            ((SettingsItemViewHolder) holder).bind((SettingsNavigationItem) item);
        } else {
            throw new IllegalArgumentException("Unknown item type " + item.name());
        }
    }

    @Override
    public int getItemViewType(int position) {
        NavigationItem item = items.get(position);

        if (item instanceof FilterNavigationItem) {
            return ITEM_VIEW_TYPE_FILTER;
        } else if (item instanceof SettingsNavigationItem) {
            return ITEM_VIEW_TYPE_SETTINGS;
        }
        throw new IllegalArgumentException("Unknown item type " + item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    abstract class ViewHolder<T extends NavigationItem> extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView name;

        T item;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.name);
        }

        @CallSuper
        void bind(T item) {
            this.item = item;
            name.setText(item.name());
        }
    }

    private class FilterItemViewHolder extends ViewHolder<FilterNavigationItem> {

        private final ImageView icon;
        private final CheckBox checkBox;

        FilterItemViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }

        @Override
        void bind(FilterNavigationItem item) {
            super.bind(item);
            icon.setImageResource(item.iconRes());
            checkBox.setChecked(isChecked());
        }

        @Override
        public void onClick(View view) {
            checkedItems.put(item, !isChecked());
            checkBox.setChecked(isChecked());
            eventListener.onFilterItemClicked(item);
        }

        private boolean isChecked() {
            return checkedItems.containsKey(item) && checkedItems.get(item);
        }
    }

    private class SettingsItemViewHolder extends ViewHolder<SettingsNavigationItem> {

        private final ImageView icon;

        SettingsItemViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }

        @Override
        void bind(SettingsNavigationItem item) {
            super.bind(item);
            icon.setImageResource(item.iconRes());
        }

        @Override
        public void onClick(View view) {
            eventListener.onSettingsItemClicked(item);
        }
    }

    public interface EventListener {
        void onFilterItemClicked(FilterNavigationItem item);

        void onSettingsItemClicked(SettingsNavigationItem item);
    }
}
