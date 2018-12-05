package com.calebco.caleb.prototype1;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.media.ThumbnailUtils.createVideoThumbnail;
import static android.provider.MediaStore.Video.Thumbnails.MICRO_KIND;
import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;
import static android.provider.MediaStore.Video.Thumbnails.getThumbnail;
import static android.support.constraint.Constraints.TAG;
import static android.view.View.inflate;


/**
 * A simple {@link Fragment} subclass.
 *
 * Work in progress
 *
 * let the user choose a list of pictures and videos
 * from device
 *
 * Compress and store as string is array lists
 *
 * send data to next activity (UploadActivity)
 *
 *
 *
 * Errors
 *
 *
 */
public class UploadFragment extends Fragment {

    private Button uploadButton;
    private Button videoFromDevice;
    private Button picFromDevice;
    private ImageView imageVideo;
    private static final int REQUEST_CODE = 101;
    private int checkBit = 0;
    private ListView videoListView;
    private ListView pictureListView;


    private List<VideoListClass> videoList = new ArrayList<>();
    private RecyclerView videoRecyclerView;
    private RecyclerView imageRecyclerView;
    private Adapter vAdapter;
    private Adapter pAdapter;
    private VideoListClass videoListClass;
    private List<VideoListClass> pictureList = new ArrayList<>();

    //MediaHelper mediaHelper = new MediaHelper();

    private ArrayList<String> videoStringList = new ArrayList<>();   //videoURI.toString()
    private ArrayList<String> videoStringThumb = new ArrayList<>(); //video bitmap thumbnail.compress byte[]
    private ArrayList<String> pictureStringBitmap = new ArrayList<>();  //picture bitmap.compress byte[]


    private Uri contentUri;

    public UploadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload, container, false);

        //VideoListClass videoListClass = new VideoListClass();

        //videoListView = view.findViewById(R.id.videoListView);
        //pictureListView = view.findViewById(R.id.pictureListView);
        videoRecyclerView = view.findViewById(R.id.videoRecyclerView);
        imageRecyclerView = view.findViewById(R.id.pictureRecyclerView);

        vAdapter = new Adapter(videoList);
        pAdapter = new Adapter(pictureList);


        uploadButton = view.findViewById(R.id.uploadButton);
        videoFromDevice = view.findViewById(R.id.videoFromDevice);
        picFromDevice = view.findViewById(R.id.picFromDevice);
        //imageVideo = view.findViewById(R.id.imageVideo);
        uploadButton.setEnabled(false);


        LinearLayoutManager vLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        videoRecyclerView.setLayoutManager(vLayoutManager);
        videoRecyclerView.setAdapter(vAdapter);

        LinearLayoutManager pLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        imageRecyclerView.setLayoutManager(pLayoutManager);
        imageRecyclerView.setAdapter(pAdapter);


        /**********************************************************
         * recordButton OnClick
         *
         * this is going to open the camera and allow the user to
         * record a video
         *
         * TODO*****************************************
         * ****
         *  we want to be able to save the video to the device before uploading
         *  so if uploading errors out the user doesn't lose that video
         *
         *  we may be able to work around this if we can find a way to use the
         *  camera app from the device maker i.e. Samsungs app
         * ****
         *
         *
         *
         **********************************************************/

        videoFromDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBit = 1;

                //Intent chooseVideo = new Intent(); //Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                //chooseVideo.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                //chooseVideo.setType("video/*");
                // chooseVideo.setAction(Intent.ACTION_GET_CONTENT);
                //startActivityForResult(Intent.createChooser(chooseVideo, "Selected Video"), REQUEST_CODE);
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("video/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("video/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Video");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, REQUEST_CODE);

            }
        });
        picFromDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBit = 2;
                //Intent chooseVideo = new Intent(); //Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                //chooseVideo.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                //chooseVideo.setType("image/*");
                //chooseVideo.setAction(Intent.ACTION_GET_CONTENT);
                //startActivityForResult(Intent.createChooser(chooseVideo,"Select Picture"), REQUEST_CODE);


                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, REQUEST_CODE);


            }
        });


        /**********************************************************
         * uploadButton OnClick
         *
         * this is going to send the recorded video to the server
         * under the current user id
         *
         * TODO: add progress bar for upload loading time
         *
         **********************************************************/
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent uploadIntent = new Intent(getActivity(), UploadActivity.class);
                Bundle b=new Bundle();
                b.putStringArray("pictureStringList",pictureStringBitmap);
                uploadIntent.putStringArrayListExtra("pictureStringList", pictureStringBitmap); //picture bitmaps
                uploadIntent.putStringArrayListExtra("videoUriList", videoStringList);    //video uri
                uploadIntent.putStringArrayListExtra("videoThumbList", videoStringThumb); //video thumbnail bitmaps
                //and picClass with bitmap and uri
                startActivity(uploadIntent);


            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //contentUri = data.getData();
        //Log.d("TAG", videoUri);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                contentUri = data.getData();
                //picture
                if (checkBit == 2) {
                    Uri imageUri = data.getData();
                    try {
                        InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);

                        Bitmap thumb = BitmapFactory.decodeStream(imageStream);
                        //imageVideo.setImageBitmap(thumb);

                        videoListClass = new VideoListClass();
                        videoListClass.setBitmap(thumb);
                        videoListClass.setVideoUri(imageUri);
                        pictureList.add(videoListClass);
                        pAdapter.notifyDataSetChanged();
                        MediaHelper2 mediaHelper = new MediaHelper2();
                        String pictureBitmap = mediaHelper.compressBitmap(thumb);

                       // FileOutputStream stream = null;
                        //try {
                          //  stream = getActivity().openFileOutput(pictureBitmap, Context.MODE_PRIVATE);
                        //} catch (FileNotFoundException e) {
                         //   e.printStackTrace();
                        //}
                        //thumb.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        pictureStringBitmap.add(pictureBitmap);
                        //try {
                          //  stream.close();
                        //} catch (IOException e) {
                         //   e.printStackTrace();
                        //}
                        //thumb.recycle();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                    //video
                } else if (checkBit == 1) {
                    Uri video = data.getData();

                    String[] filePathCol = {MediaStore.Video.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(video, filePathCol, null, null, null);
                    cursor.moveToFirst();
                    int colIndex = cursor.getColumnIndex(filePathCol[0]);
                    String videoPath = cursor.getString(colIndex);
                    cursor.close();

                    //Log.e("PATH: ", "File path: " + videoPath);

                    Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Video.Thumbnails.MINI_KIND);

                    //imageVideo.setImageBitmap(thumbnail);

                    videoListClass = new VideoListClass();
                    videoListClass.setBitmap(thumbnail);
                    videoListClass.setVideoUri(video);

                    videoList.add(videoListClass);
                    vAdapter.notifyDataSetChanged();
                    MediaHelper2 mediaHelper = new MediaHelper2();

                    String videoBitmap = mediaHelper.compressBitmap(thumbnail);


                    videoStringThumb.add(videoBitmap);                                    // string thumbnail list
                    videoStringList.add(video.toString());                             //string uri list
//MARK TODO*************
                    //making 2 List<String>, videoStringList, pictureStringList  put extra each list
                    //compressed bitmap list
                    //uri.toString list
                    //Cleanup




                }

                Toast.makeText(getContext(), "Upload Saved", Toast.LENGTH_SHORT).show();
                //realPath = getRealPathFromURI(getActivity(),videoUri);

                //thumbnail = getBitmap(contentUri);
                //thumbnail = createVideoThumbnail(realPath, MICRO_KIND);
                //imageVideo.setImageBitmap(thumbnail);   //or MINI_KIND
                uploadButton.setEnabled(true);

            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(getContext(), "Recording Canceled", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(getContext(), "Recording Failed", Toast.LENGTH_SHORT).show();

            }


        }
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.VideoHolder> {

        private List<VideoListClass> videoList;

        public class VideoHolder extends RecyclerView.ViewHolder {
            ImageView image;


            public VideoHolder(View view) {
                super(view);

                image = view.findViewById(R.id.imageListItem);

            }
        }


        public Adapter(List<VideoListClass> videoList) {
            this.videoList = videoList;
        }


        @NonNull
        @Override
        public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item, parent, false);

            return new VideoHolder(itemView);
        }

        @Override
        public void onBindViewHolder(VideoHolder holder, int position) {

            VideoListClass videoClass = videoList.get(position);
            holder.image.setImageBitmap(videoClass.getBitmap());


        }

        @Override
        public int getItemCount() {
            if (videoList.isEmpty()) {
                return 0;
            }
            else {
                return videoList.size();
            }
        }


    }
    public class MediaHelper2 {

        public String compressBitmap (Bitmap bitmap){
            String filename = "bitmap.png";
            FileOutputStream stream = null;
            try {
                stream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //bitmap.recycle();

            return filename;

        }
    }
}








