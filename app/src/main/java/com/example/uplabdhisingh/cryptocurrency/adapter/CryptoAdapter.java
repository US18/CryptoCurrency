package com.example.uplabdhisingh.cryptocurrency.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uplabdhisingh.cryptocurrency.R;
import com.example.uplabdhisingh.cryptocurrency.model.CryptoDetails;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by uplabdhisingh on 15/03/18.
 */

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.CryptoAdapterViewHolder>
{

    private List<CryptoDetails> cryptoDetailsList;
    private Context mContext;
    private int rowLayoutCrypto;
    private final String TAG=CryptoAdapter.class.getSimpleName();

    public CryptoAdapter(Context cryptoContext,List<CryptoDetails> cryptoDetails,int rowLayout)
    {
        mContext=cryptoContext;
        cryptoDetailsList=cryptoDetails;
        rowLayoutCrypto=rowLayout;
    }

    @Override
    public CryptoAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        int layoutID = R.layout.currencies_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutID,viewGroup,false);
        CryptoAdapterViewHolder cryptoAdapterViewHolder = new CryptoAdapterViewHolder(view);
        return cryptoAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(CryptoAdapterViewHolder holder, int position)
    {
        CryptoDetails cryptoDetailsClickedPosition = cryptoDetailsList.get(position);
        holder.name.setText(cryptoDetailsClickedPosition.getName());
        holder.symbol.setText(cryptoDetailsClickedPosition.getSymbol());
        holder.rank.setText(cryptoDetailsClickedPosition.getRank());
        holder.price.setText(cryptoDetailsClickedPosition.getPriceUsd());
        holder.percent.setText(cryptoDetailsClickedPosition.getPercentChange1h());
    }

    @Override
    public int getItemCount()
    {
     if(cryptoDetailsList==null)
     {
         return 0;
     }
     return cryptoDetailsList.size();
    }

    public class CryptoAdapterViewHolder extends RecyclerView.ViewHolder
    {

        public TextView name,symbol,rank,price,percent;

        public CryptoAdapterViewHolder(View itemView)
        {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.tv_currency_name);
            symbol=(TextView) itemView.findViewById(R.id.tv_currency_symbol);
            rank=(TextView) itemView.findViewById(R.id.tv_currency_rank);
            price=(TextView) itemView.findViewById(R.id.tv_currency_price);
            percent=(TextView) itemView.findViewById(R.id.tv_currency_percent_change);
        }
    }

    public void setData(List<CryptoDetails> cryptoDetails)
    {
        cryptoDetailsList=cryptoDetails;
        notifyDataSetChanged();
    }
}
