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


del .\out\ANXCamFix.zip
del .\out\ANXCamFix_*.zip
del .\out\ANXCustLibs.zip
del .\out\ANXCustLibs_*.zip
"C:\Program Files\7-Zip\7z.exe" a -tzip .\out\ANXCustLibs.zip .\src\ANXCamFix\*
copy .\out\ANXCustLibs.zip /B .\out\ANXCustLibs_%anxversion%.zip

adb push .\out\ANXCustLibs.zip  /sdcard/zips


del .\out\ANXMimoji.zip
del .\out\ANXMimoji_*.zip
"C:\Program Files\7-Zip\7z.exe" a -tzip .\out\ANXMimoji.zip .\src\ANXMimoji\*
copy .\out\ANXMimoji.zip /B .\out\ANXMimoji_%anxversion%.zip

adb push .\out\ANXMimoji.zip  /sdcard/zips
