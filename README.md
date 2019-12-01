# ANXCamera
MiuiCamera Port for Xiaomi's Phones

Self sufficient repository to decompile to smali, recompile, sign, decompile to java, push to device app port.

miui_TUCANAEEAGlobal_V11.0.5.0.PFDEUXM_08e1a88222_9.0 was used as src.

Recommended to open this Repository in VSCode

Requires Java 1.7 or 1.8, and 7-zip.

Instructions for Development:

 1. Clone this repository
 2. Run redo.bat 
 3. Start porting

  
Instructions for Testing:

 1. Download the `zip` from https://camera.aeonax.com/#predownloads
 2. Install the `zip` with Magisk or recovery
 3. Reboot Once, if it doesn't work properly, reboot twice.
 4. Start Testing
 5. To uninstall, uninstall via Magisk or flash same zip via recovery


Special Thanks to
Abhishek Aggarwal (https://github.com/TheScarastic) for bringing this up to Beta version
Mustang_ssc (https://github.com/Mustang-ssc) for his help in adding support for other devices
Amogha Maiya (https://github.com/amog787) for sponsoring, and all-round help
Sandeep (https://github.com/CodeElixir) for help with the libs
Psygarden (https://forum.xda-developers.com/member.php?u=7645131) for his general help. 



Steps to Port MiuiCamera from scratch:
1. Unpack System of Miui ROM
   1. Check `extractrom.bat`
2. Setup Original files for Decompiling
   1. Copy following to ANXCamera\orig\MiuiFrameworks. Files to be taken from Unpacked ROM above 
      1. framework\framework-res.apk
      2. app\miui\miui.apk
   2. Copy following to ANXCamera\orig
      1. priv-app\MiuiCamera\MiuiCamera.apk
3. Prepare APKTool for decompiling
   1. Install above framework files by running following commands
      1. `java  -jar ..\ANXMiuiPortTools\apktool.jar if -p ..\ANXMiuiPortTools\MiuiFrameworks .\orig\MiuiFrameworks\framework-res.apk`
      2. `java  -jar ..\ANXMiuiPortTools\apktool.jar if -p ..\ANXMiuiPortTools\MiuiFrameworks .\orig\MiuiFrameworks\miui.apk`
4. Decompile MiuiCamera by running
   1. `java  -jar ..\ANXMiuiPortTools\apktool.jar d -p ..\ANXMiuiPortTools\MiuiFrameworks -f -b -o .\src\ANXCamera .\orig\MiuiCamera.apk`
   2. Parameters
      1. d, decode
      2. -p, --frame-path <DIR>
      3. -f, --force
      4. -b, --no-debug-info
      5. -o, --output <DIR>
5. Open `src\ANXCamera\AndroidManifest.xml` and format the document
6. First Compile Attempt
   1. Run `recompile.bat` just to check whether we are able to recompile without any modification
   2. Run `sign.bat` to sign and zipalign
   3. Run `jadx.bat` to create java code from compiled apk. This fails, don't worry, it does whatever it can
7. We will try to Install this recompiled version, It should fail because of missing Java Libraries
   1. Set `android:required="false"` of this libs
8. Once it installs try running it.
   1. It will crash as expected, because it fails to initialize MiuiSDK. 
   2. Remove the initialization code. We don't want to init miui sdk cause we are not on miui.
   3. Once you remove the init code and try to run, It fails with Hidden API Blacklisted Errors, That means we now have to move our app to priv-app. 
   4. We need to now package it in a Unity Module so that it can be installed via Magisk.
9.  Next we will Deodex rom and decompile the required libs
   5. Run in WSL or Linux `$ /<path to vdexExtractor>/tools/deodex/run.sh -i /<path to system>/framework -o /<path to deodex destination>/framework`
   6. Above will deodex the system framework
   7. Now we decompile the required libs
      1. Copy latest baksmali.*.jar to `<path to deodex destination>`
      2. To identify what libs you need to decompile. Open `src\ANXCamera\AndroidManifest.xml`
         1. Find the `uses-library` XML Nodes. We need to decompile these
         2. We will skip `miui-stat.jar` as we will disable miui-stats from sending data to miui.
      3. Open a cmd inside `<path to deodex destination>` folder. And run the following:
         1. `java -jar baksmali-2.2.7.jar d -o android-support-v7-recyclerview .\framework\vdexExtractor_deodexed\android-support-v7-recyclerview\android-support-v7-recyclerview_classes.dex.dex`
         2. `java -jar baksmali-2.2.7.jar d -o android-support-v13 .\framework\vdexExtractor_deodexed\android-support-v13\android-support-v13_classes.dex.dex`
         3. `java -jar baksmali-2.2.7.jar d -o boot-framework .\framework\vdexExtractor_deodexed\boot-framework\boot-framework_classes.dex`
         4. `java -jar baksmali-2.2.7.jar d -o boot-framework2 .\framework\vdexExtractor_deodexed\boot-framework\boot-framework_classes2.dex`
         5. `java -jar baksmali-2.2.7.jar d -o boot-framework3 .\framework\vdexExtractor_deodexed\boot-framework\boot-framework_classes3.dex`
         6. `java -jar baksmali-2.2.7.jar d -o boot-miui .\framework\vdexExtractor_deodexed\boot-miui\boot-miui_classes.dex`
         7. `java -jar baksmali-2.2.7.jar d -o boot-miuisystem .\framework\vdexExtractor_deodexed\boot-miuisystem\boot-miuisystem_classes.dex`
         8. `java -jar baksmali-2.2.7.jar d -o gson .\framework\vdexExtractor_deodexed\gson\gson_classes.dex.dex`
         9. `java -jar baksmali-2.2.7.jar d -o volley .\framework\vdexExtractor_deodexed\volley\volley_classes.dex.dex`
         10. `java -jar baksmali-2.2.7.jar d -o zxing .\framework\vdexExtractor_deodexed\zxing\zxing_classes.dex.dex`
10. Now we will add **few** of the above decompiles libs to our code
   8. Create a folder `src\ANXCamera\smali_classes2`
   9. Copy the **contents** of 
      1. `<path to deodex destination>\android-support-v7-recyclerview`
      2. `<path to deodex destination>\android-support-v13`
      3. `<path to deodex destination>\gson`
      4. `<path to deodex destination>\volley`
      5. `<path to deodex destination>\zxing`
   10. to `src\ANXCamera\smali_classes2`. It should finally contain two folders
      6. `android` and `com`
11. Set required = false in AndroidManifest of these libs as their code is now included
12. Add missing smali files from decompiled miui rom
13. Add native libs
14. Edit Smali
   11. ...

