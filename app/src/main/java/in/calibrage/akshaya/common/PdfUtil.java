package in.calibrage.akshaya.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Date;

import in.calibrage.akshaya.R;

public class PdfUtil {
    public static  final String TAG = PdfUtil.class.getSimpleName();
    private static String FILE = Environment.getExternalStorageDirectory().getPath() + "/mypdf/" + "quickpay.pdf";
    public static  void createPDF(Context ctx,Bitmap bitmapsignature,String qtymit,String FFBrate,String totalFFB,String convinceCharge,String quickpay,String closingBal,String totalBal)
    {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            // Left
            Paragraph paragraph = new Paragraph("DATE :"+new Date());
            document.add(new Paragraph("\n\n\n"));
            Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.banner_logo);
            ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream3);
            Image maimg = Image.getInstance(stream3.toByteArray());
            maimg.setAbsolutePosition(200, 745);
            maimg.scalePercent(20);
            document.add(maimg);
            document.add(new Paragraph("\n\n"));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            document.add(new Paragraph("\n\n"));
            PdfPTable table = new PdfPTable(2);
            // the cell object

            // we add the four remaining cells with addCell()
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(ctx.getResources().getString(R.string.quantity_mt));
            table.addCell(qtymit);
            table.addCell(ctx.getResources().getString(R.string.ffb_flot));
            table.addCell(FFBrate);
            table.addCell(ctx.getResources().getString(R.string.amount_of_FFB));
            table.addCell(totalFFB);
            table.addCell(ctx.getResources().getString(R.string.convenience_charge));
            table.addCell(convinceCharge);
            table.addCell(ctx.getResources().getString(R.string.quick_pay));
            table.addCell(quickpay);
            table.addCell(ctx.getResources().getString(R.string.closingBal));
            table.addCell(closingBal);
            table.addCell(ctx.getResources().getString(R.string.totalBalance));
            table.addCell(totalBal);


            document.add(table);

            ByteArrayOutputStream stream4 = new ByteArrayOutputStream();
            bitmapsignature.compress(Bitmap.CompressFormat.PNG, 70, stream4);
            Image maimg1 = Image.getInstance(stream4.toByteArray());
            maimg.setAbsolutePosition(200, 200);
            maimg.scalePercent(20);
            document.add(maimg1);
            document.close();

            Log.d(TAG,"------------- analysis ---------- >> PDF Created ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
