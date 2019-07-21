for /f "delims== tokens=1,2" %%G in (VERSION) do set anx%%G=%%H
echo %anxversion%
git tag %anxversioncode%.oos_singularity
git push origin %anxversioncode%.oos_singularity
..\ANXMiuiPortTools\hub.exe release create -a .\out\ANXCameraUnity_%anxversion%.zip -m oos_singularity.%anxversion% %anxversioncode%.oos_singularity
REM ..\ANXMiuiPortTools\hub.exe release edit -a .\out\ANXCameraUnity_%anxversion%.zip -m oos_singularity.%anxversion% %anxversioncode%.oos_singularity
REM ..\ANXMiuiPortTools\hub.exe release edit -a .\out\KubilWhyredyFix_%anxversion%.zip -m oos_singularity.%anxversion% %anxversioncode%.oos_singularity
REM ..\ANXMiuiPortTools\hub.exe release edit -a .\out\Dyneteve48MPFix_%anxversion%.zip -m oos_singularity.%anxversion% %anxversioncode%.oos_singularity
