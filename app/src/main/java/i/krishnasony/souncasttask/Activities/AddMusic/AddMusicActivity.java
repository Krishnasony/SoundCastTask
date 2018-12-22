package i.krishnasony.souncasttask.Activities.AddMusic;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import i.krishnasony.souncasttask.MVVM.Navigator.Navigator;
import i.krishnasony.souncasttask.R;
import i.krishnasony.souncasttask.SongsListActivity;


public class AddMusicActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextsongtitle;
    Button buttonupload_thumnail, buttonuploadsong, buttonuploadmusic;
    Navigator clicllistener;
    String Songtitle, filePath, imageFilePath;
    Uri selectedImage, selectedMusic;
    private byte[] imageByte = null;
    private byte[] musicByte = null;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music);
        init();
        //back press
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        buttonuploadmusic.setOnClickListener(this);
        //upload thumbnail
        buttonupload_thumnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Image From Gallery"), 2);
            }
        });

        //upload music file
        buttonuploadsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkSelfPermission()){
                    Intent musicIntent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(Intent.createChooser(musicIntent, "Select Music From storage"), 3);

                }
                else {
                    requestPermission();
                }


            }
        });
    }

    private void init() {
        editTextsongtitle = findViewById(R.id.editsongtiltle);
        buttonupload_thumnail = findViewById(R.id.upload_thumbnail);
        buttonuploadsong = findViewById(R.id.choose_Music);
        buttonuploadmusic = findViewById(R.id.uploadmusic);
        toolbar = findViewById(R.id.playback_press);

    }

    @Override
    public void onClick(View view) {
        Songtitle = editTextsongtitle.getText().toString();
        uploadfile();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 2 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                selectedImage = data.getData();

//                String stringuri = selectedImage.toString();
                buttonupload_thumnail.setText(getFilename(selectedImage));
                imageByte = parseIntoByte(selectedImage);
            }
            if (requestCode == 3 && resultCode == RESULT_OK && null != data) {
                selectedMusic = data.getData();
                buttonuploadsong.setText(getFilename(selectedMusic));
                musicByte = parseIntoByte(selectedMusic);
            } else {
                Toast.makeText(this, "You haven't picked Image/AudioFile", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private void uploadfile() {
        final ParseObject entity = new ParseObject("songs_library");
        final ParseFile thumbnail = new ParseFile(buttonupload_thumnail.getText().toString(), imageByte);
        ParseFile audio = new ParseFile(buttonuploadsong.getText().toString(), musicByte);

        entity.put("title", Songtitle);
        entity.put("link", "lik");
        entity.put("thumbnail", buttonupload_thumnail.getText().toString());
        entity.put("music_file", new ParseFile(buttonuploadsong.getText().toString(), musicByte));
        entity.put("thumbnail_file", new ParseFile(buttonupload_thumnail.getText().toString(), imageByte));

        // Saves the new object.
        // Notice that the SaveCallback is totally optional!
        audio.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                // Here you can handle errors, if thrown. Otherwise, "e" should be null
                if(e == null){
                    thumbnail.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                entity.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if(e == null){
                                            Toast.makeText(AddMusicActivity.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(AddMusicActivity.this,SongsListActivity.class));


                                        }else {
                                            Toast.makeText(AddMusicActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }

            }
        });

    }

    public String getFilename(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        result = result.replaceAll("[^A-Za-z0-9.]","");
//        result = result.substring(0, result.length() - 4);

        return result;
    }

    //
    private byte[] parseIntoByte(Uri uri) throws IOException {

        InputStream is = null;
        try {
            is = getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();

    }

    private Boolean checkSelfPermission() {
        boolean flag = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED;
        } else {
            flag = true;
        }
        return flag;
    }



    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    3);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 3) {
            if (permissions.length > 0) {
                Intent musicIntent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(musicIntent, "Select Music From storage"), 3);


            } else {
                requestPermission();
            }
        }
    }
}