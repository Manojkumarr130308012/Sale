package com.example.sale.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.sale.Activity.Additemticket;
import com.example.sale.Fragment.TicketListFragment;
import com.example.sale.Model.TicketData;
import com.example.sale.R;
import com.example.sale.font.OpenSansFontUtils;


import java.util.ArrayList;
import java.util.List;

import ru.embersoft.expandabletextview.ExpandableTextView;

public class TicketListRecyclerViewAdapter extends RecyclerView.Adapter<TicketListRecyclerViewAdapter.ViewHolder> implements Filterable {
    public static final String TAG = TicketListRecyclerViewAdapter.class.getName();

    private List<TicketData> _items;

    private List<TicketData> _filteredItems;
    private final TicketListFragment.OnListFragmentInteractionListener mListener;
    OpenSansFontUtils mFontClass;
    Context mContext;
    Typeface openSanRegularBold;

    public TicketListRecyclerViewAdapter(List<TicketData> items, TicketListFragment.OnListFragmentInteractionListener listener, Context context) {
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
                .inflate(R.layout.customerlist_item, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = _filteredItems.get(position);
        mFontClass.setTextView(holder.mTxtCustomerName);
        mFontClass.setTextView(holder.mTxtCustomerNo);
        mFontClass.setTextView(holder.mTxtCustomerPlace);
        mFontClass.setTextView(holder.mTxtOutstandingamount);
        try {
            holder.mTxtCustomerName.setText(holder.mItem.getCustomerName());
            holder.mTxtCustomerNo.setText(holder.mItem.getTicketNo());
            holder.mTxtCustomerPlace.setText(holder.mItem.getTicketDate());
            holder.descTextView.setText(holder.mItem.getCustomerAddress());
            holder.descTextView.setOnStateChangeListener(new ExpandableTextView.OnStateChangeListener() {
                @Override
                public void onStateChange(boolean isShrink) {
                    TicketData contentItem = _filteredItems.get(position);
                    contentItem.setShrink(isShrink);
                    _items.set(position, contentItem);
                }
            });
            holder.mTxtOutstandingamount.setText(""+holder.mItem.getDescription());
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            Character firstchar = holder.mItem.getCustomerName().charAt(0);

            int color1 = mContext.getColor(R.color.blue1);
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig().useFont(openSanRegularBold)
                    .endConfig()
                    .buildRound(firstchar.toString(), color1);

            holder.mImgNameCircle.setImageDrawable(drawable);
            holder.descTextView.resetState(holder.mItem.isShrink());
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create custom dialog object
                    final Dialog dialog = new Dialog(mContext);
                    // Include dialog.xml file
                    dialog.setContentView(R.layout.dialog);
                    // Set dialog title
                    dialog.setTitle("Details");

                    // set values for custom dialog components - text, image and button
                    TextView TicketID = (TextView) dialog.findViewById(R.id.TicketID);
                    TextView TicketNo = (TextView) dialog.findViewById(R.id.TicketNo);
                    TextView TicketDate = (TextView) dialog.findViewById(R.id.TicketDate);
                    TextView CustomerName = (TextView) dialog.findViewById(R.id.CustomerName);
                    TextView CustomerAddress = (TextView) dialog.findViewById(R.id.CustomerAddress);
                    TextView Description = (TextView) dialog.findViewById(R.id.Description);
                    TextView Priority = (TextView) dialog.findViewById(R.id.Priority);
                    TextView CreatedBy = (TextView) dialog.findViewById(R.id.CreatedBy);
                    TextView Status = (TextView) dialog.findViewById(R.id.Status);
                    TicketID.setText(""+holder.mItem.getTicketID());
                    TicketNo.setText(""+holder.mItem.getTicketNo());
                    TicketDate.setText(""+holder.mItem.getTicketDate());
                    CustomerName.setText(""+holder.mItem.getCustomerName());
                    CustomerAddress.setText(""+holder.mItem.getCustomerAddress());
                    Description.setText(""+holder.mItem.getDescription());
                    Priority.setText(""+holder.mItem.getPriority());
                    CreatedBy.setText(""+holder.mItem.getCreatedBy());
                    Status.setText(""+holder.mItem.getStatus());


                    dialog.show();

                    Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
                    // if decline button is clicked, close the custom dialog
                    declineButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Close dialog
                            dialog.dismiss();

                            final Intent i=new Intent(mContext, Additemticket.class);
                            i.putExtra("ticketid",""+holder.mItem.getTicketID());
                            i.putExtra("custamername",""+holder.mItem.getCustomerName());
                            i.putExtra("custameraddress",""+holder.mItem.getCustomerAddress());
                            mContext.startActivity(i);
                        }
                    });

                }
            });
            holder.telephone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try
                    {
                        if(Build.VERSION.SDK_INT > 22)
                        {
                            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling

                                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE}, 101);

                                return;
                            }

                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + holder.mItem.getCustomerName()));
                            mContext.startActivity(callIntent);

                        }
                        else {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + holder.mItem.getCustomerAddress()));
                            mContext.startActivity(callIntent);
                        }
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });

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
                    List<TicketData> filteredList = new ArrayList<>();
                    for (TicketData row : _items) {
                        if (row.getCustomerName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getDescription().toLowerCase().contains(charString.toLowerCase())
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
                _filteredItems = (ArrayList<TicketData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void updateList(List<TicketData> list) {
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
        public final ImageView telephone;
        public final ExpandableTextView descTextView;
        public final CardView card;
        public TicketData mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTxtCustomerName = view.findViewById(R.id.txt_customer_name);
            mTxtCustomerNo = view.findViewById(R.id.txt_customer_number);
            mTxtCustomerPlace = view.findViewById(R.id.txt_customer_place);
            mTxtOutstandingamount = view.findViewById(R.id.txt_customer_os_amount);
            mImgNameCircle = view.findViewById(R.id.img_letter_icon);
            telephone = view.findViewById(R.id.telephone);
            card = view.findViewById(R.id.card);
            descTextView = view.findViewById(R.id.descTextView);

        }
    }


}
