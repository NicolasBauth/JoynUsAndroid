package adapters;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nicol.joynus.R;

import java.util.ArrayList;

import model.Category;

public class InterestsGridViewAdapter extends BaseAdapter
{
    private ArrayList<Category> categoriesToDisplay;
    private Activity sender;

    public InterestsGridViewAdapter(Activity sender, ArrayList<Category> categoriesToDisplay)
    {
        super();
        this.sender = sender;
        this.categoriesToDisplay = categoriesToDisplay;
    }
    @Override
    public int getCount()
    {
        return categoriesToDisplay.size();
    }
    @Override
    public Category getItem(int position)
    {
        return categoriesToDisplay.get(position);
    }
    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    private class InterestsGridViewHolder
    {
        private ImageView categoryImage;
        private TextView categoryTitle;

        public ImageView getCategoryImage() {
            return categoryImage;
        }

        public void setCategoryImage(ImageView categoryImage) {
            this.categoryImage = categoryImage;
        }

        public TextView getCategoryTitle() {
            return categoryTitle;
        }

        public void setCategoryTitle(TextView categoryTitle) {
            this.categoryTitle = categoryTitle;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        InterestsGridViewHolder view;
        LayoutInflater inflater = sender.getLayoutInflater();
        if(convertView == null)
        {
            view = new InterestsGridViewHolder();
            convertView = inflater.inflate(R.layout.interestsgrid_row,null);
            view.setCategoryTitle((TextView) convertView.findViewById(R.id.categoryTextView));
            view.setCategoryImage((ImageView) convertView.findViewById(R.id.categoryImageView));
            convertView.setTag(view);
        }
        else
        {
            view = (InterestsGridViewHolder) convertView.getTag();
        }
        view.getCategoryTitle().setText(categoriesToDisplay.get(position).getTitle());
        view.getCategoryImage().setImageResource(categoriesToDisplay.get(position).getImagePath());
        return convertView;
    }

}

