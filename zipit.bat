for /f "delims== tokens=1,2" %%G in (VERSION) do set anx%%G=%%H
echo %anxversion%
xcopy /s /y .\out\ANXCamera.apk .\src\ANXCameraUnity\system\priv-app\ANXCamera

del .\out\ANXCameraUnity.zip
del .\out\ANXCameraUnity_*.zip

"C:\Program Files\7-Zip\7z.exe" a -tzip .\out\ANXCameraUnity.zip .\src\ANXCameraUnity\*
copy .\out\ANXCameraUnity.zip /B .\out\ANXCameraUnity_%anxversion%.zip


REM "C:\Program Files\7-Zip\7z.exe" a -tzip .\out\Arnob48MPFix.zip .\src\Arnob48MPFix\*
REM copy .\out\Arnob48MPFix.zip /B .\out\Arnob48MPFix_%anxversion%.zip

REM "C:\Program Files\7-Zip\7z.exe" a -tzip .\out\Dyneteve48MPFix.zip .\src\Dyneteve48MPFix\*
REM copy .\out\Dyneteve48MPFix.zip /B .\out\Dyneteve48MPFix_%anxversion%.zip

REM "C:\Program Files\7-Zip\7z.exe" a -tzip .\out\KubilWhyredyFix.zip .\src\KubilWhyredyFix\*
REM copy .\out\KubilWhyredyFix.zip /B .\out\KubilWhyredyFix_%anxversion%.zip

REM adb push .\out\ANXCameraUnity.zip  /sdcard/zips

REM del .\out\ANXCamFix.zip
REM del .\out\ANXCamFix_*.zip
REM "C:\Program Files\7-Zip\7z.exe" a -tzip .\out\ANXCamFix.zip .\src\ANXCamFix\*
REM copy .\out\ANXCamFix.zip /B .\out\ANXCamFix_%anxversion%.zip

REM adb push .\out\ANXCamFix.zip  /sdcard/zips

