for /f "delims== tokens=1,2" %%G in (VERSION) do set anx%%G=%%H
echo %anxversion%
xcopy /s /y .\out\ANXCamera.apk .\src\ANXCameraUnity\system\priv-app\ANXCamera

del .\out\ANXCameraUnity.zip
del .\out\ANXCameraUnity_*.zip

"C:\Program Files\7-Zip\7z.exe" a -tzip .\out\ANXCameraUnity.zip .\src\ANXCameraUnity\*
copy .\out\ANXCameraUnity.zip /B .\out\ANXCameraUnity_%anxversion%.zip
adb push .\out\ANXCameraUnity.zip  /sdcard/zips

REM "C:\Program Files\7-Zip\7z.exe" a -tzip .\out\Arnob48MPFix.zip .\src\Arnob48MPFix\*
REM copy .\out\Arnob48MPFix.zip /B .\out\Arnob48MPFix_%anxversion%.zip

REM "C:\Program Files\7-Zip\7z.exe" a -tzip .\out\Dyneteve48MPFix.zip .\src\Dyneteve48MPFix\*
REM copy .\out\Dyneteve48MPFix.zip /B .\out\Dyneteve48MPFix_%anxversion%.zip

REM "C:\Program Files\7-Zip\7z.exe" a -tzip .\out\KubilWhyredyFix.zip .\src\KubilWhyredyFix\*
REM copy .\out\KubilWhyredyFix.zip /B .\out\KubilWhyredyFix_%anxversion%.zip


REM del .\out\LavendyFix.zip
REM del .\out\LavendyFix_*.zip
REM "C:\Program Files\7-Zip\7z.exe" a -tzip .\out\LavendyFix.zip .\src\LavendyFix\*
REM copy .\out\LavendyFix.zip /B .\out\LavendyFix_%anxversion%.zip


REM del .\out\ANXCamFix.zip
REM del .\out\ANXCamFix_*.zip
REM del .\out\ANXCamCustVendFix.zip
REM del .\out\ANXCamCustVendFix_*.zip
REM "C:\Program Files\7-Zip\7z.exe" a -tzip .\out\ANXCamCustVendFix.zip .\src\ANXCamFix\*
REM copy .\out\ANXCamCustVendFix.zip /B .\out\ANXCamCustVendFix_%anxversion%.zip

REM adb push .\out\ANXCamCustVendFix.zip  /sdcard/zips

