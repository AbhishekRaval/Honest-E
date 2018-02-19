package com.honeste.honest_e;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.honeste.honest_e.commonclasses.CommonURL;
import com.honeste.honest_e.commonclasses.common_functions;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by abhis on 10-Mar-17.
 */

public class complainAdapter extends BaseAdapter {
    int flag = 0,flag_like = 0;
    Context context;
    int compid;
    int Flag_profile;
    ArrayList<commonComplain> commonComplains;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    class Holder
    {
        com.honeste.honest_e.CircularImageView circularImageView;
        NetworkImageView compdetails;
        TextView textView1,textView2,textView3,textView4,textView5,textView6;
        ImageButton plusone,comment,setting;
    }



    public complainAdapter(Context context, ArrayList<commonComplain> commonComplains) {
        this.context=context;
        this.commonComplains=commonComplains;

        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    @Override
    public int getCount() {
        return commonComplains.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final  Holder holder;
        final int position = i;

        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.complain,null);

        holder=new Holder();

        holder.circularImageView = (com.honeste.honest_e.CircularImageView)view.findViewById(R.id.complaint_profilepic);

        holder.compdetails = (NetworkImageView)view.findViewById(R.id.complain_Description);
        String pathcomp = commonComplains.get(i).getCompimgpath();
        if(pathcomp.isEmpty())
        {
           // pathcomp="Default_Complaint";
           // CommonURL cp = new CommonURL();
            //String pathcompfinal = cp.getPathIp(pathcomp);
            //holder.compdetails.setImageUrl(pathcompfinal, mImageLoader);
            holder.compdetails.setDefaultImageResId(R.drawable.default_complaint);
        }
        else {
            flag = 1;
            CommonURL cp = new CommonURL();
            String pathcompfinal = cp.getPathIp(pathcomp);
            holder.compdetails.setImageUrl(pathcompfinal, mImageLoader);
        }


        int ridimg = commonComplains.get(i).getComplaint_rid();
        common_functions allfuncts = new common_functions();
        Flag_profile = allfuncts.checkImgset(ridimg);
        if (Flag_profile == 0)
        {
            holder.circularImageView.setDefaultImageResId(R.drawable.ic_profile);
        }
        else
        {
            String imgname = allfuncts.getprofileimagename(ridimg);
            CommonURL c1 = new CommonURL();
            String server_url = c1.getProfilePathdetailIP(imgname);
            holder.circularImageView.setImageUrl(server_url, mImageLoader);
        }

        //name
        holder.textView1=(TextView)view.findViewById(R.id.tvName);
        holder.textView1.setText(commonComplains.get(i).getName());

        //Category
        holder.textView2=(TextView)view.findViewById(R.id.tvCategory);
        holder.textView2.setText(commonComplains.get(i).getCategory());

        //Area
        holder.textView3=(TextView)view.findViewById(R.id.tvArea);
        holder.textView3.setText(commonComplains.get(i).getArea());

        //time
        holder.textView4=(TextView)view.findViewById(R.id.tvTime);
        holder.textView4.setText(commonComplains.get(i).getHours());

        //Complaint Description
        holder.textView5=(TextView)view.findViewById(R.id.tvComplaintDes);
        holder.textView5.setText(commonComplains.get(i).getDesc());

        //subcategory
        holder.textView6 = (TextView)view.findViewById(R.id.tvSubCategory);
        holder.textView6.setText(commonComplains.get(i).getSubcategory());

        holder.plusone = (ImageButton) view.findViewById(R.id.onPlus);

        final common_functions Likecheck = new common_functions();
        final int Like_userrid = commonComplains.get(i).getLogin_rid();
        final int Like_complainid = commonComplains.get(i).getCompid();
        flag_like = Likecheck.issetLiked(Like_userrid,Like_complainid);
        if (flag_like == 1)
        {
            holder.plusone.setImageResource(R.drawable.ic_liked_btn);
        }

        holder.plusone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag_like = Likecheck.issetLiked(Like_userrid,Like_complainid);
                if(flag_like == 0)
                {
                    int response1 = Likecheck.InsertLike(Like_userrid,Like_complainid);
                    if (response1 == 0)
                    {
                        Toast.makeText(context,"Failed to like", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        holder.plusone.setImageResource(R.drawable.ic_liked_btn);
                    }
                }

                else if (flag_like == 1)
                {
                    int response2 = Likecheck.deletelike(Like_complainid,Like_userrid);
                    if (response2 == 0)
                    {
                        Toast.makeText(context,"Failed to delete like", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        holder.plusone.setImageResource(R.drawable.ic_action_name);
                    }
                }

                //Toast.makeText(view.getContext(),"Clicked plusone", Toast.LENGTH_SHORT).show();
            }
        });

        holder.comment = (ImageButton) view.findViewById(R.id.onComment);
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "clicked comment", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,comment_list.class);
                compid = commonComplains.get(position).getCompid();
                String temp = String.valueOf(compid);
                intent.putExtra("id",temp);
                context.startActivity(intent);
            }
        });

        holder.setting = (ImageButton)view.findViewById(R.id.onSettings);
        holder.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rid_user,rid_comment;
                rid_user = commonComplains.get(position).getLogin_rid();
                rid_comment = commonComplains.get(position).getComplaint_rid();
                if (rid_user == rid_comment) {
                    PopupMenu popup = new PopupMenu(context, holder.setting);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.edit: {
                                    Intent intent = new Intent(context, Edit_Complaint.class);
                                    compid = commonComplains.get(position).getCompid();
                                    String temp = String.valueOf(compid);
                                    intent.putExtra("compid", temp);
                                    context.startActivity(intent);
                                }
                                    return true;
                                case R.id.delete:{
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                    builder1.setMessage("Are you sure, you want to delete this Complaint?");
                                    builder1.setCancelable(true);

                                    builder1.setPositiveButton(
                                            "Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    compid = commonComplains.get(position).getCompid();
                                                    common_functions cf2 = new common_functions();
                                                    int result =  cf2.deletecomplaint(compid);
                                                    int result2 = cf2.deletecommentforcompdelete(compid);
                                                    int result4 = cf2.deletelikesforcomment(compid);
                                                    if (flag == 1)
                                                    {
                                                        int result3 = cf2.delCompImgCID(compid);
                                                    }

                                                    if (result == 1 && result2 == 1)
                                                    {
                                                       /* Intent intent = new Intent(context,HomeUI.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                        context.startActivity(intent);
                                                        ((Activity)context).finish();*/

                                                        Intent intent = ((Activity)context).getIntent();
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                        ((Activity)context).finish();
                                                        context.startActivity(intent);
                                                        Toast.makeText(context, "Complaint Deleted Successfully", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(context, "Failed to delete complaint", Toast.LENGTH_SHORT).show();
                                                    }
                                                    notifyDataSetChanged();
                                                    dialog.cancel();
                                                }
                                            });

                                    builder1.setNegativeButton(
                                            "No",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();
                                }
                                    return true;
                                case R.id.report:
                                    common_functions cfunt = new common_functions();
                                    int repid = cfunt.insertReport(Like_userrid,Like_complainid);
                                    Toast.makeText(context, "Reported to Administrator", Toast.LENGTH_SHORT).show();

                                    return true;
                            }
                            Toast.makeText(context, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
                    popup.show();//showing popup menu
                }

                else {
                    PopupMenu popupMenu = new PopupMenu(context,holder.setting);
                    popupMenu.getMenuInflater().inflate(R.menu.pop_menu_2,popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(context, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            }
        });


        return view;
    }
}
