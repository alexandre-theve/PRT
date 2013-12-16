package helpers;

import java.util.Hashtable;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QRCodeHelper{
	private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    
	public static Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int dimension) throws WriterException {
		Hashtable hints = null;
		String encoding = "UTF-8";
		if (encoding != null) {
		hints = new Hashtable();
		hints.put(EncodeHintType.CHARACTER_SET, encoding);
		}
		MultiFormatWriter writer = new MultiFormatWriter();
		
		BitMatrix result =writer.encode(contents, format, dimension, dimension, hints);
		int width = result.getWidth();
		int height =result.getHeight();
		int[] pixels = new int[width * height];
		// All are 0, or black, by default
		for (int y = 0; y < height; y++) {
		int offset = y * width;
		for (int x = 0; x < width; x++) {

		if(result.get(x, y))
		{
		pixels[offset + x] = BLACK ;
		}
		else
		pixels[offset + x] = WHITE ;

		}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
		}
	
}