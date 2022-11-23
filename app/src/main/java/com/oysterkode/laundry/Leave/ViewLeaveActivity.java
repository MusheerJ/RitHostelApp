package com.oysterkode.laundry.Leave;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.oysterkode.laundry.R;
import com.oysterkode.laundry.databinding.ActivityViewLeaveBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ViewLeaveActivity extends AppCompatActivity {

    private ActivityViewLeaveBinding binding;
    private Leave selectedLeave;
    private String fName;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewLeaveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        selectedLeave = (Leave) getIntent().getSerializableExtra("selected_leave");
        getSupportActionBar().hide();


        setLeave();

        binding.backButton.setOnClickListener(v -> finish());
        fName = selectedLeave.getStudentName() + selectedLeave.getFrom();


        binding.leaveDownload.setOnClickListener(view -> {

//            if (checkPermission()) {
//                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
//
//                layoutTOimageConverter();
//
//            } else {
//                requestPermission();
//            }


            try {
                createPDF();
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }


        });


    }

    private void layoutTOimageConverter() {


        Dexter.withContext(this).withPermissions(WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

//                            ConstraintLayout layout = findViewById(R.id.lay);

//                            ScrollView layout = findViewById(R.id.sView);
                            LinearLayout layout = findViewById(R.id.leaveLayout);


                            File file = saveBitMap(ViewLeaveActivity.this, layout);    //which view you want to pass that view as parameter
                            if (file != null) {
                                Log.i("TAG", "Drawing saved to the gallery!");
                                Toast.makeText(ViewLeaveActivity.this, "Processing", Toast.LENGTH_SHORT).show();


                                try {
                                    imageToPDF();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }


                            } else {
                                Log.i("TAG", "Oops! Image could not be saved.");
                                Toast.makeText(ViewLeaveActivity.this, "Click Again !", Toast.LENGTH_SHORT).show();

                            }


                        } else {
                            Toast.makeText(ViewLeaveActivity.this, "Permissions are not granted!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();


    }

    public void imageToPDF() throws FileNotFoundException {
        try {
            Document document = new Document();

            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

            String dirpath = path;
            PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/" + fName + ".pdf")); //  Change pdf's name.
            document.open();
            Image img = Image.getInstance(Environment.getExternalStorageDirectory() + File.separator + "/Pictures/Download/" + fName + ".jpg");
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100;
            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();
            Toast.makeText(this, "PDF Generated successfully!..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("TAG", "imageToPDF: " + e.getMessage());
        }
    }


    private File saveBitMap(Context context, View drawView) {
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Download"); // enter folder name to save image
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if (!isDirectoryCreated)
                Log.i("ATG", "Can't create directory to save the image");
            return null;
        }
        String filename = pictureFileDir.getPath() + File.separator + fName + ".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap = getBitmapFromView(drawView);
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
        scanGallery(context, pictureFile.getAbsolutePath());
        return pictureFile;
    }


    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPDF() throws IOException, DocumentException {

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(path, selectedLeave.getStudentName() + selectedLeave.getFrom() + ".pdf");

        if (!file.exists()) {
            file.createNewFile();
        }

        Document document = new Document();

        PdfWriter.getInstance(document,
                new FileOutputStream(file.getAbsoluteFile()));
        document.open();
        float[] width = {250f, 250f};
        PdfPTable table = new PdfPTable(width);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);


        String titles[] = {"Name",
                "Leave Status",
                "From",
                "To",
                "Duration",
                "Destination",
                "Parent Contact",
                "Reason"
        };
        String[] values = {selectedLeave.getStudentName(),
                selectedLeave.getStatus(),
                selectedLeave.getFrom(),
                selectedLeave.getTo(),
                selectedLeave.getDuration(),
                selectedLeave.getDestination(),
                selectedLeave.getParentContact(),
                selectedLeave.getReason()
        };


        for (int i = 0; i < titles.length; i++) {

            Paragraph paragraph = new Paragraph(titles[i]);
            Font font = new Font(Font.FontFamily.COURIER);
            font.setSize(30);
            paragraph.setFont(font);

            PdfPCell title = new PdfPCell(paragraph);
            table.addCell(new Phrase(titles[i], font));
            PdfPCell value = new PdfPCell(paragraph);
            table.addCell(new Phrase(titles[i], font));

        }

//        PdfPCell cell = new PdfPCell();
//        cell.addElement(new Paragraph("Name"));
//        table.addCell(cell);
//        PdfPCell studentName = new PdfPCell();
//        studentName.addElement(new Paragraph(selectedLeave.getStudentName()));
//        table.addCell(studentName);
//
//
//        //Student from
//        PdfPCell cellFrom = new PdfPCell();
//        cellFrom.addElement(new
//
//                Paragraph("From"));
//        table.addCell(cellFrom);
//        PdfPCell studentFrom = new PdfPCell();
//        studentFrom.addElement(new
//
//                Paragraph(selectedLeave.getFrom()));
//
//
//        table.addCell(studentFrom);
//

        document.add(table);
        document.close();

        Toast.makeText(this, "", Toast.LENGTH_SHORT).

                show();

    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }


    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 200) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    private void setLeave() {
        binding.leaveStudentName.setText(selectedLeave.getStudentName());
        binding.leaveStudentName.setEnabled(false);


        binding.leaveCurrentStatus.setText(selectedLeave.getStatus());
        binding.leaveCurrentStatus.setEnabled(false);


        binding.startDate.setText(selectedLeave.getFrom());


        binding.dueDate.setText(selectedLeave.getTo());

        binding.leaveDuration.setText(selectedLeave.getDuration());
        binding.leaveDuration.setEnabled(false);

        binding.leaveDestination.setText(selectedLeave.getDestination());
        binding.leaveDestination.setEnabled(false);

        binding.leaveParentContact.setText(selectedLeave.getParentContact());
        binding.leaveParentContact.setEnabled(false);

        binding.leaveReason.setText(selectedLeave.getReason());
        binding.leaveReason.setEnabled(false);


    }
}