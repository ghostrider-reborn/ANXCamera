#!/usr/bin/env bash

cp ./out/ANXCamera-Unsigned.apk ./out/ANXCamera-to-be-signed.apk
apksigner sign --key ../ANXMiuiPortTools/testkey.pk8 --cert ../ANXMiuiPortTools/testkey.x509.pem ./out/ANXCamera-to-be-signed.apk
mv ./out/ANXCamera-to-be-signed.apk ./out/ANXCamera-Unaligned.apk
zipalign -f 4  ./out/ANXCamera-Unaligned.apk ./out/ANXCamera.apk
