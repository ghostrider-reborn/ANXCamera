package com.a.b;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextPaint;
import java.io.PrintStream;
import java.util.regex.Pattern;

/* compiled from: TextMeshUtils */
public class a {
    private static int a(Canvas canvas, String str, float f, float f2, Paint paint, int i) {
        int i2;
        int i3;
        Canvas canvas2 = canvas;
        String str2 = str;
        float f3 = f2;
        Paint paint2 = paint;
        Rect rect = new Rect();
        char c = 0;
        System.out.printf("the text space is: %d\n", new Object[]{Integer.valueOf(i)});
        System.out.printf("the text length is: %d\n", new Object[]{Integer.valueOf(str.length())});
        float f4 = f;
        int i4 = 0;
        int i5 = 0;
        while (i4 < str.length()) {
            char charAt = str2.charAt(i4);
            String valueOf = String.valueOf(charAt);
            Math.round(paint2.measureText(String.valueOf(charAt)));
            if (charAt > 55296) {
                i4++;
                String str3 = String.valueOf(charAt) + str2.charAt(i4);
                if (i4 != 1) {
                    paint2.getTextBounds(str2, i4, i4 + 1, rect);
                    rect.width();
                }
                int width = rect.width() + (i / 2);
                f4 += (float) width;
                i5 += width;
                if (canvas2 != null) {
                    canvas2.drawText(str3, f4, f3, paint2);
                }
            } else {
                if (charAt == ' ') {
                    i2 = Math.round(paint2.measureText(String.valueOf(str2.charAt(i4)))) + i;
                } else {
                    paint2.getTextBounds(str2, i4, i4 + 1, rect);
                    if (charAt >= 128) {
                        i3 = rect.width() + (i / 2);
                        PrintStream printStream = System.out;
                        Object[] objArr = new Object[1];
                        objArr[c] = Character.valueOf(charAt);
                        printStream.printf("%s is not NumOrLetters\n", objArr);
                    } else if (f(valueOf)) {
                        i3 = rect.width() + i;
                        PrintStream printStream2 = System.out;
                        Object[] objArr2 = new Object[1];
                        objArr2[c] = Character.valueOf(charAt);
                        printStream2.printf("%s is NumOrLetters\n", objArr2);
                    } else {
                        i2 = rect.width() + (i * 2);
                        PrintStream printStream3 = System.out;
                        Object[] objArr3 = new Object[4];
                        objArr3[c] = Character.valueOf(str2.charAt(i4));
                        objArr3[1] = Integer.valueOf(i2);
                        objArr3[2] = Integer.valueOf(i);
                        objArr3[3] = Integer.valueOf(rect.width());
                        printStream3.printf("%s is: %d, space is: %d, text is %d\n", objArr3);
                    }
                    i2 = i3;
                    PrintStream printStream32 = System.out;
                    Object[] objArr32 = new Object[4];
                    objArr32[c] = Character.valueOf(str2.charAt(i4));
                    objArr32[1] = Integer.valueOf(i2);
                    objArr32[2] = Integer.valueOf(i);
                    objArr32[3] = Integer.valueOf(rect.width());
                    printStream32.printf("%s is: %d, space is: %d, text is %d\n", objArr32);
                }
                f4 += (float) i2;
                i5 += i2;
                if (canvas2 != null) {
                    canvas2.drawText(String.valueOf(str2.charAt(i4)), f4, f3, paint2);
                }
            }
            i4++;
            c = 0;
        }
        return i5;
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x01ab  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x01cf  */
    private static Bitmap a(byte[] bArr, String str, int i, int i2, int i3, float f, int i4, float f2, int i5, float f3, float f4, float f5, int i6, int i7, int i8, int i9) {
        Bitmap bitmap;
        int i10;
        int i11;
        int i12 = i;
        int i13 = i3;
        int i14 = i7;
        int i15 = i8;
        int i16 = i9;
        String str2 = new String(bArr);
        Rect rect = new Rect(0, 0, i15, i16);
        Bitmap createBitmap = Bitmap.createBitmap(i15, i16, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(0);
        paint.setStyle(Paint.Style.FILL);
        Canvas canvas = new Canvas(createBitmap);
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(i2);
        float f6 = (float) i12;
        textPaint.setTextSize(f6);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        if (i14 == 0) {
            textPaint.setTextAlign(Paint.Align.LEFT);
        } else if (i14 == 1) {
            textPaint.setTextAlign(Paint.Align.CENTER);
        } else {
            textPaint.setTextAlign(Paint.Align.RIGHT);
        }
        if ((i4 & 16) == 16) {
            textPaint.setUnderlineText(true);
        }
        if ((i4 & 32) == 32) {
            textPaint.setStrikeThruText(true);
        }
        if ((i4 & 4) == 4) {
            textPaint.setTextSkewX((-f) / 90.0f);
        }
        if ((i4 & 8) == 8) {
            textPaint.setFakeBoldText(true);
        }
        if ((i4 & 2) == 2) {
            textPaint.setShadowLayer(f3, f4, f5, i6);
        }
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        int centerY = (int) ((((float) rect.centerY()) - (fontMetrics.top / 2.0f)) - (fontMetrics.bottom / 2.0f));
        if ((i4 & 1) == 1) {
            TextPaint textPaint2 = new TextPaint();
            textPaint2.setColor(i5);
            textPaint2.setTextSize(textPaint.getTextSize());
            textPaint2.setAntiAlias(textPaint.isAntiAlias());
            textPaint2.setStyle(Paint.Style.STROKE);
            textPaint2.setStrokeWidth((5.0f * f2) / f6);
            textPaint2.setTextAlign(textPaint.getTextAlign());
            textPaint2.setTextSkewX(textPaint.getTextSkewX());
            textPaint.setFakeBoldText(false);
            textPaint2.setFakeBoldText(true);
            float f7 = ((float) i13) / f6;
            if (Build.VERSION.SDK_INT >= 21) {
                textPaint2.setLetterSpacing(f7);
                if (i14 == 0) {
                    canvas.drawText(str2, (float) rect.left, (float) centerY, textPaint2);
                } else if (i14 == 1) {
                    canvas.drawText(str2, (float) rect.centerX(), (float) centerY, textPaint2);
                } else {
                    canvas.drawText(str2, (float) rect.right, (float) centerY, textPaint2);
                }
            } else {
                Rect rect2 = new Rect();
                textPaint.getTextBounds(str2, 0, str2.length(), rect2);
                int i17 = 0;
                int i18 = 0;
                while (i17 < str2.length()) {
                    if (str2.charAt(i17) > 55296) {
                        i17++;
                        i18++;
                    }
                    i17++;
                }
                if (i14 == 0) {
                    i11 = centerY;
                    bitmap = createBitmap;
                    i10 = 1;
                    a(canvas, str2, 0.0f, (float) centerY, textPaint2, i13);
                } else {
                    TextPaint textPaint3 = textPaint2;
                    i11 = centerY;
                    bitmap = createBitmap;
                    i10 = 1;
                    if (i14 == 1) {
                        a(canvas, str2, (float) ((((rect.width() - rect2.width()) + (i18 * i12)) - ((str2.length() - 1) * i13)) / 2), (float) i11, textPaint3, i13);
                    } else {
                        a(canvas, str2, (float) (((rect.width() - rect2.width()) + (i18 * i12)) - ((str2.length() - 1) * i13)), (float) i11, textPaint3, i13);
                    }
                }
                float f8 = ((float) i13) / f6;
                if (Build.VERSION.SDK_INT < 21) {
                    textPaint.setLetterSpacing(f8);
                    if (i14 == 0) {
                        canvas.drawText(str2, (float) rect.left, (float) i11, textPaint);
                    } else if (i14 == i10) {
                        canvas.drawText(str2, (float) rect.centerX(), (float) i11, textPaint);
                    } else {
                        canvas.drawText(str2, (float) rect.right, (float) i11, textPaint);
                    }
                } else {
                    Rect rect3 = new Rect();
                    int i19 = 0;
                    textPaint.getTextBounds(str2, 0, str2.length(), rect3);
                    int i20 = 0;
                    while (i19 < str2.length()) {
                        if (str2.charAt(i19) > 55296) {
                            i19++;
                            i20++;
                        }
                        i19 += i10;
                    }
                    if (i14 == 0) {
                        a(canvas, str2, 0.0f, (float) i11, textPaint, i13);
                    } else if (i14 == i10) {
                        a(canvas, str2, (float) ((((rect.width() - rect3.width()) + (i20 * i12)) - ((str2.length() - i10) * i13)) / 2), (float) i11, textPaint, i13);
                    } else {
                        a(canvas, str2, (float) (((rect.width() - rect3.width()) + (i20 * i12)) - ((str2.length() - i10) * i13)), (float) i11, textPaint, i13);
                    }
                }
                canvas.save(31);
                canvas.restore();
                return bitmap;
            }
        }
        i11 = centerY;
        bitmap = createBitmap;
        i10 = 1;
        float f82 = ((float) i13) / f6;
        if (Build.VERSION.SDK_INT < 21) {
        }
        canvas.save(31);
        canvas.restore();
        return bitmap;
    }

    public static boolean f(String str) {
        return Pattern.compile("^[A-Za-z0-9_]+$").matcher(str).matches();
    }
}
