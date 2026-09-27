@echo off
title SaleAppV1 - Spring Boot Server
set "JAVA_HOME=C:\Program Files\Java\jdk-17"
echo ========================================================
echo   KHOI DONG HE THONG BAN HANG TRUC TUYEN (SALEAPPV1)
echo   Dia chi website: http://localhost:8080
echo ========================================================
cd /d "%~dp0"
call mvnw.cmd spring-boot:run
pause
