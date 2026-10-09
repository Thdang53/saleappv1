@echo off
title Push SaleAppV1 to GitHub
set "GIT_EXE=C:\Users\Admin\.gemini\antigravity\scratch\mingit\cmd\git.exe"
if exist "%GIT_EXE%" (
    set "PATH=C:\Users\Admin\.gemini\antigravity\scratch\mingit\cmd;%PATH%"
)
echo ========================================================
echo   DONG BO VA DAY CODE LEN GITHUB: Thdang53/saleappv1
echo ========================================================
cd /d "%~dp0"

echo [1/2] Kiem tra commit...
git status

echo [2/2] Dang day code len GitHub (origin main)...
git push origin main

echo ========================================================
echo Hoan tat! Neu co popup hoi dang nhap GitHub, ban vui long dang nhap de hoan tat.
pause
