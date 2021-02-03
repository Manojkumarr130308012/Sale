package com.example.sale.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.sale.Fragment.Allitems;
import com.example.sale.Fragment.CustomerListFragment;
import com.example.sale.Model.CustomerData;
import com.example.sale.Model.ItemData;
import com.example.sale.R;
import com.example.sale.font.FontFace;
import com.example.sale.font.OpenSansFontUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemListRecyclerViewAdapter extends RecyclerView.Adapter<ItemListRecyclerViewAdapter.ViewHolder> implements Filterable {
    public static final String TAG = ItemListRecyclerViewAdapter.class.getName();

    private List<ItemData> _items;

    private List<ItemData> _filteredItems;
    private final Allitems.OnListFragmentInteractionListener mListener;
    OpenSansFontUtils mFontClass;
    Context mContext;
    Typeface openSanRegularBold;

    public ItemListRecyclerViewAdapter(List<ItemData> items, Allitems.OnListFragmentInteractionListener listener, Context context) {
        _items = items;
        this._filteredItems = items;
        mListener = listener;
        mContext = context;
        mFontClass = new OpenSansFontUtils(mContext);
        openSanRegularBold = Typeface.createFromAsset(mContext.getAssets(), "fonts/OpenSans-Regular.ttf");
    }

    @Override
    public int getItemCount() {
        return _filteredItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allitemlist_item, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = _filteredItems.get(position);
        mFontClass.setTextView(holder.mTxtCustomerName, FontFace.SEMIBOLD);
        mFontClass.setTextView(holder.mTxtCustomerNo, FontFace.REGULAR);
        mFontClass.setTextView(holder.mTxtCustomerPlace, FontFace.REGULAR);
        mFontClass.setTextView(holder.mTxtOutstandingamount, FontFace.SEMIBOLD_ITALIC);
        try {
            holder.mTxtCustomerName.setText(holder.mItem.getCustomername());
            holder.mTxtCustomerNo.setText(holder.mItem.getCustomerMobile());
            holder.mTxtCustomerPlace.setText(holder.mItem.getCustomerCityName());
            holder.mTxtOutstandingamount.setText("Wallet amount : " + holder.mItem.getCustomerOSAmount() + " Rs");
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            Character firstchar = holder.mItem.getCustomername().charAt(0);

            int color1 = mContext.getColor(R.color.primary_text_too_light);
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig().useFont(openSanRegularBold)
                    .endConfig()
                    .buildRound(firstchar.toString(), color1);

            holder.mImgNameCircle.setImageDrawable(drawable);

//            holder.mImgBtn_payment.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (null != mListener) {
//                        mListener.onListPaymentInteraction(holder.mItem);
//                    }
//                }
//            });
//
//            holder.mImgBtn_new_order.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (null != mListener) {
//                        // Notify the active callbacks interface (the activity, if the
//                        // fragment is attached to one) that an item has been selected.
//                        mListener.onListOrderInteraction(holder.mItem);
//                    }
//                }
//            });
        } catch (Exception e) {
//            LogCrashlytics.log(e);
            e.printStackTrace();
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    _filteredItems = _items;
                } else {
                    List<ItemData> filteredList = new ArrayList<>();
                    for (ItemData row : _items) {
                        if (row.getCustomername().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getCustomerMobile().toLowerCase().contains(charString.toLowerCase())
                        ) {
                            filteredList.add(row);
                        }
                    }
                    _filteredItems = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = _filteredItems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                _filteredItems = (ArrayList<ItemData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void updateList(List<ItemData> list) {
        _items = list;
        // _filteredItems = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTxtCustomerName;
        public final TextView mTxtCustomerNo;
        public final TextView mTxtCustomerPlace;
        public final TextView mTxtOutstandingamount;
        public final ImageView mImgNameCircle;
//        public final ImageButton mImgBtn_new_order;
//        public final ImageButton mImgBtn_payment;
        public ItemData mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTxtCustomerName = view.findViewById(R.id.txt_customer_name);
            mTxtCustomerNo = view.findViewById(R.id.txt_customer_number);
            mTxtCustomerPlace = view.findViewById(R.id.txt_customer_place);
            mTxtOutstandingamount = view.findViewById(R.id.txt_customer_os_amount);
            mImgNameCircle = view.findViewById(R.id.img_letter_icon);
//            mImgBtn_new_order = view.findViewById(R.id.btn_new_order);
//            mImgBtn_payment = view.findViewById(R.id.btn_payment);
        }
    }
}
