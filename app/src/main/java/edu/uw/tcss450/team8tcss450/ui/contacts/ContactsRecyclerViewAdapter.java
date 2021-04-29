package edu.uw.tcss450.team8tcss450.ui.contacts;

import android.graphics.drawable.Icon;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentContactsCardBinding;

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactsViewHolder>  {

    //Store all of the blogs to present
    private final List<ContactsPost> myContacts;

    public ContactsRecyclerViewAdapter(List<ContactsPost> items) {
        this.myContacts = items;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactsViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_contacts_card, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        holder.setBlog(myContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return myContacts.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentContactsCardBinding binding;
        private ContactsPost mBlog;

        public ContactsViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentContactsCardBinding.bind(view);
            binding.buittonMore.setOnClickListener(this::handleMoreOrLess);
        }

        /**
         * When the button is clicked in the more state, expand the card to display
         * the blog preview and switch the icon to the less state.  When the button
         * is clicked in the less state, shrink the card and switch the icon to the
         * more state.
         * @param button the button that was clicked
         */
        private void handleMoreOrLess(final View button) {
            displayPreview();
        }

        /**
         * Helper used to determine if the preview should be displayed or not.
         */
        private void displayPreview() {
            if (binding.textPreview.getVisibility() == View.GONE) {
                binding.textPreview.setVisibility(View.VISIBLE);
                binding.buittonMore.setImageIcon(
                        Icon.createWithResource(
                                mView.getContext(),
                                R.drawable.ic_less_grey_24dp));
            } else {
                binding.textPreview.setVisibility(View.GONE);
                binding.buittonMore.setImageIcon(
                        Icon.createWithResource(
                                mView.getContext(),
                                R.drawable.ic_more_grey_24dp));
            }
        }

        void setBlog(final ContactsPost blog) {
            mBlog = blog;
            binding.buttonFullPost.setOnClickListener(view -> {
                //TODO add navigation later step
            });
            binding.textTitle.setText(blog.getTitle());
            binding.textPubdate.setText(blog.getPubDate());
            //Use methods in the HTML class to format the HTML found in the text
            final String preview =  Html.fromHtml(
                    blog.getTeaser(),
                    Html.FROM_HTML_MODE_COMPACT)
                    .toString().substring(0,100) //just a preview of the teaser
                    + "...";
            binding.textPreview.setText(preview);
            displayPreview();
        }
    }

}
