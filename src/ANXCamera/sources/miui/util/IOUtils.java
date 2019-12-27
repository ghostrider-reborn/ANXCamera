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
    private static final Pools.Pool<ByteArrayOutputStream> BYTE_ARRAY_OUTPUT_STREAM_POOL = Pools.createSoftReferencePool(new Pools.Manager<ByteArrayOutputStream>() {
        public ByteArrayOutputStream createInstance() {
            return new ByteArrayOutputStream();
        }

        public void onRelease(ByteArrayOutputStream byteArrayOutputStream) {
            byteArrayOutputStream.reset();
        }
    }, 2);
    private static final Pools.Pool<CharArrayWriter> CHAR_ARRAY_WRITER_POOL = Pools.createSoftReferencePool(new Pools.Manager<CharArrayWriter>() {
        public CharArrayWriter createInstance() {
            return new CharArrayWriter();
        }

        public void onRelease(CharArrayWriter charArrayWriter) {
            charArrayWriter.reset();
        }
    }, 2);
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final String LINE_SEPARATOR;
    private static final Pools.Pool<StringWriter> STRING_WRITER_POOL = Pools.createSoftReferencePool(new Pools.Manager<StringWriter>() {
        public StringWriter createInstance() {
            return new StringWriter();
        }

        public void onRelease(StringWriter stringWriter) {
            stringWriter.getBuffer().setLength(0);
        }
    }, 2);
    private static final ThreadLocal<SoftReference<byte[]>> THREAD_LOCAL_BYTE_BUFFER = new ThreadLocal<>();
    private static final ThreadLocal<SoftReference<char[]>> THREAD_LOCAL_CHAR_BUFFER = new ThreadLocal<>();

    static {
        StringWriter acquire = STRING_WRITER_POOL.acquire();
        PrintWriter printWriter = new PrintWriter(acquire);
        printWriter.println();
        printWriter.flush();
        LINE_SEPARATOR = acquire.toString();
        printWriter.close();
        STRING_WRITER_POOL.release(acquire);
    }

    protected IOUtils() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e2) {
            }
        }
    }

    public static void closeQuietly(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e2) {
            }
        }
    }

    public static void closeQuietly(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.flush();
            } catch (IOException e2) {
            }
            try {
                outputStream.close();
            } catch (IOException e3) {
            }
        }
    }

    public static void closeQuietly(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e2) {
            }
        }
    }

    public static void closeQuietly(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e2) {
            }
        }
    }

    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        long j = 0;
        byte[] byteArrayBuffer = getByteArrayBuffer();
        while (true) {
            int read = inputStream.read(byteArrayBuffer);
            int i = read;
            if (read != -1) {
                outputStream.write(byteArrayBuffer, 0, i);
                j += (long) i;
            } else {
                outputStream.flush();
                return j;
            }
        }
    }

    public static long copy(Reader reader, Writer writer) throws IOException {
        long j = 0;
        char[] charArrayBuffer = getCharArrayBuffer();
        while (true) {
            int read = reader.read(charArrayBuffer);
            int i = read;
            if (read != -1) {
                writer.write(charArrayBuffer, 0, i);
                j += (long) i;
            } else {
                writer.flush();
                return j;
            }
        }
    }

    public static void copy(InputStream inputStream, Writer writer) throws IOException {
        copy((Reader) new InputStreamReader(inputStream), writer);
    }

    public static void copy(InputStream inputStream, Writer writer, String str) throws IOException {
        copy((Reader) (str == null || str.length() == 0) ? new InputStreamReader(inputStream) : new InputStreamReader(inputStream, str), writer);
    }

    public static void copy(Reader reader, OutputStream outputStream) throws IOException {
        copy(reader, (Writer) new OutputStreamWriter(outputStream));
    }

    public static void copy(Reader reader, OutputStream outputStream, String str) throws IOException {
        copy(reader, (Writer) (str == null || str.length() == 0) ? new OutputStreamWriter(outputStream) : new OutputStreamWriter(outputStream, str));
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: byte[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    private static byte[] getByteArrayBuffer() {
        byte[] bArr = null;
        SoftReference softReference = THREAD_LOCAL_BYTE_BUFFER.get();
        if (softReference != null) {
            bArr = softReference.get();
        }
        if (bArr != null) {
            return bArr;
        }
        byte[] bArr2 = new byte[4096];
        THREAD_LOCAL_BYTE_BUFFER.set(new SoftReference(bArr2));
        return bArr2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: char[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    private static char[] getCharArrayBuffer() {
        char[] cArr = null;
        SoftReference softReference = THREAD_LOCAL_CHAR_BUFFER.get();
        if (softReference != null) {
            cArr = softReference.get();
        }
        if (cArr != null) {
            return cArr;
        }
        char[] cArr2 = new char[4096];
        THREAD_LOCAL_CHAR_BUFFER.set(new SoftReference(cArr2));
        return cArr2;
    }

    public static List<String> readLines(InputStream inputStream) throws IOException {
        return readLines((Reader) new InputStreamReader(inputStream));
    }

    public static List<String> readLines(InputStream inputStream, String str) throws IOException {
        return readLines((Reader) (str == null || str.length() == 0) ? new InputStreamReader(inputStream) : new InputStreamReader(inputStream, str));
    }

    public static List<String> readLines(Reader reader) throws IOException {
        BufferedReader bufferedReader = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
        ArrayList arrayList = new ArrayList();
        for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
            arrayList.add(readLine);
        }
        return arrayList;
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream acquire = BYTE_ARRAY_OUTPUT_STREAM_POOL.acquire();
        copy(inputStream, (OutputStream) acquire);
        byte[] byteArray = acquire.toByteArray();
        BYTE_ARRAY_OUTPUT_STREAM_POOL.release(acquire);
        return byteArray;
    }

    public static byte[] toByteArray(Reader reader) throws IOException {
        ByteArrayOutputStream acquire = BYTE_ARRAY_OUTPUT_STREAM_POOL.acquire();
        copy(reader, (OutputStream) acquire);
        byte[] byteArray = acquire.toByteArray();
        BYTE_ARRAY_OUTPUT_STREAM_POOL.release(acquire);
        return byteArray;
    }

    public static byte[] toByteArray(Reader reader, String str) throws IOException {
        ByteArrayOutputStream acquire = BYTE_ARRAY_OUTPUT_STREAM_POOL.acquire();
        copy(reader, (OutputStream) acquire, str);
        byte[] byteArray = acquire.toByteArray();
        BYTE_ARRAY_OUTPUT_STREAM_POOL.release(acquire);
        return byteArray;
    }

    public static char[] toCharArray(InputStream inputStream) throws IOException {
        CharArrayWriter acquire = CHAR_ARRAY_WRITER_POOL.acquire();
        copy(inputStream, (Writer) acquire);
        char[] charArray = acquire.toCharArray();
        CHAR_ARRAY_WRITER_POOL.release(acquire);
        return charArray;
    }

    public static char[] toCharArray(InputStream inputStream, String str) throws IOException {
        CharArrayWriter acquire = CHAR_ARRAY_WRITER_POOL.acquire();
        copy(inputStream, (Writer) acquire, str);
        char[] charArray = acquire.toCharArray();
        CHAR_ARRAY_WRITER_POOL.release(acquire);
        return charArray;
    }

    public static char[] toCharArray(Reader reader) throws IOException {
        CharArrayWriter acquire = CHAR_ARRAY_WRITER_POOL.acquire();
        copy(reader, (Writer) acquire);
        char[] charArray = acquire.toCharArray();
        CHAR_ARRAY_WRITER_POOL.release(acquire);
        return charArray;
    }

    public static InputStream toInputStream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    public static InputStream toInputStream(String str, String str2) throws UnsupportedEncodingException {
        return new ByteArrayInputStream((str2 == null || str2.length() == 0) ? str.getBytes() : str.getBytes(str2));
    }

    public static String toString(InputStream inputStream) throws IOException {
        StringWriter acquire = STRING_WRITER_POOL.acquire();
        copy(inputStream, (Writer) acquire);
        String stringWriter = acquire.toString();
        STRING_WRITER_POOL.release(acquire);
        return stringWriter;
    }

    public static String toString(InputStream inputStream, String str) throws IOException {
        StringWriter acquire = STRING_WRITER_POOL.acquire();
        copy(inputStream, (Writer) acquire, str);
        String stringWriter = acquire.toString();
        STRING_WRITER_POOL.release(acquire);
        return stringWriter;
    }

    public static String toString(Reader reader) throws IOException {
        StringWriter acquire = STRING_WRITER_POOL.acquire();
        copy(reader, (Writer) acquire);
        String stringWriter = acquire.toString();
        STRING_WRITER_POOL.release(acquire);
        return stringWriter;
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

    public static void write(OutputStream outputStream, byte[] bArr) throws IOException {
        if (bArr != null) {
            outputStream.write(bArr);
        }
    }

    public static void write(OutputStream outputStream, char[] cArr) throws IOException {
        if (cArr != null) {
            outputStream.write(new String(cArr).getBytes());
        }
    }

    public static void write(OutputStream outputStream, char[] cArr, String str) throws IOException {
        if (cArr != null) {
            outputStream.write((str == null || str.length() == 0) ? new String(cArr).getBytes() : new String(cArr).getBytes(str));
        }
    }

    public static void write(Writer writer, String str) throws IOException {
        if (str != null) {
            writer.write(str);
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
}
