#!/usr/bin/env bash
java  -jar ../ANXMiuiPortTools/signapk.jar ../ANXMiuiPortTools/testkey.x509.pem ../ANXMiuiPortTools/testkey.pk8 ./out/ANXCamera-Unsigned.apk ./out/ANXCamera-Unaligned.apk
zipalign -f 4  ./out/ANXCamera-Unaligned.apk ./out/ANXCamera.apk
