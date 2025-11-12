package dev.aaa1115910.m3qrcode

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

enum class MaterialShapeQrErrorCorrectionLevel(val level: ErrorCorrectionLevel) {
    L(ErrorCorrectionLevel.L),
    M(ErrorCorrectionLevel.M),
    Q(ErrorCorrectionLevel.Q),
    H(ErrorCorrectionLevel.H)
}