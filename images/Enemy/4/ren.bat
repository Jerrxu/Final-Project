@echo off
setlocal EnableDelayedExpansion
set i=0
for %%a in (*.png) do (
    ren %%a Right!i!.png
    set /a i+=1
)