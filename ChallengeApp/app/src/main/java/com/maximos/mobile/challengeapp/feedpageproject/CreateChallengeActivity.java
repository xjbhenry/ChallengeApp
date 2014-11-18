package com.maximos.mobile.challengeapp.feedpageproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.maximos.mobile.challengeapp.FetchFriends.FriendPickerSampleActivity;
import com.maximos.mobile.challengeapp.R;
import com.maximos.mobile.challengeapp.constants.App_Constants;
import com.maximos.mobile.challengeapp.dao.ChallengeDao;
import com.maximos.mobile.challengeapp.model.Challenge;
import com.maximos.mobile.challengeapp.util.RecordAudio;
import com.maximos.mobile.challengeapp.util.UploadFile;
import com.maximos.mobile.challengeapp.util.VideoCapture;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateChallengeActivity extends Activity {

    private static final int SELECT_AUDIO = 2;
    private static final int SELECT_VIDEO = 3;
    private static final int SELECT_IMAGE = 1;
    String selectedPath = "";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    ImageView mImageView;
    private static int responseCode = 0;
    String fileOnServer = "";

    public Logger logger = Logger.getLogger(CreateChallengeActivity.class.getName());
    private static final String TAG_NAME = CreateChallengeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);
        ((Button) findViewById(R.id.recordAudio)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Audio "), SELECT_AUDIO);
                */
                Intent intent;
                intent = new Intent(CreateChallengeActivity.this, RecordAudio.class);
                startActivity(intent);


            }
        });
        ((Button) findViewById(R.id.takePicture)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File

                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }
                }


            }
        });
        ((Button) findViewById(R.id.uploadVideo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Video "), SELECT_VIDEO);
                */
                Intent intent;
                intent = new Intent(CreateChallengeActivity.this, VideoCapture.class);
                startActivity(intent);


            }
        });
        ((Button) findViewById(R.id.selectFriend)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(CreateChallengeActivity.this, FriendPickerSampleActivity.class);
                startActivity(intent);

            }
        });
        ((Button) findViewById(R.id.upload_audio)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Audio "), SELECT_AUDIO);
            }
        });
        ((Button) findViewById(R.id.upload_video)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Video "), SELECT_VIDEO);
            }
        });
        ((Button) findViewById(R.id.upload_image)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image "), SELECT_IMAGE);
            }
        });
        ((Button) findViewById(R.id.location)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ((Button) findViewById(R.id.create_challenge)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateChallengeTask createChallengeAsyncTask = new CreateChallengeTask();
                createChallengeAsyncTask.execute((Void) null);
            }
        });
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onPause() {
        logger.log(Level.INFO, "Inside onPause of Create Challenge class Activity");
        super.onPause();
    }

    @Override
    protected void onStop() {
        logger.log(Level.INFO, "Inside OnStop in Create Challenge Class Activity ");
        super.onStop();
    }

    @Override
    protected void onStart() {
        logger.log(Level.INFO, "Inside Onstart in Create challenge class activity");
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        logger.log(Level.INFO, "Inside OnDestroy in create challenge class activity");
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_VIDEO) {
                logger.log(Level.INFO, TAG_NAME + ": Upload Video with Uri" + data.getData());
                Uri selectedVideoUri = data.getData();
                selectedPath = getPath(selectedVideoUri);
                logger.log(Level.INFO, TAG_NAME + ": path selected" + selectedPath);
                FileUploadAsyncTask fileUploadAsyncTask = new FileUploadAsyncTask(selectedPath);
                fileUploadAsyncTask.execute((Void) null);
            } else if (requestCode == SELECT_AUDIO) {
                logger.log(Level.INFO, TAG_NAME + ": Upload Audio with Uri" + data.getData());
                Uri selectedAudioUri = data.getData();
                selectedPath = getPath(selectedAudioUri);
                logger.log(Level.INFO, TAG_NAME + ": path selected" + selectedPath);
                FileUploadAsyncTask fileUploadAsyncTask = new FileUploadAsyncTask(selectedPath);
                fileUploadAsyncTask.execute((Void) null);
            } else if (requestCode == SELECT_IMAGE) {
                logger.log(Level.INFO, TAG_NAME + ": Upload Image with Uri" + data.getData());
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
                logger.log(Level.INFO, TAG_NAME + ": path selected" + selectedPath);
                FileUploadAsyncTask fileUploadAsyncTask = new FileUploadAsyncTask(selectedPath);
                fileUploadAsyncTask.execute((Void) null);
            }
            if (requestCode == 200) {
                TextView textView = (TextView) findViewById(R.id.uploadTextResult);
                textView.setText(App_Constants.DATA_UPLOADED);
            }
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                setPic();
                galleryAddPic();
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                mImageView.setImageBitmap(imageBitmap);

                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

    public String getPath(Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            logger.log(Level.INFO, TAG_NAME + " : cursor" + cursor);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_challenge, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class FileUploadAsyncTask extends AsyncTask<Void, Void, Object> {
        private String selectedPath;

        private ProgressDialog pd;

        FileUploadAsyncTask(String selectedPath) {
            this.selectedPath = selectedPath;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(CreateChallengeActivity.this);
            pd.setTitle("Uploading File..");
            pd.setMessage("Please wait,File is getting sent");
            pd.setCancelable(true);
            pd.setIndeterminate(true);
            pd.show();
        }

        @Override
        protected Object doInBackground(Void... params) {
            UploadFile uploadFile = new UploadFile();
            responseCode = uploadFile.uploadVideo(selectedPath);
            fileOnServer = uploadFile.fileOnServer;
            logger.log(Level.INFO, TAG_NAME + " : file on server " + fileOnServer);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            pd.dismiss();
        }
    }

    public class CreateChallengeTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(CreateChallengeActivity.this);
            pd.setTitle("Creating Challenge..");
            pd.setMessage("Please wait, Creating Challenge ");
            pd.setCancelable(true);
            pd.setIndeterminate(true);
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            String creatorId = "";
            EditText titleEditText = (EditText) findViewById(R.id.title);
            String title = titleEditText.getText().toString();
            EditText descEditText = (EditText) findViewById(R.id.challenge_desc);
            String desc = titleEditText.getText().toString();
            SharedPreferences prefs = getSharedPreferences(App_Constants.USER_PREFERENCE_FILE, getApplicationContext().MODE_PRIVATE);
            Boolean isLoggedIn = prefs.getBoolean(App_Constants.IS_USER_LOGGED_IN, false);
            logger.log(Level.INFO, " : " + isLoggedIn);
            if (isLoggedIn) {
                creatorId = prefs.getString(App_Constants.LOGGED_USER_ID, "none");
                Challenge challenge = new Challenge(title, desc, 1, fileOnServer, null, null, creatorId);
                ChallengeDao.createChallenge(challenge);
            }
            pd.dismiss();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}