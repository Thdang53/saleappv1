@echo off
title SaleAppV1 - Spring Boot Server
if defined JAVA_HOME goto start_app
if exist "C:\Users\Admin\.p2\pool\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_21.0.12.v20260826-1216\jre" (
    set "JAVA_HOME=C:\Users\Admin\.p2\pool\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_21.0.12.v20260826-1216\jre"
    set "PATH=%JAVA_HOME%\bin;%PATH%"
)
:start_app
echo ========================================================
echo   KHOI DONG HE THONG BAN HANG TRUC TUYEN (SALEAPPV1)
echo   Quan ly Danh muc: http://localhost:9999/categories
echo   Quan ly San pham: http://localhost:9999/products
echo ========================================================
cd /d "%~dp0"
call mvnw.cmd spring-boot:run
pause
