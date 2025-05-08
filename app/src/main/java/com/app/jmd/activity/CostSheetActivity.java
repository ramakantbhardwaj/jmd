package com.app.jmd.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jmd.AppPrefrences;
import com.app.jmd.R;
import com.app.jmd.adapterS.AdapterCostDesign;
import com.app.jmd.adapterS.DesignAdapter;
import com.app.jmd.adapterS.ManagerFilterAdapter;
import com.app.jmd.helper.ImageUtils;
import com.app.jmd.interfaces.CheckBoxInterface;
import com.app.jmd.interfaces.DesignCodeInterface;
import com.app.jmd.interfaces.FilterSpinner;
import com.app.jmd.interfaces.MyItemClickListener;
import com.app.jmd.mode.DesignDetailsMainModel;
import com.app.jmd.mode.GetDesignModel;
import com.app.jmd.mode.LstDesign;
import com.app.jmd.mode.LstParty;
import com.app.jmd.mode.UploadCostSheetModel;
import com.app.jmd.retrofit.APIClient;
import com.app.jmd.retrofit.API_Interface;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CostSheetActivity extends AppCompatActivity implements DesignCodeInterface, FilterSpinner, CheckBoxInterface {
    ImageView getPhoto;
    RecyclerView recyclerDesignName;
    API_Interface api_interface;
    ProgressDialog progressDialog;
    Call<GetDesignModel> getDesignModelCall;
    Call<DesignDetailsMainModel> designDetailsMainModelCall;
    Call<UploadCostSheetModel> uploadCostSheetModelCall;
    GetDesignModel getDesignModel;
    DesignDetailsMainModel designDetailsMainModel;
    UploadCostSheetModel uploadCostSheetModel;
    Context context;
    DesignAdapter designAdapter;
    LinearLayout linLaySelectImage;
    ImageView imageView2,imageView1,iv_back;
    AppCompatButton btn_upload;
    TextView tv_header;

    String imageUrl,imageUrl2;
    //////////////////////cam/////////////////////////
    private Bitmap bitmap;
    private String imgPath = null;
    private File destination = null;
    private InputStream inputStreamImg;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri photoURI;
    private static final int PERMISSION_REQUEST_CODE = 200;
    Uri selectedImage;
    String img="",designCode;
    String  currentPhotoPath;
    ProgressBar progressBar;
    String masterCode="";
    String masterName="";
    AdapterCostDesign adapterCostDesign;
    AlertDialog alertDialog;
    RelativeLayout relaLayoutManager;
    TextView tv_mangerPart_name;
    List<LstDesign> designCodeList = new ArrayList<>();
    List<LstDesign> designNameList = new ArrayList<>();
    boolean isChecedBox =false;
//    ArrayList<Uri> itemListUri;
    private static final int REQUEST_CODE_PERMISSIONS = 10;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_sheet);
        imageView2=findViewById(R.id.imageView2);
        imageView1=findViewById(R.id.imageView1);
        btn_upload=findViewById(R.id.btn_upload);
        progressBar=findViewById(R.id.progressBar);
        iv_back=findViewById(R.id.iv_back);
        tv_header=findViewById(R.id.tv_header);
        iv_back.setVisibility(View.VISIBLE);
        tv_header.setVisibility(View.VISIBLE);
        linLaySelectImage=findViewById(R.id.linLaySelectImage);
        tv_mangerPart_name=findViewById(R.id.tv_mangerPart_name);
        context = CostSheetActivity.this;

        recyclerDesignName=findViewById(R.id.recyclerDesignName);
        relaLayoutManager=findViewById(R.id.relaLayoutManager);
        showAllDesignData();
        Glide.with(context)
                .load(imageUrl2).diskCacheStrategy(DiskCacheStrategy.NONE) // No disk caching
                .skipMemoryCache(true)
                .placeholder(R.drawable.dummy_img) // Dummy image while loading
                .error(R.drawable.dummy_img).into(imageView2);

        linLaySelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CostSheetActivity.this, ZoomViewActivity.class);
                intent.putExtra("image_url", imageUrl); // Pass the image URL to the ZoomActivity
                intent.putExtra("productname", "Cost Sheet"); // Pass the image URL to the ZoomActivity
                startActivity(intent);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageUrl2 = "http://103.91.90.186/jmdapi/Images/CS_"+designCode+".jpg";
                Intent intent = new Intent(CostSheetActivity.this, ZoomViewActivity.class);
                intent.putExtra("image_url", imageUrl2);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("productname", "Cost Sheet"); // Pass the image URL to the ZoomActivity// Pass the image URL to the ZoomActivity
                startActivity(intent);
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img!=null) {
                    uploadCostSheet(designCode, img);
                }
                else {
                    Toast.makeText(context, "first select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MainActivity2.class));
                finish();
            }
        });
        tv_header.setText("Cost Sheet");
        relaLayoutManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CostSheetActivity.this);
                builder.setTitle("Search Design");
                LayoutInflater inflater = getLayoutInflater();
                View convertView = inflater.inflate(R.layout.alert_dialog_spinner_filter_layout, null);
                builder.setView(convertView);
                SearchView searchView = convertView.findViewById(R.id.searchView);
                searchView.setIconifiedByDefault(false);
                RecyclerView list = convertView.findViewById(R.id.recyclerViewCity);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapterCostDesign.getFilter().filter(newText);
                        return false;
                    }
                });
                list.setLayoutManager(new LinearLayoutManager(context));
                list.setHasFixedSize(true);
                adapterCostDesign = new AdapterCostDesign(designNameList, context);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                list.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(),
                        layoutManager.getOrientation());
                list.addItemDecoration(dividerItemDecoration);
                list.setAdapter(adapterCostDesign);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapterCostDesign.getFilter().filter("");
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapterCostDesign.getFilter().filter("");
                        masterCode = "";
                        masterName = "Search Design";
                        tv_mangerPart_name.setText(masterName);
                        dialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    ///////////////////////////cam///////////////////////////////////////////
    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle("Select Option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @RequiresApi(api = 33)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    dialog.dismiss();
                    if(checkPermission()){
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            File photoFile = null;
                            try {
                                photoFile = createImageFile();
                            } catch (IOException ex) {
                            }
                            if (photoFile != null) {
                                photoURI = FileProvider.getUriForFile(CostSheetActivity.this,
                                        getApplicationContext().getPackageName() + ".fileprovider",
                                        photoFile);
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                cameraActivityResultLauncher.launch(takePictureIntent);
                            }
                        }

                    }
                } else if (options[item].equals("Choose From Gallery")) {
                    dialog.dismiss();
                    if (checkPermission())
                    {
                        try {
                            Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                            intent.setType("image/*");
                            galleryActivityResultLauncher.launch(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {

                if (result.getResultCode() == RESULT_OK) {
                    img= ImageUtils.uriToBase64(photoURI,getContentResolver());
                    imageView2.setImageURI(photoURI);
                }

            });

    ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    inputStreamImg = null;
                    Intent data = result.getData();
                    if (data != null) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent pick = result.getData();
                            Uri selectedImage = pick.getData();
                            try {
//                                bitmap = MediaStore.Images.Media.getBitmap(AddDocumentAct.this.getContentResolver(), selectedImage);
//                                imgPath = getRealPathFromURI(selectedImage);
                                img= ImageUtils.uriToBase64(selectedImage,getContentResolver());
                                imageView2.setImageURI(selectedImage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            });


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((CostSheetActivity) context, Manifest.permission.CAMERA)) {
                androidx.appcompat.app.AlertDialog.Builder alertBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Permission necessary");
                alertBuilder.setMessage("CAMERA is necessary");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.CAMERA,
                                        Manifest.permission.READ_MEDIA_IMAGES,
                                        Manifest.permission.READ_MEDIA_VIDEO,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_MEDIA_AUDIO},
                                PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
            } else {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.READ_MEDIA_IMAGES,
                                Manifest.permission.READ_MEDIA_VIDEO,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.READ_MEDIA_AUDIO,},
                        PERMISSION_REQUEST_CODE);
            }
            return false;
        } else {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_MEDIA_IMAGES,
                            Manifest.permission.READ_MEDIA_VIDEO,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_MEDIA_AUDIO,},
                    PERMISSION_REQUEST_CODE);
        }
        return  true;
    }
    ///////////////////////////cam/////////////////////////////////////////

    private void showAllDesignData() {
        ProgressDialog pd=new ProgressDialog(CostSheetActivity.this);
        pd.show();
        pd.setMessage("Please Wait....");
        api_interface = APIClient.getService();
        getDesignModelCall = api_interface.getDesignApi("","");
        getDesignModelCall.enqueue(new Callback<GetDesignModel>() {
            @Override
            public void onResponse(@NonNull Call<GetDesignModel> call, @NonNull Response<GetDesignModel> response) {
                assert response.body() != null;
                boolean status = response.body().getStatus();
                String msg = response.body().getMessage();
                getDesignModel = response.body();
                if (status) {
                    if(!getDesignModel.getLstDesign().isEmpty()){
//                        imageUrl = "http://103.91.90.186/jmdapi/Images/CS_"+getDesignModel.getLstDesign().get(0).getDesigncode()+".jpg";
//                        imageUrl2 = "http://103.91.90.186/jmdapi/Images/CS_"+getDesignModel.getLstDesign().get(0).getDesigncode()+".jpg";
                        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CostSheetActivity.this);
                        recyclerDesignName.setLayoutManager(linearLayoutManager);
                        designAdapter = new DesignAdapter(getDesignModel, CostSheetActivity.this);
                        recyclerDesignName.setAdapter(designAdapter);
                        designNameList.clear();
                        designCodeList.clear();
                        designNameList.addAll(getDesignModel.getLstDesign());
                        designCodeList.addAll(getDesignModel.getLstDesign());
                        showAllDesignDetailData(getDesignModel.getLstDesign().get(0).getDesigncode());
                        pd.dismiss();
                    }else{
                        Toast.makeText(CostSheetActivity.this, "Record not Available", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }else {
                    pd.dismiss();
                    Toast.makeText(CostSheetActivity.this, "Record not Available", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<GetDesignModel> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        });

    }
    private void showAllDesignDetailData(String designCode) {
        ProgressDialog pd=new ProgressDialog(CostSheetActivity.this);
        pd.show();
        pd.setMessage("Please Wait....");
        api_interface = APIClient.getService();
        designDetailsMainModelCall = api_interface.getDesignDetails(designCode);
        designDetailsMainModelCall.enqueue(new Callback<DesignDetailsMainModel>() {
            @Override
            public void onResponse(@NonNull Call<DesignDetailsMainModel> call, @NonNull Response<DesignDetailsMainModel> response) {
                assert response.body() != null;
                boolean status = response.body().getStatus();
                String msg = response.body().getMessage();
                designDetailsMainModel = response.body();
                if (status) {
                    if(!designDetailsMainModel.getLstCostDetail().isEmpty()){
                        imageUrl = "http://103.91.90.186/jmdapi/Images/"+designDetailsMainModel.getLstCostDetail().get(0).getCostsheetpath();
                        imageUrl2 = "http://103.91.90.186/jmdapi/Images/"+designDetailsMainModel.getLstCostDetail().get(0).getDesignpath();
                        Log.d("imaggege", imageUrl+imageUrl2);
//                        Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
                        Glide.with(context)
                                .load(imageUrl2)
                                .placeholder(R.drawable.dummy_img) // Dummy image while loading
                                .error(R.drawable.dummy_img) // Dummy image if there's an error
                                .into(imageView1);
//
                        Glide.with(context)
                                .load(imageUrl)
                                .placeholder(R.drawable.dummy_img) // Dummy image while loading
                                .error(R.drawable.dummy_img) // Dummy image if there's an error
                                .into(imageView2);

                        pd.dismiss();
                    }else{
                        Toast.makeText(CostSheetActivity.this, "Record not Available", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }else {
                    pd.dismiss();
                    Toast.makeText(CostSheetActivity.this, "Record not Available", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<DesignDetailsMainModel> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        });

    }
    private void uploadCostSheet(String designCode, String img) {
        ProgressDialog pd=new ProgressDialog(CostSheetActivity.this);
        pd.show();
        pd.setMessage("Please Wait....");
        api_interface = APIClient.getService();
        uploadCostSheetModelCall = api_interface.uploadCostSheet(designCode,img);
        uploadCostSheetModelCall.enqueue(new Callback<UploadCostSheetModel>() {
            @Override
            public void onResponse(@NonNull Call<UploadCostSheetModel> call, @NonNull Response<UploadCostSheetModel> response) {
                assert response.body() != null;
                boolean status = response.body().getStatus();
                String msg = response.body().getMessage();
                uploadCostSheetModel = response.body();
                if (status) {
                    Toast.makeText(CostSheetActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                    Intent intent = getIntent();
                    finish(); // Finish the current activity
                    startActivity(intent);
                }else {
                    pd.dismiss();
                    Toast.makeText(CostSheetActivity.this, "Record not Available", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<UploadCostSheetModel> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        });

    }



    @Override
    public void getPosAndCode(int pos, String code) {
        designCode=code;
            imageUrl2 = "http://103.91.90.186/jmdapi/Images/CS_"+designCode+".jpg";
        progressBar.setVisibility(View.VISIBLE);

        Glide.with(context)
                    .load(imageUrl2).diskCacheStrategy(DiskCacheStrategy.NONE) // No disk caching
                .skipMemoryCache(true)
                .placeholder(R.drawable.dummy_img) // Dummy image while loading
                    .error(R.drawable.dummy_img).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(imageView2);


    }

    @Override
    public void customerFilter(String partyCode, String partyName) {

    }

    @Override
    public void supplierFilter(String partyCode, String partyName) {

    }

    @Override
    public void marketerFilter(String partyCode, String partyName) {

    }

    @Override
    public void sizeFilter(String partyCode, String partyName) {
        if (partyCode.length() > 0) {
            masterCode = partyCode;
            masterName = partyName;
            adapterCostDesign.getFilter().filter("");
            alertDialog.dismiss();
            tv_mangerPart_name.setText(masterName);
            imageUrl2 = "http://103.91.90.186/jmdapi/Images/CS_"+designCode+".jpg";
            progressBar.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load(imageUrl2).diskCacheStrategy(DiskCacheStrategy.NONE) // No disk caching
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.dummy_img) // Dummy image while loading
                    .error(R.drawable.dummy_img).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(imageView2);
            isChecedBox=true;

        }

    }

    @Override
    public void getCheced(int pos, boolean isChecked) {
        isChecedBox=isChecked;
    }
}
