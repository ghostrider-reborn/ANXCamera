
	:: Convert .dat.br -> .dat
"G:\ROOT\Extractor\brotli.exe" --decompress --in E:\system.new.dat.br --out G:\ROOT\Tucana11.0.5.0\system.new.dat
"G:\ROOT\Extractor\brotli.exe" --decompress --in E:\vendor.new.dat.br --out G:\ROOT\Tucana11.0.5.0\vendor.new.dat


	:: Convert .dat -> .img
"G:\ROOT\Extractor\sdat2Img.exe" G:\ROOT\Tucana11.0.5.0\system.transfer.list G:\ROOT\Tucana11.0.5.0\system.new.dat G:\ROOT\Tucana11.0.5.0\system.img
"G:\ROOT\Extractor\sdat2Img.exe" G:\ROOT\Tucana11.0.5.0\vendor.transfer.list G:\ROOT\Tucana11.0.5.0\vendor.new.dat G:\ROOT\Tucana11.0.5.0\vendor.img

	:: Extract .img
"C:\Program Files\7-Zip\7z.exe" x G:\ROOT\Tucana11.0.5.0\system.img -oG:\ROOT\Tucana11.0.5.0\system
"C:\Program Files\7-Zip\7z.exe" x G:\ROOT\Tucana11.0.5.0\vendor.img -oG:\ROOT\Tucana11.0.5.0\vendor