package tech.nodex.tutils2.http;

import okhttp3.MediaType;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by cz on 2016-8-28.
 */
public class DownloadStream extends InputStream {
    Response resp;
    InputStream is;
    public DownloadStream(Response resp){
        this.resp = resp;
        is = resp.body().byteStream();
    }

    @Override
    public int read() throws IOException {
        return is.read();
    }

    public MediaType getContentType(){
        return resp.body().contentType();
    }

    public String getFileName(){
        String disposition =resp.header("Content-Disposition");
        if(disposition!=null){
            return disposition.replaceAll(".*filename=\"(.+)\".*","$1");
        }
        return null;
    }

    public long getContentLength(){
        return resp.body().contentLength();
    }

    @Override
    public void close() throws IOException {
        super.close();
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(resp);
    }
}
