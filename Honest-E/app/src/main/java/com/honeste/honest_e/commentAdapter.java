package com.honeste.honest_e;

import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Bitmap;
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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
        import com.android.volley.toolbox.Volley;
        import com.honeste.honest_e.commonclasses.CommonURL;
        import com.honeste.honest_e.commonclasses.common_functions;

        import java.util.ArrayList;

        import static android.content.Context.MODE_PRIVATE;

public class commentAdapter extends BaseAdapter {
    int Flag_profile;
    ArrayList<commonComplain> commonComplains;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public commentAdapter(Context context, ArrayList<commonComment> Commoncomments){
        this.context = context;
        this.Commoncomments = Commoncomments;

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

    int commentid;

    Context context;
    ArrayList<commonComment> Commoncomments;


    class Holder1
    {
        com.honeste.honest_e.CircularImageView circularImageView;
        TextView textView1,textView2,textView3;
        ImageButton setting;
    }
    @Override
    public int getCount() {
        return Commoncomments.size();
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

        final Holder1 holder;
        final int position = i;

        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.activity_comment,null);

        holder=new Holder1();

        //name
        holder.textView1=(TextView)view.findViewById(R.id.tvCommentName);
        //holder.textView1.setText(name);
        holder.textView1.setText(Commoncomments.get(i).getName());

        //time
        holder.textView2=(TextView)view.findViewById(R.id.tvCommentTime);
        //holder.textView2.setText(time);
        holder.textView2.setText(Commoncomments.get(i).getTime());

        //Complaint Description
        holder.textView3=(TextView)view.findViewById(R.id.tvComment);
        //holder.textView3.setText(description);
        holder.textView3.setText(Commoncomments.get(i).getContent());

        holder.circularImageView = (com.honeste.honest_e.CircularImageView)view.findViewById(R.id.comment_propic);
        int ridimg = Commoncomments.get(i).getRid_comment();
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

        //Settings Button

        holder.setting = (ImageButton)view.findViewById(R.id.btnCommentOptions);
        holder.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rid_user,rid_comment;
                rid_user = Commoncomments.get(position).getRid_user();
                rid_comment = Commoncomments.get(position).getRid_comment();
                if (rid_user == rid_comment) {

                    PopupMenu popup = new PopupMenu(context, holder.setting);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                    //registering popup with OnMenuItemClickListener

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()){

                                case R.id.edit:
                                    {

                                    Intent intent = new Intent(context, Edit_Comment.class);
                                    commentid = Commoncomments.get(position).getCommentid();
                                    String temp = String.valueOf(commentid);
                                    String data = Commoncomments.get(position).getContent();
                                    int data2 = Commoncomments.get(position).getComplaintid();
                                    String data3 = String.valueOf(data2);
                                    intent.putExtra("commentid", temp);
                                    intent.putExtra("content",data);
                                    intent.putExtra("complaintid",data3);
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

                                                    commentid = Commoncomments.get(position).getCommentid();
                                                    common_functions cf2 = new common_functions();
                                                    int result =  cf2.deletecomment(commentid);

                                                    if (result == 1)
                                                    {
                                                        /*Intent intent = new Intent(context,HomeUI.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                        context.startActivity(intent);
                                                        ((Activity)context).finish();*/
                                                        Intent intent = ((Activity)context).getIntent();
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                        //((Activity)context).overridePendingTransition(0, 0);
                                                        ((Activity)context).finish();
                                                        context.startActivity(intent);

                                                        Toast.makeText(context, "Comment Deleted Successfully", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(context,"Reported comment to administrator", Toast.LENGTH_SHORT).show();
                                    return true;
                            }
                            //Toast.makeText(context, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
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

                //Toast.makeText(view.getContext(), "clicked Settings done by " + Commoncomments.get(position).getCommentid(), Toast.LENGTH_SHORT).show();
            }
        });
    return view;
    }

     }
