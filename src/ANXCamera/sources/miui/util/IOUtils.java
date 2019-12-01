package miui.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import miui.util.Pools;

public class IOUtils {
    private static final String LINE_SEPARATOR;
    private static final int SF = 4096;
    private static final ThreadLocal<SoftReference<byte[]>> SJ = new ThreadLocal<>();
    private static final ThreadLocal<SoftReference<char[]>> SP = new ThreadLocal<>();
    private static final Pools.Pool<ByteArrayOutputStream> SQ = Pools.createSoftReferencePool(new Pools.Manager<ByteArrayOutputStream>() {
        /* renamed from: dZ */
        public ByteArrayOutputStream createInstance() {
            return new ByteArrayOutputStream();
        }

        /* renamed from: a */
        public void onRelease(ByteArrayOutputStream byteArrayOutputStream) {
            byteArrayOutputStream.reset();
        }
    }, 2);
    private static final Pools.Pool<CharArrayWriter> SS = Pools.createSoftReferencePool(new Pools.Manager<CharArrayWriter>() {
        /* renamed from: ea */
        public CharArrayWriter createInstance() {
            return new CharArrayWriter();
        }

        /* renamed from: a */
        public void onRelease(CharArrayWriter charArrayWriter) {
            charArrayWriter.reset();
        }
    }, 2);
    private static final Pools.Pool<StringWriter> SU = Pools.createSoftReferencePool(new Pools.Manager<StringWriter>() {
        /* renamed from: eb */
        public StringWriter createInstance() {
            return new StringWriter();
        }

        /* renamed from: a */
        public void onRelease(StringWriter stringWriter) {
            stringWriter.getBuffer().setLength(0);
        }
    }, 2);

    static {
        StringWriter acquire = SU.acquire();
        PrintWriter printWriter = new PrintWriter(acquire);
        printWriter.println();
        printWriter.flush();
        LINE_SEPARATOR = acquire.toString();
        printWriter.close();
        SU.release(acquire);
    }

    protected IOUtils() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static void closeQuietly(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.flush();
            } catch (IOException e) {
            }
            try {
                outputStream.close();
            } catch (IOException e2) {
            }
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream acquire = SQ.acquire();
        copy(inputStream, (OutputStream) acquire);
        byte[] byteArray = acquire.toByteArray();
        SQ.release(acquire);
        return byteArray;
    }

    public static byte[] toByteArray(Reader reader) throws IOException {
        ByteArrayOutputStream acquire = SQ.acquire();
        copy(reader, (OutputStream) acquire);
        byte[] byteArray = acquire.toByteArray();
        SQ.release(acquire);
        return byteArray;
    }

    public static byte[] toByteArray(Reader reader, String str) throws IOException {
        ByteArrayOutputStream acquire = SQ.acquire();
        copy(reader, (OutputStream) acquire, str);
        byte[] byteArray = acquire.toByteArray();
        SQ.release(acquire);
        return byteArray;
    }

    public static char[] toCharArray(InputStream inputStream) throws IOException {
        CharArrayWriter acquire = SS.acquire();
        copy(inputStream, (Writer) acquire);
        char[] charArray = acquire.toCharArray();
        SS.release(acquire);
        return charArray;
    }

    public static char[] toCharArray(InputStream inputStream, String str) throws IOException {
        CharArrayWriter acquire = SS.acquire();
        copy(inputStream, (Writer) acquire, str);
        char[] charArray = acquire.toCharArray();
        SS.release(acquire);
        return charArray;
    }

    public static char[] toCharArray(Reader reader) throws IOException {
        CharArrayWriter acquire = SS.acquire();
        copy(reader, (Writer) acquire);
        char[] charArray = acquire.toCharArray();
        SS.release(acquire);
        return charArray;
    }

    public static String toString(InputStream inputStream) throws IOException {
        StringWriter acquire = SU.acquire();
        copy(inputStream, (Writer) acquire);
        String stringWriter = acquire.toString();
        SU.release(acquire);
        return stringWriter;
    }

    public static String toString(InputStream inputStream, String str) throws IOException {
        StringWriter acquire = SU.acquire();
        copy(inputStream, (Writer) acquire, str);
        String stringWriter = acquire.toString();
        SU.release(acquire);
        return stringWriter;
    }

    public static String toString(Reader reader) throws IOException {
        StringWriter acquire = SU.acquire();
        copy(reader, (Writer) acquire);
        String stringWriter = acquire.toString();
        SU.release(acquire);
        return stringWriter;
    }

    public static List<String> readLines(InputStream inputStream) throws IOException {
        return readLines((Reader) new InputStreamReader(inputStream));
    }

    public static List<String> readLines(InputStream inputStream, String str) throws IOException {
        InputStreamReader inputStreamReader;
        if (str == null || str.length() == 0) {
            inputStreamReader = new InputStreamReader(inputStream);
        } else {
            inputStreamReader = new InputStreamReader(inputStream, str);
        }
        return readLines((Reader) inputStreamReader);
    }

    public static List<String> readLines(Reader reader) throws IOException {
        BufferedReader bufferedReader;
        if (reader instanceof BufferedReader) {
            bufferedReader = (BufferedReader) reader;
        } else {
            bufferedReader = new BufferedReader(reader);
        }
        ArrayList arrayList = new ArrayList();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return arrayList;
            }
            arrayList.add(readLine);
        }
    }

    public static InputStream toInputStream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    public static InputStream toInputStream(String str, String str2) throws UnsupportedEncodingException {
        return new ByteArrayInputStream((str2 == null || str2.length() == 0) ? str.getBytes() : str.getBytes(str2));
    }

    public static void write(OutputStream outputStream, byte[] bArr) throws IOException {
        if (bArr != null) {
            outputStream.write(bArr);
        }
    }

    public static void write(Writer writer, byte[] bArr) throws IOException {
        if (bArr != null) {
            writer.write(new String(bArr));
        }
    }

    public static void write(Writer writer, byte[] bArr, String str) throws IOException {
        if (bArr != null) {
            writer.write((str == null || str.length() == 0) ? new String(bArr) : new String(bArr, str));
        }
    }

    public static void write(Writer writer, char[] cArr) throws IOException {
        if (cArr != null) {
            writer.write(cArr);
        }
    }

    public static void write(OutputStream outputStream, char[] cArr) throws IOException {
        if (cArr != null) {
            outputStream.write(new String(cArr).getBytes());
        }
    }

    public static void write(OutputStream outputStream, char[] cArr, String str) throws IOException {
        byte[] bArr;
        if (cArr != null) {
            if (str == null || str.length() == 0) {
                bArr = new String(cArr).getBytes();
            } else {
                bArr = new String(cArr).getBytes(str);
            }
            outputStream.write(bArr);
        }
    }

    public static void write(Writer writer, String str) throws IOException {
        if (str != null) {
            writer.write(str);
        }
    }

    public static void write(OutputStream outputStream, String str) throws IOException {
        if (str != null) {
            outputStream.write(str.getBytes());
        }
    }

    public static void write(OutputStream outputStream, String str, String str2) throws IOException {
        if (str != null) {
            outputStream.write((str2 == null || str2.length() == 0) ? str.getBytes() : str.getBytes(str2));
        }
    }

    public static void writeLines(OutputStream outputStream, Collection<Object> collection, String str) throws IOException {
        if (collection != null) {
            if (str == null) {
                str = LINE_SEPARATOR;
            }
            for (Object next : collection) {
                if (next != null) {
                    outputStream.write(next.toString().getBytes());
                }
                outputStream.write(str.getBytes());
            }
        }
    }

    public static void writeLines(OutputStream outputStream, Collection<Object> collection, String str, String str2) throws IOException {
        if (collection != null) {
            if (str == null) {
                str = LINE_SEPARATOR;
            }
            for (Object next : collection) {
                if (next != null) {
                    outputStream.write(next.toString().getBytes(str2));
                }
                outputStream.write(str.getBytes(str2));
            }
        }
    }

    public static void writeLines(Writer writer, Collection<Object> collection, String str) throws IOException {
        if (collection != null) {
            if (str == null) {
                str = LINE_SEPARATOR;
            }
            for (Object next : collection) {
                if (next != null) {
                    writer.write(next.toString());
                }
                writer.write(str);
            }
        }
    }

    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] dX = dX();
        long j = 0;
        while (true) {
            int read = inputStream.read(dX);
            if (read != -1) {
                outputStream.write(dX, 0, read);
                j += (long) read;
            } else {
                outputStream.flush();
                return j;
            }
        }
    }

    public static void copy(InputStream inputStream, Writer writer) throws IOException {
        copy((Reader) new InputStreamReader(inputStream), writer);
    }

    public static void copy(InputStream inputStream, Writer writer, String str) throws IOException {
        InputStreamReader inputStreamReader;
        if (str == null || str.length() == 0) {
            inputStreamReader = new InputStreamReader(inputStream);
        } else {
            inputStreamReader = new InputStreamReader(inputStream, str);
        }
        copy((Reader) inputStreamReader, writer);
    }

    public static void copy(Reader reader, OutputStream outputStream) throws IOException {
        copy(reader, (Writer) new OutputStreamWriter(outputStream));
    }

    public static void copy(Reader reader, OutputStream outputStream, String str) throws IOException {
        OutputStreamWriter outputStreamWriter;
        if (str == null || str.length() == 0) {
            outputStreamWriter = new OutputStreamWriter(outputStream);
        } else {
            outputStreamWriter = new OutputStreamWriter(outputStream, str);
        }
        copy(reader, (Writer) outputStreamWriter);
    }

    public static long copy(Reader reader, Writer writer) throws IOException {
        char[] dY = dY();
        long j = 0;
        while (true) {
            int read = reader.read(dY);
            if (read != -1) {
                writer.write(dY, 0, read);
                j += (long) read;
            } else {
                writer.flush();
                return j;
            }
        }
    }

    private static byte[] dX() {
        byte[] bArr;
        SoftReference softReference = SJ.get();
        if (softReference != null) {
            bArr = (byte[]) softReference.get();
        } else {
            bArr = null;
        }
        if (bArr != null) {
            return bArr;
        }
        byte[] bArr2 = new byte[4096];
        SJ.set(new SoftReference(bArr2));
        return bArr2;
    }

    private static char[] dY() {
        char[] cArr;
        SoftReference softReference = SP.get();
        if (softReference != null) {
            cArr = (char[]) softReference.get();
        } else {
            cArr = null;
        }
        if (cArr != null) {
            return cArr;
        }
        char[] cArr2 = new char[4096];
        SP.set(new SoftReference(cArr2));
        return cArr2;
    }
}
