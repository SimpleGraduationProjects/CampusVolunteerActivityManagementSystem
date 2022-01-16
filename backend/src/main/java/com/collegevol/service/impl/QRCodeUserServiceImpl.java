package com.collegevol.service.impl;

import com.collegevol.service.QRCodeUserService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class QRCodeUserServiceImpl implements QRCodeUserService {

    @Value("${upload.qrcode.dir}")
    private String IMAGE_PATH;

    @Override
    public String generateCodeImg(String code) throws WriterException, IOException {
        String qrCode="http://127.0.0.1:8888/images/qrcode/";
        String filePath= UUID.randomUUID().toString()+".jpg";
        qrCode+=filePath;
        filePath=IMAGE_PATH+filePath;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(code, BarcodeFormat.QR_CODE, 350, 350);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        return qrCode;
    }


    public static void main(String[] args) {
        QRCodeUserServiceImpl qrCodeService=new QRCodeUserServiceImpl();
        try {
            qrCodeService.generateCodeImg("http://127.0.0.1:5000/#/login?userId=1");
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
