package adapters;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nicol.joynus.LoginActivity;
import com.example.nicol.joynus.R;

import java.util.ArrayList;

import model.Category;

public class EditInterestsGridViewAdapter  extends InterestsGridViewAdapter
{

    private ArrayList<Category> checkedCategories;
    public EditInterestsGridViewAdapter(Activity sender, ArrayList<Category> categoriesToDisplay)
    {
        super(sender,categoriesToDisplay);
        this.checkedCategories = new ArrayList<>(LoginActivity.getCurrentApplicationUser().getInterests());
    }


    private class EditInterestsGridViewHolder
    {
        private ImageView categoryImage;
        private TextView categoryTitle;
        private CheckBox checkedCategoryBox;

        public CheckBox getCheckedCategoryBox() {
            return checkedCategoryBox;
        }

        public void setCheckedCategoryBox(CheckBox checkedCategoryBox) {
            this.checkedCategoryBox = checkedCategoryBox;
        }

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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final EditInterestsGridViewHolder view;
        LayoutInflater inflater = this.sender.getLayoutInflater();
        if(convertView == null)
        {
            view = new EditInterestsGridViewHolder();
            convertView = inflater.inflate(R.layout.editinterestsgrid_row,null);
            view.setCategoryTitle((TextView) convertView.findViewById(R.id.editInterestsCategoryTextView));
            view.setCategoryImage((ImageView) convertView.findViewById(R.id.editInterestsCategoryImageView));
            view.setCheckedCategoryBox((CheckBox) convertView.findViewById(R.id.editInterestsCategoryCheckBox));
            convertView.setTag(view);
        }
        else
        {
            view = (EditInterestsGridViewHolder) convertView.getTag();
        }
        view.getCategoryTitle().setText(categoriesToDisplay.get(position).getTitle());
        view.getCategoryImage().setImageResource(categoriesToDisplay.get(position).getImagePath());
        view.getCheckedCategoryBox().setChecked(shouldCategoryBeChecked(categoriesToDisplay.get(position)));
        view.getCheckedCategoryBox().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(view.getCheckedCategoryBox().isChecked())
                {
                    checkedCategories.add(categoriesToDisplay.get(position));
                }
                else
                {
                    int iCategory = 0;
                    while(iCategory<checkedCategories.size() && !categoriesToDisplay.get(position).getImagePath().equals(checkedCategories.get(iCategory).getImagePath()))
                    {
                        iCategory++;
                    }
                    checkedCategories.remove(checkedCategories.get(iCategory));
                }
            }
        });
        return convertView;
    }

    private boolean shouldCategoryBeChecked(Category categoryToCheck)
    {
        for(Category category : checkedCategories)
        {
            if(category.getDbId() == categoryToCheck.getDbId())
            {
                return true;
            }
        }
        return false;
    }
    public ArrayList<Category> getCheckedCategories()
    {
        return checkedCategories;
    }
}
