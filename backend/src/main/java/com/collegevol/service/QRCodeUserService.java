package com.collegevol.service;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface QRCodeUserService {

    String generateCodeImg(String code) throws WriterException, IOException;
}
