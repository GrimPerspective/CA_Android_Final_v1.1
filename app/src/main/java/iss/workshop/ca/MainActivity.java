package iss.workshop.ca;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText urlInput;
    private Button fetchBtn;
    private String url;
    private TextView selectText;
    private Button startGameBtn;
    private ProgressBar progressBar;
    private TextView progressText;
    private boolean inactive = true;

    public List<String> imgLinks = new ArrayList<>();
    private List<ImageView> imgDL = new ArrayList<>(); // downloaded images
    private static final int imgForGame = 6;
    private List<ImageView> imgPick = new ArrayList<>();
    private ArrayList<String> imgSelected = new ArrayList<>();
    private downloadImg currentTask;

    private static final String IMG_URL = "<img.*src=\"(.*?)\"";
    private static final String IMG_SRC = "[a-zA-z]+://[^\\s]*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // to enable network calls on the main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        for (int i = 1; i <= 20; i++) {
            String imageID = "image" + i;
            int resID = getResources().getIdentifier(imageID, "id", getPackageName());
            imgDL.add(findViewById(resID));
        }

        urlInput = findViewById(R.id.urlInput);
        url = urlInput.getText().toString();

        fetchBtn = findViewById(R.id.fetchBtn);
        fetchBtn.setOnClickListener(view -> getImgUrl());

        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);
        selectText = findViewById(R.id.selectText);

        startGameBtn = findViewById(R.id.startGameBtn);

        for (int i = 0; i < 20; i++) {
            ImageView selected = imgDL.get(i);
            int num = i;
            selected.setOnClickListener(v -> {
                if (inactive) {
                    return;
                }

                selectText.setVisibility(View.VISIBLE);
                startGameBtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                progressText.setVisibility(View.GONE);

                if (num < imgLinks.size()) {
                    //after image download completed, the tag of image set null as default.
                    if (selected.getTag() != null){
                        selected.setForeground(getDrawable(R.drawable.img_border));
                        selected.setTag(null);
                        imgPick.remove(selected);
                        imgSelected.remove(imgLinks.get(num));
                        selectText.setText(imgPick.size() + " of " + imgForGame + " images selected");
                    } else {
                        if (imgPick.size() < imgForGame) {
                            selected.setForeground((getDrawable(R.drawable.img_selected)));
                            selected.setTag("selected");
                            imgPick.add(selected);
                            imgSelected.add(imgLinks.get(num));
                            selectText.setText(imgPick.size() + " of " + imgForGame + " images selected");
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Invalid Selection!",Toast.LENGTH_SHORT).show();
                }
            });
        }


//      Send url list to gameactivity
        startGameBtn.setOnClickListener(v -> {
            if (imgPick.size() == imgForGame) {
//                finish();
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("imgSelectedUrl", imgSelected);
                startActivity(intent);
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(),"Select 6 images!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getImgUrl() {
        inactive = false;

        if (!url.isEmpty() && url.equals(urlInput.getText().toString()))
            return;
        // input a new url
        url = urlInput.getText().toString();

        // check url
        if (!url.isEmpty()) {
            hideKeyboard(MainActivity.this);
            String newUrl;


            if (url.startsWith("http:")) {
                newUrl = "https:" + url.substring(6);
            } else if (!url.startsWith("https://")) {
                newUrl = "https://" + url;
            } else {
                newUrl = url;
            }

            if (currentTask != null) {
                // cancel current task if running
                currentTask.cancel(true);
            }

            //start new task
            currentTask = new downloadImg(newUrl);
            currentTask.execute();
            selectText.setVisibility(View.GONE);
            startGameBtn.setVisibility((View.GONE));
            imgPick.clear();
            imgSelected.clear();
            selectText.setText("Select " + imgPick.size() + "/" + imgForGame + " images");
            for (int i = 0; i < 20; i++) {
                ImageView selected = imgDL.get(i);
                selected.setForeground(getDrawable(R.drawable.img_border));
                selected.setTag(null);
            }
        } else
            Toast.makeText(this, "Please Enter a URL", Toast.LENGTH_SHORT).show();
    }

    public class downloadImg extends AsyncTask<Void, Void, Void> {
        private String url;

        public downloadImg(String urlInput) {
            this.url = urlInput;
        }

        @Override
        protected Void doInBackground(Void... params) {
            imgLinks.clear();
            try {
                URL Url = new URL(url);
                URLConnection urlConnection = Url.openConnection();
                urlConnection.setRequestProperty("User-Agent", "Chrome/61.0.3163.100)");
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                StringBuffer sb = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    sb.append(line, 0, line.length());
                    sb.append('\n');
                }
                reader.close();
                inputStream.close();
                Matcher matcher = Pattern.compile(IMG_URL).matcher(sb.toString());
                List<String> listimgurl = new ArrayList<>();
                while (matcher.find()) {
                    listimgurl.add(matcher.group());
                }
                int counter = 0;
                runOnUiThread(() -> {
                    //download the url of jpg
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(0);
                    progressText.setVisibility(View.VISIBLE);
                    progressText.setText("Fetching images");
                    //reset all images to that empty
                    for (ImageView image : imgDL) {
                        image.setImageResource(R.drawable.empty);
                    }
                });
                for (String imgurl : listimgurl) {
                    //replace the default empty.png by download image
                    Matcher matc = Pattern.compile(IMG_SRC).matcher(imgurl);
                    while (matc.find()) {
                        if (isCancelled()){
                            break;
                        }
                        imgLinks.add(matc.group().substring(0, matc.group().length() - 1));
                        Bitmap image = BitmapFactory.decodeStream((InputStream) new URL(imgLinks.get(counter)).getContent());
                        if (image != null) {
                            final int threadCounter1 = counter;
                            runOnUiThread(() -> {
                                imgDL.get(threadCounter1).setImageBitmap(image);
                                progressBar.incrementProgressBy(5);
                                progressText.setText("Downloading " + (threadCounter1 + 1) +
                                        " of 20 images");
                            });
                            counter += 1;
                        }
                    }
                    //incase of above 20 image.
                    if (imgLinks.size() == imgDL.size()) {
                        break;
                    }
                }
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
            Void v = null;
            return v;
        }
        @Override
        protected void onPostExecute(Void v) {
            //set progress to 100 to cater for url with less than 20 images
            progressBar.setProgress(100);
            progressText.setText("Download completed! Please select images");
        }

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}