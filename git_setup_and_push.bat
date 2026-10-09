@echo off
title Push SaleAppV1 to GitHub
echo ========================================================
echo   DONG BO VA DAY CODE LEN GITHUB: Thdang53/saleappv1
echo ========================================================
cd /d "%~dp0"

if not exist ".git" (
    echo [1/4] Khoi tao git repo...
    git init
    git branch -M main
    git remote add origin https://github.com/Thdang53/saleappv1.git
)

echo [2/4] Them cac thay doi...
git add .

echo [3/4] Commit...
git commit -m "Bai thuc hanh 4: Xay dung chuc nang Hien thi / Them / Xoa / Sua (CRUD Category & Product)"

echo [4/4] Push len GitHub...
git push -u origin main

echo ========================================================
echo Hoan tat!
pause
